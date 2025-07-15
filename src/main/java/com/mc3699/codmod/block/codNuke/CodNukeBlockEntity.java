package com.mc3699.codmod.block.codNuke;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CodNukeBlockEntity extends BlockEntity {

    private int remaining = 90;
    private boolean detonating = false;

    public CodNukeBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.COD_NUKE.get(), pos, blockState);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("timer", remaining);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        remaining = tag.getInt("timer");
    }

    public void sync() {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        CodNukeBlockEntity blockEntity = (CodNukeBlockEntity) be;


        if(level instanceof ServerLevel serverLevel)
        {

            if(serverLevel.getGameTime() % 20 == 0)
            {
                serverLevel.playSound(null, blockEntity.worldPosition, CodSounds.BEEP.get(), SoundSource.MASTER, 3, 1);
                blockEntity.remaining--;
                blockEntity.setChanged();
                blockEntity.sync();
            }

            if(blockEntity.remaining == 1)
            {
                if(!blockEntity.detonating) serverLevel.playSound(null, blockEntity.worldPosition, CodSounds.DETONATION.get(), SoundSource.MASTER, 16, 1);
                blockEntity.detonating = true;
            }

            if(blockEntity.remaining == 0)
            {
                explode(blockEntity.worldPosition, serverLevel);
                serverLevel.setBlock(blockEntity.worldPosition, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    public int getRemaining() {
        return remaining;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    private static void explode(BlockPos pos, ServerLevel serverLevel) {
        Vec3 codSpawnPos = pos.getCenter();
        serverLevel.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER, 16, 1);

        /*
        int gridSize = 32;
        for (int xIndex = 0; xIndex < gridSize; xIndex++) {
            for (int zIndex = 0; zIndex < gridSize; zIndex++) {
                double xOffset = xIndex - (gridSize / 2.0) + 0.5;
                double zOffset = zIndex - (gridSize / 2.0) + 0.5;
                Vec3 spawnPos = codSpawnPos.add(xOffset, 0, zOffset);
                Entity cod = new Cod(EntityType.COD, serverLevel);
                cod.setPos(spawnPos);
                int randomVelX = serverLevel.random.nextInt(-4, 5);
                int randomVelZ = serverLevel.random.nextInt(-4, 5);
                int randomHeight = serverLevel.random.nextInt(8, 25);
                cod.setDeltaMovement(randomVelX * 0.1, randomHeight * 0.05, randomVelZ * 0.1);
                serverLevel.addFreshEntity(cod);
            }
        }

         */


        for(int i = 0; i < 1024; i++)
        {
            ItemStack stack = new ItemStack(Items.COD, 1);
            ItemProjectileEntity codProjectile = new ItemProjectileEntity(CodEntities.ITEM_PROJECTILE.get(), serverLevel, stack, 20, 100, false);
            double yaw = serverLevel.random.nextDouble() * Math.PI * 4;
            double pitch = serverLevel.random.nextDouble()-0.5;
            double speed = 1f;

            codProjectile.setDeltaMovement(
                    Math.cos(yaw) * Math.cos(pitch) * speed,
                    Math.sin(pitch) * speed + 0.4,
                    Math.sin(yaw) * Math.cos(pitch) * speed
            );

            codProjectile.setPos(codSpawnPos);

            serverLevel.addFreshEntity(codProjectile);
        }

    }
}
