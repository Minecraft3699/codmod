package com.mc3699.codmod.event;

import com.mc3699.codmod.block.johnGeometry.JohnGeometryBlock;
import com.mc3699.codmod.registry.CodBlocks;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class JohnGeometryEvent extends BaseEvent {
    public JohnGeometryEvent() {
        super(2);
    }

    public static void placeJohn(ServerLevel level, BlockPos pos, int radius) {
        BlockPos closestDoor = null;
        double minDistance = Double.MAX_VALUE;

        for (BlockPos checkPos : BlockPos.betweenClosed(
                pos.offset(-radius, -radius, -radius),
                pos.offset(radius, radius, radius)
        )) {
            BlockState state = level.getBlockState(checkPos);
            if (state.getBlock() instanceof DoorBlock && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                double distance = pos.distSqr(checkPos);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestDoor = checkPos.immutable();
                }
            }
        }

        if (closestDoor != null) {
            BlockState doorState = level.getBlockState(closestDoor);
            var facing = doorState.getValue(DoorBlock.FACING);
            var hinge = doorState.getValue(DoorBlock.HINGE);
            boolean open = doorState.getValue(DoorBlock.OPEN);
            BlockPos placePos = closestDoor.relative(facing, open ? (hinge == DoorHingeSide.LEFT ? 2 : -2) : -2);
            level.setBlock(
                    placePos,
                    CodBlocks.JOHN_GEOMETRY.get().defaultBlockState().setValue(JohnGeometryBlock.FACING, facing),
                    3
            );
        }
    }

    @Override
    protected void execute(@NotNull ServerLevel serverLevel, @NotNull ServerPlayer serverPlayer, @NotNull Vec3 vec3) {
        serverLevel.players().forEach(victim -> {
            if (serverLevel.random.nextInt(1, 10) > 0) {
                placeJohn(serverLevel, victim.getBlockPosBelowThatAffectsMyMovement(), 30);
            }
        });


    }
}
