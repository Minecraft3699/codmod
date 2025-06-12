package com.mc3699.codmod.entity.friendlyFace;

import com.mc3699.codmod.registry.CodEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FriendlyFaceEntity extends PathfinderMob {
    private static final Logger LOGGER = LogManager.getLogger();
    public int chaseTime = 200;
    private ResourceLocation nullParticleID = ResourceLocation.fromNamespaceAndPath("thebrokenscript", "null_particle");
    ParticleType<?> nullParticle = BuiltInRegistries.PARTICLE_TYPE.get(nullParticleID);

    public FriendlyFaceEntity(EntityType<FriendlyFaceEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AttackForTimeGoal(this, this.level(), 16, 2));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
        super.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 22f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel serverLevel) {
            if (tickCount % 10 == 0) {
                ParticleOptions particle = (ParticleOptions) nullParticle;
                serverLevel.sendParticles(particle, position().x, position().y, position().z, 3, 0, 0f, 0, 0.2f);
            }
        }
        super.tick();
    }
}