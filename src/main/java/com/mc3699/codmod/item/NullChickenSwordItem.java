package com.mc3699.codmod.item;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NullChickenSwordItem extends Item {
    public NullChickenSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        //return super.use(level, player, usedHand);
        for (int i = 0; i < 2; i++) {
            ItemStack randomStack = new ItemStack(CodItems.NULL_CHICKEN.get(), 1);

            ItemProjectileEntity projectile = new ItemProjectileEntity(
                    CodEntities.ITEM_PROJECTILE.get(),
                    level,
                    randomStack,
                    5,
                    10,
                    false
            );
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getViewVector(1.0F).normalize().scale(0.8);
            projectile.setPos(eyePos.x + lookVec.x, eyePos.y + lookVec.y, eyePos.z + lookVec.z);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 1f, 2.5f, 4f);
            level.addFreshEntity(projectile);
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }
}
