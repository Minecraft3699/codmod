package com.mc3699.codmod.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BloodParticle extends TextureSheetParticle {
    private boolean hasHitGround = false;
    private int groundTimer = 0;


    protected BloodParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSpriteFromAge(spriteSet);
        this.lifetime = 40;
        this.gravity = (0.25f + this.random.nextFloat()) * 0.8f;
        this.quadSize = (0.35f + this.random.nextFloat()) * 0.2f;

        this.xd = (this.random.nextFloat() - 0.5) * 0.02;
        this.yd = -0.25;
        this.zd = (this.random.nextFloat() - 0.5) * 0.02;

    }

    @Override
    public void tick() {
        if(!hasHitGround && this.onGround) {
            hasHitGround = true;
            groundTimer = 0;

            this.xd = 0;
            this.yd = 0;
            this.zd = 0;
        }
        if(hasHitGround) {
            groundTimer++;
            this.alpha = 1.0f - (groundTimer/20f);
            if (groundTimer > 20) {
                this.remove();
            }
        }

        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                                 double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BloodParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed);
        }
    }

}