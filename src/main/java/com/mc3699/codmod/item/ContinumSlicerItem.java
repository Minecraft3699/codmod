package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodMobEffects;
import com.mc3699.codmod.registry.CodParticles;
import com.mc3699.codmod.registry.CodTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class ContinumSlicerItem extends SwordItem {
    public ContinumSlicerItem(Properties properties) {
        super(CodTiers.CONTINUM_SLICER_TIER, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level instanceof ServerLevel serverLevel) {
            double strength = 1.5;
            Vec3 lookVec = player.getLookAngle();
            player.setDeltaMovement(lookVec.x * strength, lookVec.y * strength, lookVec.z * strength);
            player.hurtMarked = true;
            player.getCooldowns().addCooldown(this, 40);
            player.getPersistentData().putBoolean("NoFall",true);
            serverLevel.sendParticles(
                    CodParticles.SPACE_TIME_PARTICLE.get(), player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, 8, 0.0, 1.0, 0.0, 0.5 );
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("Made for NahBro2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Model by Ebridger").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "scythe_speed")
                                , -2.5, AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) { // i am confused on what this does
        return damage * 8;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        target.hurtMarked = true;

        if (attacker instanceof Player player && player.fallDistance > 0.0F && !player.onGround() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS)) {
            Vec3 direction = attacker.position().subtract(target.position()).normalize();

            double strength = 1.0;
            target.setDeltaMovement(
                    target.getDeltaMovement().x + direction.x * strength,
                    target.getDeltaMovement().y + direction.y * strength,
                    target.getDeltaMovement().z + direction.z * strength
            );

            target.addEffect(new MobEffectInstance(CodMobEffects.BLEEDING, 300, 0));
            attacker.hurt(attacker.damageSources().generic(), 1.0F);
        }

        return true;
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String soundId, SoundSource source, float volume, float pitch) { //lokey reused decapo code since lazy
        if (world instanceof Level level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            if (!level.isClientSide()) {
                level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)), source, volume, pitch);
            } else {
                level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)), source, volume, pitch, false);
            }
        }
    }
    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
        int pitch = Mth.nextInt(RandomSource.create(), 8, 12);
        playSound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "codmod:scythe_slash", SoundSource.PLAYERS, 0.4f, pitch / 10f);
        return true;
    }
}