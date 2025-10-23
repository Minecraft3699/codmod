package com.mc3699.codmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CodKnifeItem extends SwordItem {
    public CodKnifeItem(Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(SwordItem.createAttributes(Tiers.NETHERITE, 100, 10)));
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        // Spawns cod on hit
        if (!level.isClientSide()) {
            for (int i = 0; i < 5; i++) {
                Cod entity = new Cod(EntityType.COD, level);
                entity.setPos(target.getX(), target.getY(), target.getZ());
                level.addFreshEntity(entity);
                Random rand = new Random();
                Vec3 motion = target.getDeltaMovement().add(rand.nextFloat(1), rand.nextFloat(1), rand.nextFloat(1));
                entity.setDeltaMovement(motion);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            for (int i = 0; i < 5; i++) {
            Cod entity = new Cod(EntityType.COD, level);
            entity.setPos(player.getX(), player.getY(), player.getZ());
            level.addFreshEntity(entity);
            Random rand = new Random();
            Vec3 motion = player.getDeltaMovement().add(rand.nextFloat(1), rand.nextFloat(1), rand.nextFloat(1));
            entity.setDeltaMovement(motion);
            }

        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("The sharpest fish you'll ever hold."));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}


