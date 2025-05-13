package com.mc3699.codmod.entity.trialTrader;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import java.util.EnumSet;
import java.util.List;

public class RepairGoal extends Goal {
    private final TrialTraderEntity entity;
    private RepairTask currentTask;
    private int timeoutCounter;

    public RepairGoal(TrialTraderEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE)); // Indicates this goal controls movement
    }

    @Override
    public boolean canUse() {
        List<RepairTask> tasks = entity.getRepairTasks();
        if (!tasks.isEmpty()) {
            currentTask = tasks.get(0); // Take the first task in the list
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        BlockPos pos = currentTask.pos();
        // Start moving to the target position
        this.entity.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
        timeoutCounter = 0;
    }

    @Override
    public void tick() {
        if (currentTask != null) {
            BlockPos pos = currentTask.pos();
            Level level = this.entity.level();
            // Check if the entity is close enough to the block (within 2 blocks)
            if (this.entity.blockPosition().closerThan(pos, 2.0)) {
                // Repair the block if it’s still air
                if (level.getBlockState(pos).isAir()) {
                    level.setBlock(pos, currentTask.state(), 3);
                    if(currentTask.blockEntity() != null)
                    {
                        level.setBlockEntity(currentTask.blockEntity());
                    }
                    entity.getRepairTasks().remove(currentTask);
                    entity.setTarget(currentTask.breaker());
                    currentTask = null;
                }
            } else {
                // Increment timeout; abandon task if it takes too long
                timeoutCounter++;
                if (timeoutCounter > 1200) {
                    entity.getRepairTasks().remove(currentTask);
                    currentTask = null;
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return currentTask != null && !entity.getRepairTasks().isEmpty();
    }
}