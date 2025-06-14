package com.mc3699.codmod.entity.misguided;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class MisguidedEntity extends PathfinderMob {

    private static final int DESPAWN_TIME = 3 * 60 * 20;
    private Mode currentMode;
    private int despawnTimer;

    public MisguidedEntity(EntityType<MisguidedEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 22f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.FOLLOW_RANGE, 48);
    }

    public Mode getMode() {
        return this.currentMode;
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EffectAndDespawnGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (this.getY() < 60) {
            this.setMode(Mode.AGGRESSIVE);
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.currentMode == Mode.PASSIVE) {
            this.despawnTimer--;
            if (this.despawnTimer <= 0) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        if(damageContainer.getSource().getEntity() instanceof LivingEntity target)
        {
            this.currentMode = Mode.AGGRESSIVE;
            this.setTarget(target);
        }
        super.onDamageTaken(damageContainer);
    }

    public enum Mode {
        PASSIVE, AGGRESSIVE
    }


}
