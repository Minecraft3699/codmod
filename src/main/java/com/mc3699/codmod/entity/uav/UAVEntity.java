package com.mc3699.codmod.entity.uav;

import com.mc3699.codmod.entity.EntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class UAVEntity extends Entity {
    private BlockPos linkedController = BlockPos.ZERO;
    private Vec3 targetPos = null;
    private static final EntityDataAccessor<Float> ALTITUDE = SynchedEntityData.defineId(UAVEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(UAVEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> CIRCLE_RADIUS = SynchedEntityData.defineId(UAVEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> TERRAIN_FOLLOWING = SynchedEntityData.defineId(UAVEntity.class, EntityDataSerializers.BOOLEAN);
    private static final float DEFAULT_ALTITUDE = 50.0f;
    private static final float DEFAULT_SPEED = 1.0f;
    private static final float DEFAULT_CIRCLE_RADIUS = 50.0f;
    private static final float ALTITUDE_SMOOTHING = 0.0125f;
    private boolean isCircling = false;
    private float targetPitch = 0.0f;
    private ChunkPos[] previousChunks = null;

    public UAVEntity(Level level) {
        super(EntityRegistration.UAV.get(), level);
        this.entityData.set(ALTITUDE, DEFAULT_ALTITUDE);
        this.entityData.set(SPEED, DEFAULT_SPEED);
        this.entityData.set(CIRCLE_RADIUS, DEFAULT_CIRCLE_RADIUS);
        this.entityData.set(TERRAIN_FOLLOWING, true); // Default to terrain following
        this.setNoGravity(true);
    }

    public boolean isCircling() {
        return this.isCircling;
    }

    public boolean isAtTarget(int threshold) {
        if (targetPos == null) return false;
        Vec3 currentPos = position();
        double horizontalDistance = Math.sqrt(Math.pow(currentPos.x - targetPos.x, 2) + Math.pow(currentPos.z - targetPos.z, 2));
        return horizontalDistance < threshold;
    }

    public void setTargetPos(Vec3 target) {
        this.targetPos = target;
        this.isCircling = false;
    }

    public void setAltitude(float altitude) {
        this.entityData.set(ALTITUDE, altitude);
    }

    public void setSpeed(float speed) {
        this.entityData.set(SPEED, speed);
    }

    public void setCircleRadius(float radius) {
        this.entityData.set(CIRCLE_RADIUS, radius);
    }

    public void setCircling(boolean circling) {
        isCircling = circling;
    }

    public void setTerrainFollowing(boolean enabled) {
        this.entityData.set(TERRAIN_FOLLOWING, enabled);
    }

    public boolean isTerrainFollowing() {
        return this.entityData.get(TERRAIN_FOLLOWING);
    }

    @Override
    public void tick() {
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
            ChunkPos currentChunkPos = chunkPosition();
            ChunkPos[] currentChunks = new ChunkPos[] {
                    currentChunkPos,
                    new ChunkPos(currentChunkPos.x - 1, currentChunkPos.z - 1),
                    new ChunkPos(currentChunkPos.x - 1, currentChunkPos.z),
                    new ChunkPos(currentChunkPos.x - 1, currentChunkPos.z + 1),
                    new ChunkPos(currentChunkPos.x, currentChunkPos.z - 1),
                    new ChunkPos(currentChunkPos.x, currentChunkPos.z + 1),
                    new ChunkPos(currentChunkPos.x + 1, currentChunkPos.z - 1),
                    new ChunkPos(currentChunkPos.x + 1, currentChunkPos.z),
                    new ChunkPos(currentChunkPos.x + 1, currentChunkPos.z + 1)
            };

            if (previousChunks != null) {
                for (ChunkPos prev : previousChunks) {
                    serverLevel.setChunkForced(prev.x, prev.z, false);
                }
            }

            for (ChunkPos chunk : currentChunks) {
                serverLevel.setChunkForced(chunk.x, chunk.z, true);
            }
            previousChunks = currentChunks;
        }

        if (!level().isClientSide && targetPos != null) {
            Vec3 currentPos = position();
            float speed = entityData.get(SPEED);
            if (!isCircling) {
                Vec3 direction = targetPos.subtract(currentPos).normalize();
                double horizontalDistance = Math.sqrt(Math.pow(currentPos.x - targetPos.x, 2) + Math.pow(currentPos.z - targetPos.z, 2));

                if (horizontalDistance > 0.5) {
                    Vec3 motion = direction.scale(speed);
                    move(MoverType.SELF, motion);

                    double targetY;
                    if (entityData.get(TERRAIN_FOLLOWING)) {
                        BlockPos groundPos = new BlockPos((int) position().x, (int) position().y, (int) position().z);
                        int groundY = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundPos).getY();
                        targetY = groundY + entityData.get(ALTITUDE);
                    } else {
                        targetY = targetPos.y; // Maintain fixed altitude relative to target Y
                    }
                    double currentY = position().y;
                    double newY = currentY + (targetY - currentY) * ALTITUDE_SMOOTHING;
                    setPos(position().x, newY, position().z);

                    float yaw = (float) (Math.atan2(direction.z, direction.x) * 180 / Math.PI);
                    setYRot(yaw);
                } else {
                    isCircling = true;
                }
            } else {
                float radius = entityData.get(CIRCLE_RADIUS);
                // Calculate angular speed to maintain constant linear speed
                float angularSpeed = speed / radius; // v = r * ω => ω = v / r
                double angle = (this.tickCount * angularSpeed) % (2 * Math.PI);
                double centerX = targetPos.x;
                double centerZ = targetPos.z;
                double x = centerX + radius * Math.cos(angle);
                double z = centerZ + radius * Math.sin(angle);

                double targetY;
                if (entityData.get(TERRAIN_FOLLOWING)) {
                    BlockPos groundPos = new BlockPos((int) x, (int) position().y, (int) z);
                    int groundY = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundPos).getY();
                    targetY = groundY + entityData.get(ALTITUDE);
                } else {
                    targetY = targetPos.y; // Maintain fixed altitude
                }
                double currentY = position().y;
                double newY = currentY + (targetY - currentY) * ALTITUDE_SMOOTHING;
                setPos(x, newY, z);

                float yaw = (float) (angle * 180 / Math.PI + 90);
                setYRot(yaw);

                targetPitch = 0.0f;
                setXRot(targetPitch);
            }
        }

        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, position().x, position().y + 0.75f, position().z, 20, 0, 0, 0, 0);
        }
        super.tick();
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel && previousChunks != null) {
            for (ChunkPos chunk : previousChunks) {
                serverLevel.setChunkForced(chunk.x, chunk.z, false);
            }
        }
        super.remove(reason);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return true;
    }

    public void setLinkedController(BlockPos pos) {
        this.linkedController = pos;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ALTITUDE, DEFAULT_ALTITUDE);
        builder.define(SPEED, DEFAULT_SPEED);
        builder.define(CIRCLE_RADIUS, DEFAULT_CIRCLE_RADIUS);
        builder.define(TERRAIN_FOLLOWING, true);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        int linkedX = compoundTag.getInt("controllerX");
        int linkedY = compoundTag.getInt("controllerY");
        int linkedZ = compoundTag.getInt("controllerZ");
        linkedController = new BlockPos(linkedX, linkedY, linkedZ);
        entityData.set(ALTITUDE, compoundTag.getFloat("altitude"));
        entityData.set(SPEED, compoundTag.getFloat("speed"));
        entityData.set(CIRCLE_RADIUS, compoundTag.getFloat("circleRadius"));
        entityData.set(TERRAIN_FOLLOWING, compoundTag.getBoolean("terrainFollowing"));
        if (compoundTag.contains("targetX")) {
            targetPos = new Vec3(compoundTag.getDouble("targetX"), compoundTag.getDouble("targetY"), compoundTag.getDouble("targetZ"));
        }
        isCircling = compoundTag.getBoolean("isCircling");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("controllerX", linkedController.getX());
        compoundTag.putInt("controllerY", linkedController.getY());
        compoundTag.putInt("controllerZ", linkedController.getZ());
        compoundTag.putFloat("altitude", entityData.get(ALTITUDE));
        compoundTag.putFloat("speed", entityData.get(SPEED));
        compoundTag.putFloat("circleRadius", entityData.get(CIRCLE_RADIUS));
        compoundTag.putBoolean("terrainFollowing", entityData.get(TERRAIN_FOLLOWING));
        if (targetPos != null) {
            compoundTag.putDouble("targetX", targetPos.x);
            compoundTag.putDouble("targetY", targetPos.y);
            compoundTag.putDouble("targetZ", targetPos.z);
        }
        compoundTag.putBoolean("isCircling", isCircling);
    }
}