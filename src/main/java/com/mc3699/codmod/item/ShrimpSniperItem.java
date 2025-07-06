package com.mc3699.codmod.item;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShrimpSniperItem extends Item {
    public ShrimpSniperItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 1000000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        ItemProjectileEntity projectile = new ItemProjectileEntity(
                CodEntities.ITEM_PROJECTILE.get(),
                level,
                new ItemStack(CodItems.SHRIMP.get(), 1),
                0,
                100,
                true
        );
        Vec3 eyePos = livingEntity.getEyePosition();
        Vec3 lookVec = livingEntity.getViewVector(1.0F).normalize().scale(0.8);
        projectile.setPos(eyePos.x + lookVec.x, eyePos.y + lookVec.y, eyePos.z + lookVec.z);
        projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 1f, 5, 0f);
        level.addFreshEntity(projectile);
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

}
