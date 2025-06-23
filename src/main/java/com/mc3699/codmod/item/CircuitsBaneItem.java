package com.mc3699.codmod.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

public class CircuitsBaneItem extends SwordItem {

    public CircuitsBaneItem(Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        entity.remove(Entity.RemovalReason.KILLED);
        return super.onLeftClickEntity(stack, player, entity);
    }
}
