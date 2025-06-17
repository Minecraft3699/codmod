package com.mc3699.codmod.points;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

/**
 * Manages points for a full console setup. Each one should have its own unique instance of this class.
 * This can be encoded and decoded to NBT.
 */
public class PointsManager {
    private int points = 0;

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        tag.putInt("points", points);

        return tag;
    }

    public void deserialize(CompoundTag tag) {
        points = tag.contains("points", Tag.TAG_INT) ? tag.getInt("points") : 0;
    }
}
