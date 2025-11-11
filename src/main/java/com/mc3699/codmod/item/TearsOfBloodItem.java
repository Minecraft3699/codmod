package com.mc3699.codmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import java.util.List;

public class TearsOfBloodItem extends SwordItem {
    public TearsOfBloodItem(Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(SwordItem.createAttributes(Tiers.NETHERITE, 3, -2.4F)));
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            Vec3 lookVec = player.getLookAngle();
            Vec3 startPos = player.getEyePosition();
            Vec3 endPos = startPos.add(lookVec.scale(3.0));

            AABB searchBox = new AABB(startPos, endPos).inflate(2.0);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
                    entity -> entity != player && player.hasLineOfSight(entity));

            for (LivingEntity entity : entities) {
                entity.hurt(level.damageSources().playerAttack(player), 6.0F); // First slash
                entity.hurt(level.damageSources().playerAttack(player), 6.0F); // Second slash
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0));
            }

            spawnSlashParticles(level, player, lookVec, startPos);

            level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,
                    SoundSource.PLAYERS, 1.0F, 1.0F);

            player.getCooldowns().addCooldown(this, 40);
        }

        if (level.isClientSide) {
            spawnSlashParticles(level, player, player.getLookAngle(), player.getEyePosition());
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private void spawnSlashParticles(Level level, Player player, Vec3 lookVec, Vec3 startPos) {
        Vec3 rightVec = lookVec.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 upVec = lookVec.cross(rightVec).normalize();

        // First slash - diagonal line (top-left to bottom-right)
        for (int i = 0; i < 25; i++) {
            double progress = i / 25.0;

            Vec3 particlePos = startPos
                    .add(lookVec.scale(2.0))
                    .add(rightVec.scale(-1.0 + progress * 2.0))
                    .add(upVec.scale(1.0 - progress * 2.0));

            level.addParticle(ParticleTypes.FLAME,
                    particlePos.x, particlePos.y, particlePos.z,
                    0, 0, 0);
        }

        // Second slash - diagonal line (top-right to bottom-left)
        for (int i = 0; i < 25; i++) {
            double progress = i / 25.0;

            Vec3 particlePos = startPos
                    .add(lookVec.scale(2.0))
                    .add(rightVec.scale(1.0 - progress * 2.0))
                    .add(upVec.scale(1.0 - progress * 2.0));

            level.addParticle(ParticleTypes.FLAME,
                    particlePos.x, particlePos.y, particlePos.z,
                    0, 0, 0);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Tears of blood, from the dead."));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}