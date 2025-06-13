package com.mc3699.codmod.entity.wisp;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class BaseWispEntity extends PathfinderMob {
    private final List<ParticleType<?>> particleTypes;
    private int color;

    protected BaseWispEntity(
            EntityType<? extends PathfinderMob> type,
            Level level,
            int color,
            List<ParticleType<?>> particleTypes
    ) {
        super(type, level);
        this.color = color;
        this.particleTypes = particleTypes;
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            //spawnParticles();
            //spawnParticles();
        }
    }

    private void spawnParticles() {
        double speed = 0.4;
        double dx = (this.random.nextDouble() - 0.5) * speed;
        double dy = (this.random.nextDouble() - 0.5) * speed;
        double dz = (this.random.nextDouble() - 0.5) * speed;

        int randParticle = random.nextInt(0, particleTypes.size());


        this.level().addParticle(
                (ParticleOptions) particleTypes.get(randParticle),
                this.getX(), this.getY() + 2.5, this.getZ(),
                dx, dy, dz
        );
    }

    @Override
    public boolean save(CompoundTag compound) {
        compound.putInt("color", this.color);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        this.color = compound.getInt("color");
        super.load(compound);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new OpenDoorGoal(this, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.3f));
    }

}