package com.mc3699.codmod.item;

import dev.wendigodrip.thebrokenscript.api.entity.base.BaseCircuitEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class CircuitsBaneItem extends SwordItem {

    UUID ancientUUID = UUID.fromString("743e3214-5958-4076-9876-a05337a84749");

    public CircuitsBaneItem(Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if(entity instanceof Player player)
        {

            if(player.hasPermissions(2) || player.getUUID().equals(ancientUUID))
            {
                if(entity.tickCount % 10 == 0 && level instanceof ServerLevel serverLevel)
                {
                    AABB circuitSearch = entity.getBoundingBox().inflate(8);
                    List<BaseCircuitEntity> circuits = serverLevel.getEntitiesOfClass(BaseCircuitEntity.class, circuitSearch);
                    if(!circuits.isEmpty())
                    {
                        if(isSelected)
                        {
                            circuits.forEach((circuitEntity -> {
                                circuitEntity.setNoAi(true);
                            }));
                        }

                        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                    } else {
                        stack.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
                    }
                }
            }

        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof BaseCircuitEntity circuitEntity)
        {
            circuitEntity.remove(Entity.RemovalReason.KILLED);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
