package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class PsycheEthosGunItem extends Item {
    //Custom weapon made for Lost/mgwew2

    public PsycheEthosGunItem(Properties properties) {
        super(properties);
    }


    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("Made for Mgwew2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Model by Reverister").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Implemented by Deli_Shoes").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(player.level() instanceof ServerLevel serverLevel)
        {
            ItemStack ammo = null;
            if (player.hasInfiniteMaterials()) {
                fireRaycast(serverLevel, player);
                serverLevel.playSound(null, player.blockPosition(), CodSounds.GUNSHOT.value(), SoundSource.MASTER, 1, 1);
                player.getCooldowns().addCooldown(this, 20);
            } else if (player.getInventory().contains(Tags.Items.NUGGETS)) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack itemstack1 = player.getInventory().getItem(i);
                    if (itemstack1.is(Tags.Items.NUGGETS)) {
                        ammo = itemstack1;
                    }
                }
                if (ammo != null) {
                    ammo.shrink(1);
                    fireRaycast(serverLevel, player);
                    serverLevel.playSound(null, player.blockPosition(), CodSounds.GUNSHOT.value(), SoundSource.MASTER, 1, 1);
                    player.getCooldowns().addCooldown(this.asItem(), 20);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    //I'm sorry minecraft
    private void fireRaycast(ServerLevel serverLevel, Player player)
    {
        double maxRange = 250;
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 angle = player.getLookAngle().scale(maxRange);
        Vec3 end = start.add(angle.scale(maxRange));

        BlockHitResult blockHitResult = serverLevel.clip(new ClipContext(
                start,
                angle,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        AABB hitZone = player.getBoundingBox().expandTowards(angle.scale(maxRange));
        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(player, start, end, hitZone, (entity -> true), 1000000000);

        serverLevel.sendParticles(ParticleTypes.CRIT,
                (start.x + end.x) / 2,
                (start.y + end.y) / 2,
                (start.z + end.z) / 2,
                10,
                (end.x - start.x) / 2,
                (end.y - start.y) / 2,
                (end.z - start.z) / 2,
                0.1
        );

        if (hitResult != null) {
            hitResult.getEntity().hurt(serverLevel.damageSources().playerAttack(player), 10);
        }
    }
}
