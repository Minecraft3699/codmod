package com.mc3699.codmod.signals;

import net.minecraft.world.phys.Vec2;

public record Signal(String id, Vec2 position, byte[] data) {
    public SignalSize getSize() {
        return SignalSize.forBytes(data.length);
    }

    public int liveTime() {
        return data.length / 1024 / 2;
    }
}
