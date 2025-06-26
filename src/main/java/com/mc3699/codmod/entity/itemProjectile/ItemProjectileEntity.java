package com.mc3699.codmod.entity.itemProjectile;

import com.mc3699.codmod.registry.CodDamageTypes;
import com.mc3699.codmod.registry.CodEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;

public class ItemProjectileEntity extends AbstractArrow {
    private static final EntityDataAccessor<Integer> BOUNCE_COUNT = SynchedEntityData.defineId(ItemProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> CARRIED_ITEM = SynchedEntityData.defineId(ItemProjectileEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(ItemProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHOULD_DROP = SynchedEntityData.defineId(ItemProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final int MAX_BOUNCES = 0;
    private static final double SPEED_THRESHOLD = 0.01;
    private Vec3 lastPosition;


    public ItemProjectileEntity(EntityType<? extends ItemProjectileEntity> entityType, Level level, ItemStack carriedItem, int bounceCount, int damage, boolean dropItem) {
        super(entityType, level);
        this.setCarriedItem(carriedItem.copy());
        this.getEntityData().set(BOUNCE_COUNT, bounceCount);
        this.getEntityData().set(DAMAGE, damage);
        this.getEntityData().set(SHOULD_DROP, dropItem);
        this.setCustomName(carriedItem.getDisplayName());
        this.lastPosition = this.position();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOUNCE_COUNT, 0);
        builder.define(CARRIED_ITEM, ItemStack.EMPTY);
        builder.define(DAMAGE, 0);
        builder.define(SHOULD_DROP, false);
    }

    private void setCarriedItem(ItemStack item) {
        this.getEntityData().set(CARRIED_ITEM, item.copy());
    }

    public ItemStack getCarriedItem() {
        return this.getEntityData().get(CARRIED_ITEM).copy();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide) {
            int currentBounce = this.getEntityData().get(BOUNCE_COUNT);
            if (currentBounce < MAX_BOUNCES) {
                this.inGround = false;
                this.getEntityData().set(BOUNCE_COUNT, currentBounce + 1);
                Vec3 velocity = this.getDeltaMovement();
                Vec3 normal = new Vec3(result.getDirection().getNormal().getX(), result.getDirection().getNormal().getY(), result.getDirection().getNormal().getZ());
                double dot = velocity.dot(normal);
                Vec3 reflected = velocity.subtract(normal.scale(2.0 * dot));
                this.setDeltaMovement(reflected);
                this.setPos(this.position().add(normal.scale(0.2)));
            } else {
                ServerLevel serverLevel = (ServerLevel) this.level();
                ItemStack item = this.getCarriedItem();
                if (!item.isEmpty() && this.getEntityData().get(SHOULD_DROP)) {
                    serverLevel.addFreshEntity(new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), item));
                }
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity entity = result.getEntity();

            if(getEntityData().get(DAMAGE) > 0)
            {
                DamageSource damage = new DamageSource(serverLevel
                        .registryAccess()
                        .lookupOrThrow(Registries.DAMAGE_TYPE)
                        .getOrThrow(CodDamageTypes.ITEM_PROJECTILE), null, this);

                entity.hurt(damage, getEntityData().get(DAMAGE));
            }

            if (entity instanceof Player player && player != this.getOwner()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    ItemStack item = this.getCarriedItem();
                    if (!item.isEmpty() && this.getEntityData().get(SHOULD_DROP)) {
                        serverPlayer.addItem(item);
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        return target.isAlive() && !(target instanceof ItemEntity);
    }



    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BounceCount", this.getEntityData().get(BOUNCE_COUNT));
        ItemStack item = this.getCarriedItem();
        if (!item.isEmpty()) {
            compound.put("CarriedItem", item.save(this.registryAccess()));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            Vec3 currentPosition = this.position();
            double speed = currentPosition.distanceTo(lastPosition);
            this.lastPosition = currentPosition;
            if ((this.inGround || speed < SPEED_THRESHOLD) && !this.isNoPhysics() && getEntityData().get(BOUNCE_COUNT) > 0) {
                if (this.getEntityData().get(SHOULD_DROP)) {
                    ItemStack item = this.getCarriedItem();
                    if (!item.isEmpty()) {
                        ServerLevel serverLevel = (ServerLevel) this.level();
                        serverLevel.addFreshEntity(new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), item));
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(BOUNCE_COUNT, compound.getInt("BounceCount"));
        if (compound.contains("CarriedItem")) {
            ItemStack item = ItemStack.parse(this.registryAccess(), compound.getCompound("CarriedItem")).orElse(ItemStack.EMPTY);
            this.setCarriedItem(item);
        }
    }

    @Override
    public float getPickRadius() {
        return 0.5F;
    }
}