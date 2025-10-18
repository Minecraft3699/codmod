package com.mc3699.codmod.entity;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import com.mc3699.codmod.handlers.NullFangCallbacks;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodEntities;

import javax.annotation.Nullable;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class NullFangHookEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(CodItems.NULLFANG.get());
    private static final float KNOCKBACK_MULTIPLIER = 0.6f;
    private static final float VERTICAL_KNOCKBACK = 0.1f;
    private int knockback = 0;

    public NullFangHookEntity(EntityType<?> type, Level world) {
        super((EntityType<? extends AbstractArrow>) type, world);
    }

    public NullFangHookEntity(EntityType<? extends AbstractArrow> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
        initializeKnockback(world, firedFromWeapon);
    }

    public NullFangHookEntity(EntityType<? extends AbstractArrow> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
        initializeKnockback(world, firedFromWeapon);
    }

    private void initializeKnockback(Level world, @Nullable ItemStack weapon) {
        if (weapon != null) {
            int knockbackLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
                    weapon
            );
            setKnockback(knockbackLevel);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(CodItems.NULLFANG.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    @Override
    protected void doKnockback(LivingEntity target, DamageSource source) {
        if (knockback > 0) {
            applyCustomKnockback(target);
        } else {
            super.doKnockback(target, source);
        }
    }

    private void applyCustomKnockback(LivingEntity target) {
        double resistance = Math.max(0.0, 1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        Vec3 direction = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize();
        Vec3 knockbackVec = direction.scale(knockback * KNOCKBACK_MULTIPLIER * resistance);

        if (knockbackVec.lengthSqr() > 0.0) {
            target.push(knockbackVec.x, VERTICAL_KNOCKBACK, knockbackVec.z);
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (result.getEntity() instanceof LivingEntity hitEntity && hitEntity.isInvulnerable()) {
            this.discard();
            return;
        }

        NullFangCallbacks.onHitEntity(this.level(), this.getX(), this.getY(), this.getZ(), result.getEntity(), this.getOwner());
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        NullFangCallbacks.onHitBlock(this.level(), result.getBlockPos(), this.getOwner());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }
    }

    public static NullFangHookEntity shoot(Level world, LivingEntity entity, RandomSource random) {
        return shoot(world, entity, random, 1.5f, 1, 0);
    }

    public static NullFangHookEntity shoot(Level world, LivingEntity entity, RandomSource random, float pullingPower) {
        return shoot(world, entity, random, pullingPower * 1.5f, 1, 0);
    }

    public static NullFangHookEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        NullFangHookEntity arrow = new NullFangHookEntity(CodEntities.NULLFANG_HOOK.get(), entity, world, null);
        Vec3 direction = entity.getViewVector(1);
        arrow.shoot(direction.x, direction.y, direction.z, power * 2, 0);
        arrow.setSilent(true);
        arrow.setCritArrow(false);
        arrow.setBaseDamage(damage);
        arrow.setKnockback(knockback);
        world.addFreshEntity(arrow);
        return arrow;
    }

    public static NullFangHookEntity shootAtTarget(LivingEntity shooter, LivingEntity target) {
        NullFangHookEntity arrow = new NullFangHookEntity(CodEntities.NULLFANG_HOOK.get(), shooter, shooter.level(), null);

        double dx = target.getX() - shooter.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - shooter.getZ();
        double horizontalDistance = Math.hypot(dx, dz);

        arrow.shoot(dx, dy - arrow.getY() + horizontalDistance * 0.2F, dz, 1.5f * 2, 12.0F);
        arrow.setSilent(true);
        arrow.setBaseDamage(1);
        arrow.setKnockback(0);
        arrow.setCritArrow(false);
        shooter.level().addFreshEntity(arrow);
        return arrow;
    }
}