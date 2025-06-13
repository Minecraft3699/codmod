package com.mc3699.codmod.entity.darkener;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

public class DarkenerEntity extends PathfinderMob {

    private final int DESPAWN_TIME = 6000;

    public DarkenerEntity(EntityType<DarkenerEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AttackPlayerInLightGoal(this, 1.8f));
        this.goalSelector.addGoal(5, new DestroyLightsGoal(this, 1.25f, 16));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1));
    }

    @Override
    public void tick() {
        super.tick();

        if (tickCount > DESPAWN_TIME) {
            discard();
        }
    }
}