package com.mc3699.codmod.entity.trialTrader;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TrialTraderEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if(event.getLevel() instanceof ServerLevel serverLevel)
        {
            BlockState brokenBlock = event.getState();

            if(brokenBlock.is(Tags.Blocks.STORAGE_BLOCKS_COPPER) || brokenBlock.is(Blocks.TRIAL_SPAWNER) || brokenBlock.is(Blocks.VAULT))
            {
                BlockPos breakPos = event.getPos();
                BlockEntity blockEntity = event.getLevel().getBlockEntity(breakPos);
                List<TrialTraderEntity> trialTraders = serverLevel.getEntitiesOfClass(TrialTraderEntity.class, new AABB(breakPos).inflate(32));

                TrialTraderEntity closest = null;
                double minDist = Double.MAX_VALUE;
                for (TrialTraderEntity entity : trialTraders) {
                    double dist = entity.distanceToSqr(breakPos.getX(), breakPos.getY(), breakPos.getZ());
                    if (dist < minDist) {
                        minDist = dist;
                        closest = entity;
                    }
                }
                if (closest != null) {
                    closest.addRepairTask(breakPos, brokenBlock, blockEntity, event.getPlayer());
                }
            }
        }
    }

}