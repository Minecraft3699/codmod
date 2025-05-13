package com.mc3699.codmod.entity.trialTrader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public record RepairTask(BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Player breaker) {}