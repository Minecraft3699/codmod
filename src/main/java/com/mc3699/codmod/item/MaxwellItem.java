package com.mc3699.codmod.item;

import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MaxwellItem extends Item {

    public MaxwellItem() {
        super(new Properties().durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level instanceof ServerLevel serverLevel) {

            ItemStack stack = player.getItemInHand(usedHand);
            stack.setDamageValue(stack.getDamageValue()+1);

            serverLevel.playSound(
                    null,
                    BlockPos.containing(player.position()),
                    SoundEvents.CAT_AMBIENT,
                    SoundSource.PLAYERS,
                    16,
                    1+(stack.getDamageValue())*0.01f
            );

            if(stack.getDamageValue() == 100)
            {
                stack.shrink(1);
                explode(serverLevel, player.position());
            }

        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    private void explode(ServerLevel serverLevel, Vec3 pos) {
        Vec3 codSpawnPos = pos.add(0, 1, 0);
        serverLevel.playSound(
                null,
                BlockPos.containing(codSpawnPos),
                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                SoundSource.PLAYERS,
                10.0F,
                1.0F
        );

        int gridSize = 16;
        for (int i = 0; i < 64; i++) {
            int xIndex = i % gridSize;
            int zIndex = i / gridSize;
            double xOffset = xIndex - (gridSize / 2.0);
            double zOffset = zIndex - (gridSize / 2.0);
            Vec3 spawnPos = codSpawnPos.add(xOffset, 0, zOffset);
            Entity cod = new SwarmCodEntity(CodEntities.SWARM_COD.get(), serverLevel);
            cod.setPos(spawnPos);
            int randomVelX = serverLevel.random.nextInt(-4, 4);
            int randomVelZ = serverLevel.random.nextInt(-4, 4);
            int randomHeight = serverLevel.random.nextInt(8, 24);
            cod.setDeltaMovement(randomVelX * 0.1, randomHeight * 0.05, randomVelZ * 0.1);
            serverLevel.addFreshEntity(cod);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if(level instanceof ServerLevel serverLevel)
        {
            if(isSelected) {
                if (serverLevel.random.nextInt(1, 100) == 1) {
                    serverLevel.playSound(
                        null,
                        BlockPos.containing(entity.position()),
                        SoundEvents.CAT_PURR,
                        SoundSource.PLAYERS,
                        16,
                        1
                    );
                }
            }

            if(entity.tickCount % 40 == 0)
            {
                stack.setDamageValue(stack.getDamageValue()-1);
            }


        }
    }
}
