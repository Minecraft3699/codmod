package com.mc3699.codmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import com.mc3699.codmod.handlers.GrapplingHookCallbacks;
import com.mc3699.codmod.entity.NullFangHookEntity;

import java.util.List;

public class NullFangItem extends Item {
    private static final int ATTACK_DAMAGE = 9;
    private static final double ATTACK_SPEED_MODIFIER = -2.4;
    private static final int MAX_STACK_SIZE = 1;
    private static final int DRAW_TIME_TICKS = 60;
    private static final float MIN_DRAW_POWER = 0.1f;

    public NullFangItem(Item.Properties properties) {
        super(properties
                .stacksTo(MAX_STACK_SIZE)
                .rarity(Rarity.EPIC)
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, ATTACK_SPEED_MODIFIER, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .build())
                .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
        );
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack, LivingEntity livingEntity) {
        return DRAW_TIME_TICKS;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("Made for Pinky - Initally Made by BigManRake - Fixed and UnMcreatored by Eyae").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("A magenta-swirled harpoon-like growth. Venemous fang, seething with corruption.").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Allows the user to grapple enemies as well as surfaces. (Sneak to unstuck yourself)").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player.getAbilities().instabuild || hasAmmo(player)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity hitEntity, LivingEntity attacker) {
        boolean result = super.hurtEnemy(itemstack, hitEntity, attacker);
        GrapplingHookCallbacks.onMeleeHit(hitEntity.level(), hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(), hitEntity, attacker);
        return result;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);

        if (entity instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            boolean isInHand = mainHand == itemstack || offHand == itemstack;

            if (!isInHand && GrapplingHookCallbacks.isPlayerGrappling(entity)) {
                GrapplingHookCallbacks.setPlayerGrappling(entity, false);
            }
        }

        GrapplingHookCallbacks.onInventoryTick(entity);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int chargeTime) {
        if (world.isClientSide() || !(entity instanceof ServerPlayer player)) {
            return;
        }

        float drawPower = BowItem.getPowerForTime(DRAW_TIME_TICKS - chargeTime);

        if (drawPower < MIN_DRAW_POWER) {
            return;
        }

        ItemStack ammo = findAmmo(player);
        if (!player.getAbilities().instabuild && ammo.isEmpty()) {
            return;
        }
        NullFangHookEntity hook = NullFangHookEntity.shoot(world, entity, world.getRandom(), drawPower);

        if (player.getAbilities().instabuild) {
            hook.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        } else {
            consumeAmmo(ammo, world, player);
        }

        GrapplingHookCallbacks.onRangedShot(world, entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    private ItemStack findAmmo(Player player) {
        return new ItemStack(NullFangHookEntity.PROJECTILE_ITEM.getItem());
    }

    private boolean hasAmmo(Player player) {
        return !findAmmo(player).isEmpty();
    }

    private void consumeAmmo(ItemStack ammo, Level world, ServerPlayer player) {
        if (ammo.isDamageableItem()) {
            if (world instanceof ServerLevel serverLevel) {
                ammo.hurtAndBreak(1, serverLevel, player, stack -> {});
            }
        } else {
            ammo.shrink(1);
        }
    }
}