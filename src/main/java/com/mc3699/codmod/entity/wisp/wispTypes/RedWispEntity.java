package com.mc3699.codmod.entity.wisp.wispTypes;

import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.wisp.BaseWispEntity;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class RedWispEntity extends BaseWispEntity {
    private PointLight light;

    public RedWispEntity(Level level) {
        super(EntityRegistration.RED_WISP.get(), level, 0xFF0000, List.of(ParticleTypes.CRIMSON_SPORE, ParticleTypes.BUBBLE, ParticleTypes.SNEEZE));
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount % 40 == 0)
        {
            if(level() instanceof ServerLevel serverLevel)
            {
                //serverLevel.explode(null, getX(), getY(), getZ(), 3, Level.ExplosionInteraction.NONE);
            }
        }
    }

    @Override
    protected void registerGoals() {

        if(!level().isClientSide())
        {
            targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
            goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.7f, true));
        }

        super.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.MOVEMENT_SPEED, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 100f);
    }
}
