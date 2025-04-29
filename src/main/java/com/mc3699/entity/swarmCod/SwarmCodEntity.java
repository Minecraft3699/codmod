package com.mc3699.entity.swarmCod;

import com.mc3699.entity.EntityRegistration;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class SwarmCodEntity extends Mob {
    public SwarmCodEntity(EntityType<? extends Mob> type, Level level) {
        super(EntityRegistration.SWARM_COD.get(), level);
        this.moveControl = new SwarmCodMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.MOVEMENT_SPEED, 10f)
                .add(Attributes.ATTACK_DAMAGE, 6f);
    }

    @Override
    protected void registerGoals() {
        Predicate<LivingEntity> targetPredicate = livingEntity -> livingEntity.isAlive() && !(livingEntity instanceof SwarmCodEntity);
        this.goalSelector.addGoal(1, new SwarmCodAttackGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, targetPredicate));
    }

    public Vec3 getMovementDirectionToEntity(LivingEntity target, double speed) {
        if (target == null) {
            return Vec3.ZERO; // No movement if no target
        }

        // Calculate delta vector to target
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();

        // Calculate distance (horizontal)
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance < 1.0E-7) {
            return Vec3.ZERO; // Avoid division by zero if too close
        }

        // Normalize direction
        double normalizedX = dx / distance;
        double normalizedZ = dz / distance;

        // Scale by speed
        return new Vec3(normalizedX * speed, 0.0, normalizedZ * speed);
    }


}
