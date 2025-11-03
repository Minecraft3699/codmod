package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.teleporter.TeleporterBlockEntity;
import com.mc3699.codmod.item.transponder.TransponderItem;
import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodSounds;
import com.tterrag.registrate.util.entry.BlockEntry;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class TeleporterPeripheral implements IPeripheral {

    private final TeleporterBlockEntity blockEntity;

    public TeleporterPeripheral(TeleporterBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "teleporter";
    }

    public static void teleportBlockEntity(ServerLevel level, BlockPos from, BlockPos to) {
        BlockEntity be = level.getBlockEntity(from);
        BlockState state = level.getBlockState(from);

        if (be != null) {
            CompoundTag tag = be.saveWithoutMetadata(level.registryAccess());

            level.removeBlock(from, false);
            level.destroyBlock(to, true);
            level.setBlock(to, state, 3);

            BlockEntity newBe = level.getBlockEntity(to);
            if (newBe != null) {
                tag.putInt("x", to.getX());
                tag.putInt("y", to.getY());
                tag.putInt("z", to.getZ());
                newBe.loadWithComponents(tag, level.registryAccess());
                newBe.setChanged();
            }
        } else {
            level.removeBlock(from, false);
            level.destroyBlock(to, true);
            level.setBlock(to, state, 3);
        }
    }

    @LuaFunction
    public final void teleport(int x, int y, int z)
    {
        if(blockEntity.getLevel() instanceof ServerLevel serverLevel)
        {
            AABB teleportArea = new AABB(blockEntity.getBlockPos().above());

            List<net.minecraft.world.entity.Entity> entities = serverLevel.getEntities((Entity) null, teleportArea, entity -> true);

            entities.forEach(entity -> {

                if(entity instanceof ItemEntity item)
                {
                    if(item.getItem().is(Items.BREAD))
                    {
                        ItemEntity teleportedBread = new ItemEntity(serverLevel, x+0.5, y, z+0.5, CodItems.TELEPORTED_BREAD.asStack(item.getItem().getCount()));
                        item.discard();
                        serverLevel.addFreshEntity(teleportedBread);
                    } else {
                        entity.teleportTo(x+0.5,y,z+0.5);
                    }
                } else {
                    entity.teleportTo(x + 0.5, y, z + 0.5);
                }

            });

            serverLevel.playSound(null, blockEntity.getBlockPos(), SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER);
            serverLevel.playSound(null, new BlockPos(x,y,z), SoundEvents.PLAYER_TELEPORT, SoundSource.MASTER);
            teleportBlockEntity(serverLevel, blockEntity.getBlockPos().above(), new BlockPos(x,y,z));
            Vec3 particlePos = blockEntity.getBlockPos().above().getBottomCenter();
            serverLevel.sendParticles(ParticleTypes.END_ROD, x+0.5,y,z+0.5, 50, 0,0.5,0, 0.1);
            serverLevel.sendParticles(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z, 50, 0,0.5,0, 0.1);
        }
    }

    @LuaFunction
    public final void pullTransponder(String id)
    {
        if(blockEntity.getLevel() instanceof ServerLevel serverLevel)
        {
            List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
            for(ServerPlayer player : playerList)
            {
                for(int i = 0; i < player.getInventory().getContainerSize(); i++)
                {
                    ItemStack stack = player.getInventory().getItem(i);

                    String transponderID = stack.get(CodComponents.TRANSPONDER_ID);

                    if(transponderID != null && transponderID.equals(id))
                    {
                        Vec3 teleportPos = blockEntity.getBlockPos().above().getBottomCenter();
                        Vec3 playerPos = player.getPosition(0);


                        serverLevel.playSound(null, player.getBlockPosBelowThatAffectsMyMovement(), SoundEvents.PLAYER_TELEPORT, SoundSource.MASTER);
                        serverLevel.sendParticles(ParticleTypes.END_ROD, playerPos.x, playerPos.y+1, playerPos.z, 100, 0,0.5,0, 0.05);

                        player.teleportTo(serverLevel, teleportPos.x(), teleportPos.y(), teleportPos.z(), RelativeMovement.ALL, player.getYRot(), player.getXRot());

                        serverLevel.playSound(null, player.getBlockPosBelowThatAffectsMyMovement(), SoundEvents.PLAYER_TELEPORT, SoundSource.MASTER);
                        serverLevel.sendParticles(ParticleTypes.END_ROD, teleportPos.x, teleportPos.y, teleportPos.z, 50, 0,0.5,0, 0.1);
                    }
                }
            }
        }
    }

    @LuaFunction
    public final void pullPlayer(String name)
    {
        if(blockEntity.getLevel() instanceof ServerLevel serverLevel)
        {
            List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
            for(ServerPlayer player : playerList)
            {
                Vec3 teleportPos = blockEntity.getBlockPos().above().getBottomCenter();
                Vec3 playerPos = player.getPosition(0);

                serverLevel.playSound(null, player.getBlockPosBelowThatAffectsMyMovement(), SoundEvents.PLAYER_TELEPORT, SoundSource.MASTER);
                serverLevel.sendParticles(ParticleTypes.END_ROD, playerPos.x, playerPos.y+1, playerPos.z, 100, 0,0.5,0, 0.05);

                player.teleportTo(serverLevel, teleportPos.x(), teleportPos.y(), teleportPos.z(), RelativeMovement.ALL, player.getYRot(), player.getXRot());

                serverLevel.playSound(null, player.getBlockPosBelowThatAffectsMyMovement(), SoundEvents.PLAYER_TELEPORT, SoundSource.MASTER);
                serverLevel.sendParticles(ParticleTypes.END_ROD, teleportPos.x, teleportPos.y, teleportPos.z, 50, 0,0.5,0, 0.1);
            }
        }
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
