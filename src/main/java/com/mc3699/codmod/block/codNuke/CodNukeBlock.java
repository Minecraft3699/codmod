package com.mc3699.codmod.block.codNuke;

import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class CodNukeBlock extends Block implements EntityBlock {
    public CodNukeBlock(Properties properties) {
        super(properties.noOcclusion().destroyTime(990000000).strength(1000000000).pushReaction(PushReaction.IGNORE).explosionResistance(1000000000).sound(SoundType.ANVIL));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CodNukeBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return blockEntityType == CodBlockEntities.COD_NUKE.get() ? CodNukeBlockEntity::tick : null;
    }
}
