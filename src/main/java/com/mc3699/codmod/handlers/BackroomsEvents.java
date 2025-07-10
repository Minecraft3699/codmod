package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodArmor;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.concurrent.atomic.AtomicBoolean;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BackroomsEvents {

    private static final ResourceKey<Level> BACKROOMS_DIM =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "backrooms"));

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
