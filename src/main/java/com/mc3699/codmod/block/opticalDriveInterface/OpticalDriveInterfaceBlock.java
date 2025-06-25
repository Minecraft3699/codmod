package com.mc3699.codmod.block.opticalDriveInterface;

import com.mc3699.codmod.item.OpticalTapeDriveItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class OpticalDriveInterfaceBlock extends Block implements EntityBlock {

    public static BooleanProperty HAS_DISK = BooleanProperty.create("has_disk");

    public OpticalDriveInterfaceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HAS_DISK,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_DISK);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof OpticalDriveInterfaceBlockEntity drive) {
                if (drive.getDisk().isEmpty() && stack.getItem() instanceof OpticalTapeDriveItem) {
                    // Insert disk
                    ItemStack toInsert = stack.copyWithCount(1);
                    drive.setDisk(toInsert);
                    stack.shrink(1); // Directly reduce stack size
                    level.setBlock(pos, state.setValue(HAS_DISK, true), 3);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof OpticalDriveInterfaceBlockEntity drive) {
                if(!drive.getDisk().isEmpty()) {

                    ItemStack ejectDisk = drive.getDisk().copyAndClear();
                    player.getInventory().add(ejectDisk);
                    level.setBlock(pos, state.setValue(HAS_DISK, false), 3);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new OpticalDriveInterfaceBlockEntity(blockPos, blockState);
    }
}
