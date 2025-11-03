package com.mc3699.codmod.client.particles.blood;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BloodParticleProvider implements ParticleProvider<SimpleParticleType> {


    private final SpriteSet spriteSet;

    public BloodParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
        return new BloodParticle(clientLevel, v1, v2, v3, spriteSet);
    }
}
