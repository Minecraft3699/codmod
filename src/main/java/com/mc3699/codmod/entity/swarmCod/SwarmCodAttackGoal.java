package com.mc3699.codmod.entity.swarmCod;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SwarmCodAttackGoal extends Goal {

    private final SwarmCodEntity mob;
    private LivingEntity target;
    private int attackTime;

    public SwarmCodAttackGoal(SwarmCodEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();
        return this.target != null &&
               this.target.isAlive() &&
               this.mob.distanceToSqr(this.target) < 128.0; // Within 8 block
    }

    @Override
    public void start() {
        this.attackTime = 0;
    }

    @Override
    public void tick() {
        this.attackTime++;

        if (this.mob.onGround() && target != null) {
            // Calculate direction to target
            double dx = this.target.getX() - this.mob.getX();
            double dz = this.target.getZ() - this.mob.getZ();
            float yRot = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;

            // Set movement
            if (this.mob.getMoveControl() instanceof SwarmCodMoveControl moveControl) {
                moveControl.setWantedMovement(yRot, this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }

            // Attack if close enough
            if (this.mob.distanceToSqr(this.target) < 3.0) {
                this.mob.level()
                        .playSound(
                                null,
                                this.mob.getX(),
                                this.mob.getY(),
                                this.mob.getZ(),
                                SoundEvents.PHANTOM_BITE,
                                SoundSource.HOSTILE,
                                1.0f,
                                1.0f
                        );
                this.target.hurt(
                        this.mob.level().damageSources().mobAttack(this.mob),
                        (float) this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE)
                );
                this.attackTime = 0;
            }
        }
    }
}
