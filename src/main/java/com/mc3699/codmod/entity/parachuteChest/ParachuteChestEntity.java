package com.mc3699.codmod.entity.parachuteChest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ParachuteChestEntity extends Entity {

    private ItemStackHandler items;

    public ParachuteChestEntity(EntityType<ParachuteChestEntity> type, Level level, ItemStackHandler items) {
        super(type, level);
        this.items = items;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (position().y > 128) {
            move(MoverType.SELF, new Vec3(0, -0.8f, 0));
        } else {
            move(MoverType.SELF, new Vec3(0, -0.2f, 0));
        }


        if (onGround()) {
            BlockPos chestPos = getOnPos().above();
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.setBlock(
                        chestPos,
                        Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH),
                        3
                );
                ChestBlockEntity chestBlockEntity = (ChestBlockEntity) serverLevel.getBlockEntity(chestPos);
                for (int i = 0; i < items.getSlots(); i++) {
                    ItemStack stack = items.getStackInSlot(i);
                    assert chestBlockEntity != null;
                    chestBlockEntity.setItem(i, stack);
                    chestBlockEntity.setChanged();
                }
            }
            discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        items.deserializeNBT(level().registryAccess(), compoundTag.getCompound("inventory"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.put("inventory", items.serializeNBT(level().registryAccess()));
    }
}
