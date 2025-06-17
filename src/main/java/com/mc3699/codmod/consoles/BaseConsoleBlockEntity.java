package com.mc3699.codmod.consoles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public abstract class BaseConsoleBlockEntity extends BlockEntity {
    protected @Nullable UUID uuid;

    public BaseConsoleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        uuid = tag.contains("uuid", Tag.TAG_STRING) ? UUID.fromString(tag.getString("uuid")) : UUID.randomUUID();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putString("uuid", (uuid != null ? uuid : UUID.randomUUID()).toString());
    }

    public ConsoleData getConsoleData() {
        return ConsoleManager.getConsoleData(uuid);
    }
}
