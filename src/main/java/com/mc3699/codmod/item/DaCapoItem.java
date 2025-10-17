package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DaCapoItem extends SwordItem {

    private static final Tier TOOL_TIER = new Tier() {
        @Override
        public int getUses() {
            return 0;
        }

        @Override
        public float getSpeed() {
            return 0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_WOODEN_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 24;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of();
        }
    };

    public DaCapoItem() {
        super(TOOL_TIER, new Item.Properties()
                .attributes(SwordItem.createAttributes(TOOL_TIER, 5f, -1.5f))
                .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        onLivingEntityHit(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return retval;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        onRightClick(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
        return ar;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("???").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Made by BigManRake - fixed1 by alex - Fixed2 and ported to codmod by Eyae").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
        boolean retval = super.onEntitySwing(itemstack, entity, hand);
        onEntitySwing(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        return retval;
    }

    private static void onRightClick(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;

        playSound(world, x, y, z, "codmod:orchwork", SoundSource.NEUTRAL, 0.4f, 1f);

        for (Entity entityIterator : world.getEntities(entity, new AABB(x + 14, y + 14, z + 14, x - 14, y - 14, z - 14))) {
            if (entityIterator instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
                livingEntity.addEffect(new MobEffectInstance(CodMobEffects.INSANITY, 300, 0));
            }
        }

        spawnParticles(world, x, y, z, 200, 14);

        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 650);
        }
    }

    private static void onLivingEntityHit(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        LivingEntity livingEntity = (LivingEntity) entity;
        float health = livingEntity.getHealth();
        float maxHealth = livingEntity.getMaxHealth();

        if (health > 0) {
            int pitch = Mth.nextInt(RandomSource.create(), 8, 12);
            playSound(world, x, y, z, "codmod:capohit", SoundSource.PLAYERS, 0.2f, pitch / 10f);
        } else {
            int pitch = Mth.nextInt(RandomSource.create(), 8, 12);
            playSound(world, x, y, z, "codmod:capokill", SoundSource.PLAYERS, 0.2f, pitch / 10f);
        }

        if (health <= maxHealth * 0.4f && !livingEntity.level().isClientSide()) {
            livingEntity.addEffect(new MobEffectInstance(CodMobEffects.INSANITY, 160, 0));
        }
    }

    private static void onEntitySwing(LevelAccessor world, double x, double y, double z) {
        int pitch = Mth.nextInt(RandomSource.create(), 8, 12);
        playSound(world, x, y, z, "codmod:caposwing", SoundSource.PLAYERS, 0.4f, pitch / 10f);
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String soundId, SoundSource source, float volume, float pitch) {
        if (world instanceof Level level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            if (!level.isClientSide()) {
                level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)), source, volume, pitch);
            } else {
                level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)), source, volume, pitch, false);
            }
        }
    }

    private static void spawnParticles(LevelAccessor world, double x, double y, double z, int amount, double radius) {
        for (int i = 0; i < amount; i++) {
            double offsetX = Mth.nextDouble(RandomSource.create(), -1, 1) * radius;
            double offsetY = Mth.nextDouble(RandomSource.create(), -1, 1) * radius;
            double offsetZ = Mth.nextDouble(RandomSource.create(), -1, 1) * radius;
            double velX = Mth.nextDouble(RandomSource.create(), -0.05, 0.05);
            double velY = Mth.nextDouble(RandomSource.create(), -0.05, 0.05);
            double velZ = Mth.nextDouble(RandomSource.create(), -0.05, 0.05);

            world.addParticle(ParticleTypes.NOTE, x + offsetX, y + offsetY, z + offsetZ, velX, velY, velZ);
        }
    }
}