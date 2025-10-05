package com.mc3699.codmod.entity.itemProjectile;

import com.mc3699.codmod.registry.CodDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ItemProjectileEntity extends AbstractArrow {
    private static final EntityDataAccessor<Integer> BOUNCE_COUNT = SynchedEntityData.defineId(
            ItemProjectileEntity.class,
            EntityDataSerializers.INT
    );

    private static final EntityDataAccessor<Integer> MAX_BOUNCE = SynchedEntityData.defineId(
            ItemProjectileEntity.class,
            EntityDataSerializers.INT
    );

    private static final EntityDataAccessor<ItemStack> CARRIED_ITEM = SynchedEntityData.defineId(
            ItemProjectileEntity.class,
            EntityDataSerializers.ITEM_STACK
    );
    private static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(
            ItemProjectileEntity.class,
            EntityDataSerializers.INT
    );
    private static final EntityDataAccessor<Boolean> SHOULD_DROP = SynchedEntityData.defineId(
            ItemProjectileEntity.class,
            EntityDataSerializers.BOOLEAN
    );
    private static final double SPEED_THRESHOLD = 0.0001;
    private Vec3 lastPosition;


    public ItemProjectileEntity(
            EntityType<? extends ItemProjectileEntity> entityType,
            Level level,
            ItemStack carriedItem,
            int bounceCount,
            int damage,
            boolean dropItem
    ) {
        super(entityType, level);
        this.setCarriedItem(carriedItem.copy());
        this.getEntityData().set(MAX_BOUNCE, bounceCount);
        this.getEntityData().set(DAMAGE, damage);
        this.getEntityData().set(SHOULD_DROP, dropItem);
        this.setCustomName(carriedItem.getDisplayName());
        this.lastPosition = this.position();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOUNCE_COUNT, 0);
        builder.define(MAX_BOUNCE, 0);
        builder.define(CARRIED_ITEM, ItemStack.EMPTY);
        builder.define(DAMAGE, 0);
        builder.define(SHOULD_DROP, false);
    }

    public ItemStack getCarriedItem() {
        return this.getEntityData().get(CARRIED_ITEM).copy();
    }

    private void setCarriedItem(ItemStack item) {
        this.getEntityData().set(CARRIED_ITEM, item.copy());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide) {

            int bounces = getEntityData().get(BOUNCE_COUNT);
            int maxBounce = getEntityData().get(MAX_BOUNCE);

            setOnGround(false);
            getEntityData().set(BOUNCE_COUNT, bounces + 1);
            Vec3 velocity = getDeltaMovement();
            Vec3 normal = Vec3.atLowerCornerOf(result.getDirection().getNormal());
            double dot = velocity.dot(normal);
            setPos(position().add(normal.scale(0.5)));
            Vec3 reflected = velocity.subtract(normal.scale(2.0 * dot));
            setDeltaMovement(reflected);


            if(bounces >= maxBounce)
            {
                ServerLevel serverLevel = (ServerLevel) this.level();
                ItemStack item = this.getCarriedItem();
                if (!item.isEmpty() && this.getEntityData().get(SHOULD_DROP)) {
                    serverLevel.addFreshEntity(new ItemEntity(
                            serverLevel,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            item
                    ));
                }
                this.discard();
            }

        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity entity = result.getEntity();

            if (getEntityData().get(DAMAGE) > 0) {
                DamageSource damage = new DamageSource(
                        serverLevel
                                .registryAccess()
                                .lookupOrThrow(Registries.DAMAGE_TYPE)
                                .getOrThrow(CodDamageTypes.ITEM_PROJECTILE), null, this
                );

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
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            Vec3 currentPosition = this.position();
            double speed = currentPosition.distanceTo(lastPosition);
            this.lastPosition = currentPosition;
            if ((this.inGround || speed < SPEED_THRESHOLD) &&
                !this.isNoPhysics() &&
                getEntityData().get(BOUNCE_COUNT) > 0) {
                if (this.getEntityData().get(SHOULD_DROP)) {
                    ItemStack item = this.getCarriedItem();
                    if (!item.isEmpty()) {
                        ServerLevel serverLevel = (ServerLevel) this.level();
                        serverLevel.addFreshEntity(new ItemEntity(
                                serverLevel,
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                item
                        ));
                    }
                }
                this.discard();
            }

            if(tickCount > 300)
            {

                if(entityData.get(SHOULD_DROP))
                {
                    ItemStack item = this.getCarriedItem();
                    ServerLevel serverLevel = (ServerLevel) this.level();
                    serverLevel.addFreshEntity(new ItemEntity(
                            serverLevel,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            item
                    ));
                }
                this.discard();
            }

        }
    }

    @Override
    public float getPickRadius() {
        return 0.5F;
    }

    //bug fixed :3 it should be safe to save and throw these mfs through nether portals now
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {

        compound.putByte("inBlockState", (byte) 0);
        compound.putByte("pickup", (byte) 0); // No pickup
        compound.putDouble("damage", 0.0D);
        compound.putBoolean("crit", false);
        compound.putByte("PierceLevel", (byte) 0);
        compound.putString("SoundEvent", "minecraft:entity.arrow.hit");
        compound.putBoolean("ShotFromCrossbow", false);

        ItemStack carriedItem = getCarriedItem();
        if (!carriedItem.isEmpty()) {
            compound.put("CarriedItem", carriedItem.save(this.registryAccess()));
        }

        compound.putInt("BounceCount", this.getEntityData().get(BOUNCE_COUNT));
        compound.putInt("MaxBounce", this.getEntityData().get(MAX_BOUNCE));
        compound.putInt("Damage", this.getEntityData().get(DAMAGE));
        compound.putBoolean("ShouldDrop", this.getEntityData().get(SHOULD_DROP));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("CarriedItem", 10)) {
            CompoundTag itemTag = compound.getCompound("CarriedItem");
            ItemStack carriedItem = ItemStack.parseOptional(this.registryAccess(), itemTag);
            this.setCarriedItem(carriedItem);
        }

        if (compound.contains("BounceCount")) {
            this.getEntityData().set(BOUNCE_COUNT, compound.getInt("BounceCount"));
        }
        if (compound.contains("MaxBounce")) {
            this.getEntityData().set(MAX_BOUNCE, compound.getInt("MaxBounce"));
        }
        if (compound.contains("Damage")) {
            this.getEntityData().set(DAMAGE, compound.getInt("Damage"));
        }
        if (compound.contains("ShouldDrop")) {
            this.getEntityData().set(SHOULD_DROP, compound.getBoolean("ShouldDrop"));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        ItemStack carried = getCarriedItem();
        return carried.isEmpty() ? ItemStack.EMPTY : carried;
    }
}