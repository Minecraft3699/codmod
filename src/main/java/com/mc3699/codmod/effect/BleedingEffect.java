package com.mc3699.codmod.effect;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodDamageTypes;
import com.mc3699.codmod.registry.CodMobEffects;
import com.mc3699.codmod.registry.CodParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BleedingEffect extends MobEffect {
    private final Map<UUID, Vec3> lastPositions = new HashMap<>();

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, -1);
    }
    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effectInstance) {
        return CodParticles.BLOOD_PARTICLE.get();
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        MobEffectInstance effectInstance = livingEntity.getEffect(CodMobEffects.BLEEDING);
        if (effectInstance != null) {

            int remainingDuration = effectInstance.getDuration();
            Vec3 currentPosition = livingEntity.position();
            Vec3 lastPos = lastPositions.get(livingEntity.getUUID());

            //checks if they're moving
            //cus if ur bleeding heavy and u move around itll disturb the wound
            //so it ticks more when you're moving
            boolean isMoving = false;
            if (lastPos != null) {
                double distanceMoved = currentPosition.distanceToSqr(lastPos);
                isMoving = distanceMoved > 0.001;
            }
            lastPositions.put(livingEntity.getUUID(), currentPosition);

            if ((isMoving && remainingDuration % 10 == 0) ||
                    (!isMoving && remainingDuration % 40 == 0)) {

                float damage = 1.0f + amplifier;
                DamageSource bleedDamage = new DamageSource(livingEntity.level()
                        .registryAccess()
                        .lookupOrThrow(Registries.DAMAGE_TYPE)
                        .getOrThrow(CodDamageTypes.BLEEDING));
                Vec3 motion = livingEntity.getDeltaMovement();
                livingEntity.hurt(bleedDamage, damage);
                livingEntity.setDeltaMovement(motion);
            }

            if (remainingDuration % 3 == 0) {
                if(!livingEntity.level().isClientSide()) {

                    ServerLevel serverLevel = (ServerLevel) livingEntity.level();

                    for (int i = 0; i < 3; i++) {
                        double offsetX = (livingEntity.getRandom().nextDouble() - 0.5) * livingEntity.getBbWidth();
                        double offsetY = livingEntity.getRandom().nextDouble() * livingEntity.getBbHeight();
                        double offsetZ = (livingEntity.getRandom().nextDouble() - 0.5) * livingEntity.getBbWidth();

                        serverLevel.sendParticles(
                                CodParticles.BLOOD_PARTICLE.get(),
                                livingEntity.getX() + offsetX,
                                livingEntity.getY() + offsetY,
                                livingEntity.getZ() + offsetZ,
                                1, 0, 0, 0, 0
                        );
                    }
                }
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}