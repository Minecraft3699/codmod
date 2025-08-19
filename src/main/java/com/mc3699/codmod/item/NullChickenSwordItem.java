package com.mc3699.codmod.item;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NullChickenSwordItem extends SwordItem {

    public NullChickenSwordItem(Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return damage * 13;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("ERR.NULLCHICKEN"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        //return super.use(level, player, usedHand);
        for (int i = 0; i < 2; i++) {
            ItemStack randomStack = new ItemStack(CodItems.NULL_CHICKEN.get(), 10);

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
            if(level instanceof ServerLevel serverLevel)
            {
                serverLevel.playSound(null, player.blockPosition(), CodSounds.NULL_CHICKEN_BLAST.value(), SoundSource.MASTER, 2, 1);
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
