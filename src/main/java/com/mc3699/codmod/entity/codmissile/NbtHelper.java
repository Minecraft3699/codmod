package com.mc3699.codmod.entity.codmissile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NbtHelper {

    public static CompoundTag saveItemStacksToNbt(List<ItemStack> stacks) {
        CompoundTag nbt = new CompoundTag();
        ListTag list = new ListTag();
        for (ItemStack stack : stacks) {
            CompoundTag stackNbt = new CompoundTag();
            ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, stack)
                    .result()
                    .ifPresent(list::add);
        }
        nbt.put("items", list);
        return nbt;
    }

    public static List<ItemStack> loadItemStacksFromNbt(CompoundTag nbt) {
        List<ItemStack> stacks = new ArrayList<>();
        ListTag list = nbt.getList("items", ListTag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            ItemStack.CODEC.parse(NbtOps.INSTANCE, list.getCompound(i))
                    .result()
                    .ifPresent(stacks::add);
        }
        return stacks;
    }

}
