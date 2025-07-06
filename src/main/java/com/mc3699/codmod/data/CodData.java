package com.mc3699.codmod.data;

import com.mc3699.codmod.consoles.ConsoleManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CodData extends SavedData {
    private static final CodData INSTANCE = new CodData();

    public static final Factory<CodData> FACTORY = new Factory<>(() -> INSTANCE, CodData::load);

    public static CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        tag.put("console_data", ConsoleManager.serialize());

        return tag;
    }

    public static void deserialize(CompoundTag tag) {
        ConsoleManager.deserialize(tag.contains("console_data", Tag.TAG_COMPOUND) ?
                                   tag.getCompound("console_data") :
                                   new CompoundTag());
    }

    public static CodData load(CompoundTag tag, HolderLookup.Provider provider) {
        deserialize(tag.contains("cod", Tag.TAG_COMPOUND) ? tag.getCompound("cod") : new CompoundTag());

        return INSTANCE;
    }

    public static void forceUpdate() {
        INSTANCE.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("cod", serialize());

        return tag;
    }
}
