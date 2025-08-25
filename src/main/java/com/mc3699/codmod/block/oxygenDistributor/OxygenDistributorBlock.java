package com.mc3699.codmod.block.oxygenDistributor;

import com.mc3699.codmod.block.codNuke.CodNukeBlockEntity;
import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class OxygenDistributorBlock extends Block implements EntityBlock {
    public OxygenDistributorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new OxygenDistributorBlockEntity(blockPos,blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == CodBlockEntities.OXYGEN_DISTRIBUTOR.get() ? OxygenDistributorBlockEntity::tick : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level instanceof ServerLevel serverLevel)
        {

            if(serverLevel.getBlockEntity(pos) instanceof OxygenDistributorBlockEntity distributorBlockEntity)
            {
                boolean renderState = distributorBlockEntity.shouldRender();
                distributorBlockEntity.setShouldRender(!renderState);
                serverLevel.playSound(null, pos, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
