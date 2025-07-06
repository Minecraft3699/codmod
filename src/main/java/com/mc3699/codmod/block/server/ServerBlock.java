package com.mc3699.codmod.block.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ServerBlock extends Block {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final BooleanProperty ONLINE = BooleanProperty.create("online");
    public static final BooleanProperty IS_CONTROLLER = BooleanProperty.create("controller");

    public ServerBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_TOP, true));
        this.registerDefaultState(this.getStateDefinition().any().setValue(ONLINE, false));
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_CONTROLLER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(IS_TOP);
        builder.add(ONLINE);
        builder.add(IS_CONTROLLER);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Direction facing = Direction.NORTH;

        if (player != null) {
            Direction blockFacing = Direction.fromYRot(player.getYRot());
            facing = blockFacing.getOpposite();
        }

        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level instanceof ServerLevel serverLevel) {

            if (serverLevel.getBlockState(pos.below()).getBlock().equals(this)) {
                if (serverLevel.getBlockState(pos.below()).getValue(IS_TOP)) {
                    BlockState newState = state.setValue(IS_TOP, false);
                    serverLevel.setBlock(pos.below(), newState, 3);
                }

            }

        }
    }

    @Override
    public boolean onDestroyedByPlayer(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            boolean willHarvest,
            FluidState fluid
    ) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getBlockState(pos.below()).getBlock().equals(this)) {
                serverLevel.destroyBlock(pos.below(), true);
            }

            if (serverLevel.getBlockState(pos.above()).getBlock().equals(this)) {
                serverLevel.destroyBlock(pos.above(), true);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {

        if (level instanceof ServerLevel serverLevel) {

            if (serverLevel.getBlockState(pos.below()).getBlock().equals(this)) {
                if (!serverLevel.getBlockState(pos.below()).getValue(IS_TOP)) {

                    if (state.getValue(ONLINE)) {
                        BlockState topState = state.setValue(ONLINE, false);
                        serverLevel.setBlock(pos, topState, 3);

                        BlockState bottomState = state.setValue(ONLINE, false).setValue(IS_TOP, false);
                        serverLevel.setBlock(pos.below(), bottomState, 3);
                    } else {
                        BlockState topState = state.setValue(ONLINE, true);
                        serverLevel.setBlock(pos, topState, 3);
                        BlockState bottomState = state.setValue(ONLINE, true).setValue(IS_TOP, false);
                        serverLevel.setBlock(pos.below(), bottomState, 3);
                    }
                    return InteractionResult.SUCCESS;
                }
            }

        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
