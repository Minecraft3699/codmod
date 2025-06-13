package com.mc3699.codmod.entity.darkener;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class DestroyLightsGoal extends Goal {
    private final PathfinderMob entity;
    private final Level level;
    private final double speed;
    private final int range;
    private BlockPos targetPos;

    public DestroyLightsGoal(PathfinderMob entity, double speed, int range) {
        this.entity = entity;
        this.level = entity.level();
        this.speed = speed;
        this.range = range;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (entity.tickCount % 20 == 0 || targetPos == null) {
            targetPos = findClosestLightEmittingBlock();
        }
        return targetPos != null;
    }

    @Override
    public void start() {
        if (targetPos != null) {
            Path path = entity.getNavigation()
                    .createPath(
                            targetPos.getX() + 0.5,
                            targetPos.getY() + 0.5,
                            targetPos.getZ() + 0.5,
                            2
                    ); // Reduced accuracy
            entity.getNavigation().moveTo(path, speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (targetPos == null) return false;
        BlockState state = level.getBlockState(targetPos);
        return !entity.getNavigation().isDone() &&
               state.getLightEmission(level, targetPos) > 0 &&
               isBlockReachable(targetPos);
    }

    @Override
    public void tick() {
        if (targetPos != null) {
            entity.getLookControl().setLookAt(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 0.5,
                    targetPos.getZ() + 0.5,
                    10.0F,
                    entity.getMaxHeadXRot()
            );
            if (entity.distanceToSqr(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5) <=
                25.0) { // 5 blocks range
                level.destroyBlock(targetPos, true, entity);
                targetPos = null;
                entity.getNavigation().stop();
            } else {
                if (entity.getNavigation().isDone()) {
                    Path path = entity.getNavigation()
                            .createPath(
                                    targetPos.getX() + 0.5,
                                    targetPos.getY() + 0.5,
                                    targetPos.getZ() + 0.5,
                                    2
                            ); // Reduced accuracy
                    entity.getNavigation().moveTo(path, speed);
                }
            }
        }
    }

    @Override
    public void stop() {
        targetPos = null;
        entity.getNavigation().stop();
    }

    private BlockPos findClosestLightEmittingBlock() {
        BlockPos entityPos = entity.blockPosition();
        BlockPos closestPos = null;
        double closestDistSq = Double.MAX_VALUE;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = entityPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.getLightEmission(level, pos) > 0 && isBlockDestructible(state, pos)) {
                        if (isBlockReachable(pos)) {
                            double distSq = entity.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                            if (distSq < closestDistSq) {
                                closestDistSq = distSq;
                                closestPos = pos;
                            }
                        }
                    }
                }
            }
        }
        return closestPos;
    }

    private boolean isBlockReachable(BlockPos pos) {
        Path path = entity.getNavigation()
                .createPath(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2); // Reduced accuracy
        return path != null && path.canReach();
    }

    private boolean isBlockDestructible(BlockState state, BlockPos pos) {
        return state.getDestroySpeed(level, pos) >= 0 && !state.isAir() && !state.liquid();
    }
}