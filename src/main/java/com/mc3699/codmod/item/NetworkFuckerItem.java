package com.mc3699.codmod.item;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkFuckerItem extends Item {

    private static final Random RANDOM = new Random();
    private static final List<Item> ITEMS = new ArrayList<>();

    public NetworkFuckerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        if(level instanceof ServerLevel serverLevel)
        {
            if(ITEMS.isEmpty())
            {
                MinecraftServer server = serverLevel.getServer();
                Registry<Item> itemRegistry = server.registryAccess().registryOrThrow(Registries.ITEM);
                itemRegistry.forEach(ITEMS::add);
            }
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 1000000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {

        for(int i = 0; i < 2; i++)
        {
            Item randomItem = ITEMS.isEmpty() ? Items.COD : ITEMS.get(RANDOM.nextInt(ITEMS.size()));
            ItemStack randomStack = new ItemStack(randomItem, 1);

            ItemProjectileEntity projectile = new ItemProjectileEntity(CodEntities.ITEM_PROJECTILE.get(), level, randomStack, 5,10, false);
            Vec3 eyePos = livingEntity.getEyePosition();
            Vec3 lookVec = livingEntity.getViewVector(1.0F).normalize().scale(0.8);
            projectile.setPos(eyePos.x + lookVec.x, eyePos.y + lookVec.y, eyePos.z + lookVec.z);
            projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 1f, 2.5f, 4f);
            level.addFreshEntity(projectile);
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }
}
