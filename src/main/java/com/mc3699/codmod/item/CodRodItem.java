package com.mc3699.codmod.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;

public class CodRodItem extends Item {
    public CodRodItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.level() instanceof ServerLevel serverLevel)
        {
            Vec3 codSpawnPos = target.getPosition(0);
            target.remove(Entity.RemovalReason.DISCARDED);
            Cod cod = new Cod(EntityType.COD, serverLevel);
            serverLevel.addFreshEntity(cod);
            cod.setPos(codSpawnPos);
        }
        return false;
    }
}
