package com.mc3699.codmod.entity.misguided;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class EffectAndDespawnGoal extends Goal {

    private final MisguidedEntity entity;
    private Player target;

    public EffectAndDespawnGoal(MisguidedEntity entity) {
        this.entity = entity;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean canUse() {
        if (this.entity.getMode() == MisguidedEntity.Mode.AGGRESSIVE) {
            this.target = (Player) this.entity.getTarget();
            return this.target != null;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.target != null) {
            this.entity.getNavigation().moveTo(this.target, 2);
            if (this.entity.distanceTo(this.target) <= 1.5) {
                target.addEffect(new MobEffectInstance(CodMobEffects.HEART_CORRUPTION, 240, 5));
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 240, 5));
                this.entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
