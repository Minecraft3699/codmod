package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodComponents;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.filesystem.Mount;
import dan200.computercraft.api.filesystem.WritableMount;
import dan200.computercraft.api.media.IMedia;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpticalTapeDriveItem extends Item implements IMedia {

    public OpticalTapeDriveItem(Properties properties) {
        super(properties);

    }

    @Override
    public @Nullable String getLabel(HolderLookup.Provider registries, ItemStack stack) {
        return stack.getOrDefault(CodComponents.DISK_LABEL, "Optical Drive");
    }


    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);

        String label = stack.getOrDefault(CodComponents.DISK_LABEL.get(), "No Label");
        Long storedSize = stack.getOrDefault(CodComponents.DISK_SIZE.get(), 0L);
        tooltipComponents.add(Component.literal("Drive Label: " + label).withStyle(ChatFormatting.AQUA));

        if (data != null) {
            if (data.getUnsafe().contains("diskID")) {
                String idText = data.getUnsafe().getString("diskID");
                String idHex = Integer.toHexString(Integer.parseInt(idText));
                tooltipComponents.add(Component.literal("Drive ID: " + idHex).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltipComponents.add(Component.literal("Drive ID: Uninitialized").withStyle(ChatFormatting.RED));
        }


        tooltipComponents.add(Component.literal("Size: " + formatSize(storedSize)).withStyle(ChatFormatting.GRAY));
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1000));
        String unit = String.valueOf("KM".charAt(exp - 1));
        return String.format("%.1f %sB", bytes / Math.pow(1000, exp), unit);
    }

    @Override
    public boolean setLabel(ItemStack stack, @Nullable String label) {
        if (label == null) {
            stack.remove(CodComponents.DISK_LABEL);
        } else {
            stack.set(CodComponents.DISK_LABEL, label);
        }
        return true;
    }

    @Override
    public @Nullable Mount createDataMount(ItemStack stack, ServerLevel level) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        String diskID = data.getUnsafe().contains("diskID") ?
                        data.getUnsafe().getString("diskID") :
                        String.valueOf(level.random.nextInt());
        if (!data.getUnsafe().contains("diskID")) {
            stack.update(
                    DataComponents.CUSTOM_DATA,
                    CustomData.EMPTY,
                    customData -> customData.update(nbt -> nbt.putString("diskID", diskID))
            );
        }

        Mount mount = ComputerCraftAPI.createSaveDirMount(level.getServer(), "optical_drive/" + diskID, 10000000L);

        if (mount instanceof WritableMount writableMount) {
            try {
                long used = calculateMountSize(writableMount, "");
                stack.set(CodComponents.DISK_SIZE, used);
            } catch (IOException e) {
                stack.set(CodComponents.DISK_SIZE, 0L);
            }
        }

        return mount;
    }

    private long calculateMountSize(WritableMount mount, String path) throws IOException {
        long totalSize = 0;
        List<String> contents = new ArrayList<>();
        mount.list(path, contents);
        for (String subPath : contents) {
            String fullPath = path.isEmpty() ? subPath : path + "/" + subPath;
            if (mount.isDirectory(fullPath)) {
                totalSize += calculateMountSize(mount, fullPath);
            } else {
                totalSize += mount.getSize(fullPath);
            }
        }
        return totalSize;
    }
}
