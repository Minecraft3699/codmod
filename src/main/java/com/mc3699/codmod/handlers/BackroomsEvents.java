package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.backrooms.CeilingLightBlock;
import com.mc3699.codmod.registry.CodArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BackroomsEvents {

    public static final ResourceKey<Level> BACKROOMS_DIM =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "backrooms"));

    public static final ResourceKey<Level> CITY_DIM =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "city"));

    private static Random random = new Random();

    @SubscribeEvent
    public static void backroomsEntryListener(LivingDamageEvent.Pre event)
    {
        // random chance to enter backrooms when suffocating in blocks
        if(event.getSource().is(DamageTypes.IN_WALL))
        {
            Entity entity = event.getEntity();
            if(entity.level() instanceof ServerLevel serverLevel)
            {
                if(random.nextInt(0,12) == 2)
                {
                    if(entity.getInBlockState().equals(Blocks.SAND.defaultBlockState()) || entity.getInBlockState().equals(Blocks.GRAVEL.defaultBlockState()))
                    {
                        event.getEntity().teleportTo(Objects.requireNonNull(serverLevel.getServer().getLevel(BACKROOMS_DIM)), entity.getX(), 3, entity.getZ(), RelativeMovement.ALL, entity.getYRot(), entity.getXRot());
                        event.setNewDamage(0);
                    }
                }
            }

        }
    }


    public static void flicker(ServerLevel serverLevel, BlockPos center, int radius, float chance)
    {
        Random random = new Random();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutablePos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    BlockState state = serverLevel.getBlockState(mutablePos);
                    if(state.getBlock() instanceof CeilingLightBlock && random.nextFloat() < chance)
                    {
                        boolean lightEnabled = state.getValue(CeilingLightBlock.ENABLED);
                        serverLevel.setBlock(mutablePos, state.setValue(CeilingLightBlock.ENABLED, !lightEnabled), 3);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void backroomsLivingTick(EntityTickEvent.Post event)
    {
        if(event.getEntity().level().dimension().equals(BACKROOMS_DIM) && event.getEntity().level() instanceof ServerLevel serverLevel)
        {
            if(event.getEntity() instanceof LivingEntity livingEntity)
            {
                CompoundTag entityData = livingEntity.getPersistentData();

                AtomicBoolean suitValid = new AtomicBoolean(true);
                Iterable<ItemStack> armorSlots = livingEntity.getArmorSlots();
                armorSlots.forEach(stack -> {
                    if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial().equals(Holder.direct(CodArmor.NBC_ARMOR_MATERIAL)))) {
                        suitValid.set(false);
                    }
                });

                if(suitValid.get())
                {
                    entityData.putInt("br_sickness",0);
                }


                int sickLevel = entityData.getInt("br_sickness");

                if(entityData.contains("br_sickness") && !suitValid.get())
                {
                    entityData.putInt("br_sickness",sickLevel+1);
                } else {
                    entityData.putInt("br_sickness", 0);
                }

                // backrooms sickness unused for now

            }

        }
    }

}
