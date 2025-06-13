package com.mc3699.codmod.entity.swarmCod;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SwarmCodMoveControl extends MoveControl {
    private final Random random = new Random();
    private float yRot;
    private int jumpDelay;

    public SwarmCodMoveControl(Mob mob) {
        super(mob);
        this.yRot = 0.0F;
        this.jumpDelay = 0;
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            if (this.mob.onGround() && --this.jumpDelay <= 0) {

                LivingEntity target = this.mob instanceof SwarmCodEntity swarmCod ? swarmCod.getTarget() : null;

                // Calculate direction to target
                double dx = this.wantedX - this.mob.getX();
                double dz = this.wantedZ - this.mob.getZ();
                float newYRot = (float) (Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), newYRot, 90.0F));
                this.yRot = this.mob.getYRot();

                Vec3 motion = this.mob.getDeltaMovement();
                if (motion.x != 0.0 || motion.z != 0.0) {
                    float targetYaw = (float) (Mth.atan2(motion.z, motion.x) * (180.0 / Math.PI)) - 90.0F;
                    this.mob.setYRot(this.mob.getYRot() + Mth.wrapDegrees(targetYaw - this.mob.getYRot()) * 0.5F);
                }

                Vec3 dir = ((SwarmCodEntity) this.mob).getMovementDirectionToEntity(
                        target,
                        0.8 + random.nextFloat(-1, 1)
                );
                this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(dir.x, 0.6, dir.z));
                this.mob.level()
                        .playSound(
                                null,
                                this.mob.getX(),
                                this.mob.getY(),
                                this.mob.getZ(),
                                SoundEvents.COD_FLOP,
                                SoundSource.HOSTILE,
                                1.0f,
                                1.0f
                        );
                this.jumpDelay = this.mob.getRandom().nextInt(10) + 1;

                if (random.nextInt(1, 10) > 9) {
                    mob.level().addFreshEntity(new SwarmCodEntity(null, this.mob.level()));
                }
            }
        } else {
            this.mob.setSpeed(0.0F);
        }
    }

    public void setWantedMovement(float yRot, double speed) {
        this.yRot = yRot;
        this.operation = MoveControl.Operation.MOVE_TO;
    }
}
