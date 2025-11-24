package com.mc3699.codmod.block.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ServerBlock extends Block {

    public enum VerticalPosition implements StringRepresentable {
        SINGLE("single"),
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;

        VerticalPosition(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final EnumProperty<VerticalPosition> VERTICAL_POSITION = EnumProperty.create("vertical_position", VerticalPosition.class);
    public static final BooleanProperty ONLINE = BooleanProperty.create("online");

    public ServerBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(VERTICAL_POSITION, VerticalPosition.SINGLE)
                .setValue(ONLINE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, VERTICAL_POSITION, ONLINE);
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
            updateVerticalPosition(level, pos);
            updateVerticalPosition(level, pos.above());
            updateVerticalPosition(level, pos.below());
        }
    }

    private void updateVerticalPosition(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof ServerBlock)) {
            return;
        }

        boolean hasBelow = level.getBlockState(pos.below()).getBlock() instanceof ServerBlock;
        boolean hasAbove = level.getBlockState(pos.above()).getBlock() instanceof ServerBlock;

        VerticalPosition newPosition;
        if (!hasBelow && !hasAbove) {
            newPosition = VerticalPosition.SINGLE;
        } else if (!hasBelow && hasAbove) {
            newPosition = VerticalPosition.BOTTOM;
        } else if (hasBelow && hasAbove) {
            newPosition = VerticalPosition.MIDDLE;
        } else {
            newPosition = VerticalPosition.TOP;
        }

        if (state.getValue(VERTICAL_POSITION) != newPosition) {
            level.setBlock(pos, state.setValue(VERTICAL_POSITION, newPosition), 3);
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
        BlockPos above = pos.above();
        BlockPos below = pos.below();

        boolean result = super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);

        if (!level.isClientSide()) {
            updateVerticalPosition(level, above);
            updateVerticalPosition(level, below);
        }
        return result;
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
            boolean newOnline = !state.getValue(ONLINE);
            toggleConnectedBlocks(level, pos, newOnline);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    private void toggleConnectedBlocks(Level level, BlockPos startPos, boolean online) {
        BlockPos pos = startPos;
        while (level.getBlockState(pos.below()).getBlock() instanceof ServerBlock) {
            pos = pos.below();
        }

        while (level.getBlockState(pos).getBlock() instanceof ServerBlock) {
            BlockState state = level.getBlockState(pos);
            level.setBlock(pos, state.setValue(ONLINE, online), 3);
            pos = pos.above();
        }
    }
}
