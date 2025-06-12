package com.mc3699.codmod.entity.firelight;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;
import java.util.UUID;

public class AttackOrDisappearGoal extends Goal {

    private final PathfinderMob entity;
    private final Level level;
    private final double approachDistance;
    private final double attackSpeed;
    private final float disappearChance;
    private Player target;
    private final Random random;
    private boolean attacking = false;

    private final UUID firelightUUID = UUID.fromString("494de44d-eee2-4e82-8b11-5cd7997976e8");

    public AttackOrDisappearGoal(PathfinderMob entity, Level level, double approachDistance, double attackSpeed, float disappearChance) {
        this.entity = entity;
        this.level = level;
        this.approachDistance = approachDistance;
        this.attackSpeed = attackSpeed;
        this.disappearChance = disappearChance;
        this.random = new Random();
        this.entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(entity, Player.class, false));
    }

    @Override
    public boolean canUse() {
        target = (Player) entity.getTarget();
        return target != null && target.isAlive() && !target.getUUID().equals(firelightUUID);
    }

    @Override
    public void start() {
        entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive();
    }

    @Override
    public void tick() {
        if (target != null) {
            entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());

            if (entity.distanceToSqr(target) < approachDistance * approachDistance) {
                if (random.nextFloat() < disappearChance && !attacking) {
                    entity.discard();
                } else {
                    entity.getNavigation().moveTo(target, attackSpeed);
                    if (entity.distanceToSqr(target) < 2.0) {
                        entity.doHurtTarget(target);
                        target.removeEffect(CodMobEffects.HEART_CORRUPTION);
                        target.addEffect(new MobEffectInstance(CodMobEffects.HEART_CORRUPTION, MobEffectInstance.INFINITE_DURATION, random.nextInt(1,10)));
                    }
                    attacking = true;
                }
            }
        }
    }

    @Override
    public void stop() {
        target = null;
        entity.getNavigation().stop();
    }
}
