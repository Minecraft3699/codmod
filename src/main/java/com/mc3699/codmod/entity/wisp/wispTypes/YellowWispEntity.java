package com.mc3699.codmod.entity.wisp.wispTypes;

import com.mc3699.codmod.entity.wisp.BaseWispEntity;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class YellowWispEntity extends BaseWispEntity {
    private PointLight light;

    public YellowWispEntity(EntityType<YellowWispEntity> type, Level level) {
        super(type, level, 0xFFFF00, List.of(ParticleTypes.SMALL_FLAME, ParticleTypes.CRIMSON_SPORE));
    }


    @Override
    protected void registerGoals() {

        if (!level().isClientSide()) {
            targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
            goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.7f, true));
        }

        super.registerGoals();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.MOVEMENT_SPEED, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 100f);
    }
}
