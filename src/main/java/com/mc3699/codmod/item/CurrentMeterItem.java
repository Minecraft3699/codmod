package com.mc3699.codmod.item;

import com.mc3699.codmod.technology.foundation.BaseCableBlockEntity;
import com.mc3699.codmod.technology.foundation.BaseMachineBlockEntity;
import com.mc3699.codmod.technology.lowVoltageCable.LowVoltageCableBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CurrentMeterItem extends Item {
    public CurrentMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();;

        if(context.getLevel() instanceof ServerLevel serverLevel)
        {

            BlockEntity blockEntity = serverLevel.getBlockEntity(clickedPos);

            if(blockEntity instanceof BaseMachineBlockEntity baseMachineBlockEntity)
            {
                int energy = baseMachineBlockEntity.getEnergyStored();
                if(context.getPlayer() != null)
                {
                    context.getPlayer().sendSystemMessage(Component.literal("Machine: "+energy));
                }
            }

            if(blockEntity instanceof BaseCableBlockEntity cableBlockEntity)
            {
                int energy = cableBlockEntity.getEnergyCap(cableBlockEntity, Direction.UP).getEnergyStored();
                if(context.getPlayer() != null)
                {
                    context.getPlayer().sendSystemMessage(Component.literal("Cable: "+energy));
                }
            }
        }

        return super.useOn(context);
    }
}
