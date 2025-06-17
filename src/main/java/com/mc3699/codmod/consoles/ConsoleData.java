package com.mc3699.codmod.consoles;

import com.mc3699.codmod.points.PointsManager;
import com.mc3699.codmod.signals.SignalManager;
import com.mc3699.codmod.upgrades.UpgradeManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class ConsoleData {
    public final UpgradeManager upgrades = new UpgradeManager();
    public final PointsManager points = new PointsManager();
    public final SignalManager signals = new SignalManager();

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        tag.put("upgrades", upgrades.serialize());
        tag.put("points", points.serialize());
        tag.put("signals", signals.serialize());

        return tag;
    }

    public void deserialize(CompoundTag tag) {
        upgrades.deserialize(tag.contains("upgrades", Tag.TAG_COMPOUND) ?
                             tag.getCompound("upgrades") :
                             new CompoundTag());

        points.deserialize(tag.contains("points", Tag.TAG_COMPOUND) ? tag.getCompound("points") : new CompoundTag());
        signals.deserialize(tag.contains("signals", Tag.TAG_COMPOUND) ? tag.getCompound("signals") : new CompoundTag());
    }
}
