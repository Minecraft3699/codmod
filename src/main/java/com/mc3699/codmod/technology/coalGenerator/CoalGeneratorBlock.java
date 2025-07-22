package com.mc3699.codmod.technology.coalGenerator;

import com.mc3699.codmod.block.mantleKey.MantleKeyBlock;
import com.mc3699.codmod.technology.foundation.BaseMachineBlock;
import com.mc3699.codmod.technology.foundation.BaseMachineMenu;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlock extends BaseMachineBlock implements EntityBlock {
    public CoalGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CoalGeneratorBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return CoalGeneratorBlockEntity::tick;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level instanceof ServerLevel serverLevel)
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof  CoalGeneratorBlockEntity coalGeneratorBlockEntity)
            {
                MenuProvider provider = new SimpleMenuProvider(
                        (id, inv, entity) -> new CoalGeneratorMenu(id, coalGeneratorBlockEntity, coalGeneratorBlockEntity.getData(), ContainerLevelAccess.create(serverLevel, pos)),
                        Component.literal("Coal Generator")
                );
                player.openMenu(provider);
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
