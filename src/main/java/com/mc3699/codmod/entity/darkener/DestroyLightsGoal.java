package com.mc3699.codmod.entity.darkener;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.pathfinder.Path;

import java.util.Properties;

public class DestroyLightsGoal extends Goal {
    private final PathfinderMob entity;
    private final Level level;
    private BlockPos targetPos;
    private final double speed;
    private final int range;

    public DestroyLightsGoal(PathfinderMob entity, double speed, int range) {
        this.entity = entity;
        this.level = entity.level();
        this.speed = speed;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        // Only activate if no player with > 2.0 health is in light
        if (entity.getTarget() != null && entity.getTarget().isAlive()
                && level.getBrightness(LightLayer.BLOCK, entity.getTarget().blockPosition()) > 4
                && entity.getTarget().getHealth() > 2.0F) {
            return false;
        }
        targetPos = findClosestLightEmittingBlock();
        return targetPos != null;
    }

    @Override
    public void start() {
        Path path = entity.getNavigation().createPath(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, 1);
        entity.getNavigation().moveTo(path, speed);
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && level.getBlockState(targetPos).getLightEmission(level, targetPos) > 0
                && !entity.getNavigation().isDone()
                && (entity.getTarget() == null
                || !entity.getTarget().isAlive()
                || level.getBrightness(LightLayer.BLOCK, entity.getTarget().blockPosition()) <= 4
                || entity.getTarget().getHealth() <= 2.0F);
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
            if (entity.distanceToSqr(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5) < 20.0) {
                level.destroyBlock(targetPos, true, entity);
                targetPos = null;
            }
        }
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
                    if (state.getLightEmission(level, pos) > 7 && isBlockDestructible(state, pos) && !state.liquid()) {
                        if (isBlockReachable(pos)) {
                            double distSq = entity.distanceToSqr(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
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
        Path path = entity.getNavigation().createPath(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 10);
        return path != null && path.canReach();
    }

    private boolean isBlockDestructible(BlockState state, BlockPos pos) {
        return state.getDestroySpeed(level, pos) >= 0;
    }
}