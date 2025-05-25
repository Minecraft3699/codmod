package com.mc3699.codmod.entity.darkener;

import com.mc3699.codmod.entity.EntityRegistration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkenerEntity extends PathfinderMob {
    private static final Logger log = LoggerFactory.getLogger(DarkenerEntity.class);

    public DarkenerEntity(Level level) {
        super(EntityRegistration.DARKENER.get(), level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(2, new DestroyLightsGoal(this, level(), 1.25f, 16));
        this.goalSelector.addGoal(1, new AttackPlayerInLightGoal(this, level(), 1.8f));
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 1f)
                .add(Attributes.FOLLOW_RANGE, 48);
    }


}
