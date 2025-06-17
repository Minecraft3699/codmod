package com.mc3699.codmod.upgrades;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.points.PointsManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages upgrades for a full console setup. Each one should have its own unique instance of this class.
 * This can be encoded and decoded to NBT.
 */
public class UpgradeManager {
    private final Map<String, Integer> upgrades = new HashMap<>();

    public int getLevel(Upgrade upgrade) {
        return upgrades.computeIfAbsent(upgrade.id(), k -> upgrade.defaultLevel());
    }

    public boolean canAffordUpgrade(Upgrade upgrade, PointsManager points) {
        return upgrade.getCost(getLevel(upgrade) + 1) <= points.getPoints();
    }

    public boolean upgrade(Upgrade upgrade, PointsManager points) {
        int target = getLevel(upgrade) + 1;

        if (upgrade.getCost(target) > points.getPoints()) return false;
        if (target > upgrade.maxLevel()) return false;

        points.addPoints(-upgrade.getCost(target));
        upgrades.put(upgrade.id(), target);

        return true;
    }

    public boolean downgrade(Upgrade upgrade, PointsManager points) {
        if (getLevel(upgrade) - 1 < 0) return false;

        points.addPoints(upgrade.getCost(getLevel(upgrade)));
        upgrades.put(upgrade.id(), getLevel(upgrade) - 1);

        return true;
    }

    public void setLevel(Upgrade upgrade, int level) {
        upgrades.put(upgrade.id(), level);
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        for (Map.Entry<String, Integer> entry : upgrades.entrySet()) {
            tag.putInt(entry.getKey(), entry.getValue());
        }

        return tag;
    }

    public void deserialize(CompoundTag tag) {
        upgrades.clear();

        for (String key : tag.getAllKeys()) {
            if (!Upgrades.UPGRADES.containsKey(key)) {
                Codmod.LOGGER.warn("Unknown upgrade ID: {}", key);
                continue;
            }

            if (tag.getTagType(key) != Tag.TAG_INT) {
                Codmod.LOGGER.warn("Invalid tag type for upgrade data {}: {}", key, tag.getTagType(key));
                continue;
            }

            upgrades.put(key, tag.getInt(key));
        }
    }
}
