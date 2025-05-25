package com.mc3699.codmod.entity.darkener;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

public class DestroyLightsGoal extends Goal {

    private final PathfinderMob entity;
    private final Level level;
    private BlockPos targetPos;
    private final double speed;
    private final int range;

    public DestroyLightsGoal(PathfinderMob entity, Level level, double speed, int range) {
        this.entity = entity;
        this.level = level;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        targetPos = findClosestLightEmittingBlock();
        return targetPos != null;
    }

    @Override
    public void start() {
        Path path = entity.getNavigation().createPath(targetPos, 0);
        entity.getNavigation().moveTo(path, speed);
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && level.getBlockState(targetPos).getLightEmission(level, targetPos) > 8
                && !entity.getNavigation().isDone();
    }

    @Override
    public void tick() {

        if(targetPos !=null)
        {
            entity.getLookControl().setLookAt(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 0.5,
                    targetPos.getZ() + 0.5,
                    10.0F, // Horizontal rotation speed
                    entity.getMaxHeadXRot() // Vertical rotation limit
            );
        }


        entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_PICKAXE, 1));
        if (entity.distanceToSqr(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5) < 20.0) {
            level.destroyBlock(targetPos, true, entity);
            targetPos = null;
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
                    if (state.getLightEmission(level, pos) > 8) {
                        if(isBlockReachable(pos))
                        {
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
        Path path = entity.getNavigation().createPath(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 25);
        if (path == null || !path.canReach()) {
            return false;
        }
        return true;
    }


}
