package com.mc3699.codmod.item.marksmanRevolver;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class MarksmanRevolverItem extends Item {
    public MarksmanRevolverItem(Properties properties) {
        super(properties);
    }


    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(entity instanceof Player player && player.level() instanceof ServerLevel serverLevel)
        {
            fireRaycast(serverLevel, player);
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.setDeltaMovement(0,1,0);
        player.hurtMarked = true;
        return super.use(level, player, usedHand);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return MarksmanRevolverItemRenderer.INSTANCE;
            }
        });
    }

    private void fireRaycast(ServerLevel serverLevel, Player player)
    {
        double maxRange = 250;
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 angle = player.getLookAngle().scale(maxRange);
        Vec3 end = start.add(angle.scale(maxRange));

        AABB hitZone = player.getBoundingBox().expandTowards(angle.scale(maxRange));
        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(player, start, end, hitZone, (entity -> true), 1000000000);

        serverLevel.sendParticles(ParticleTypes.CRIT,
                (start.x + end.x) / 2,
                (start.y + end.y) / 2,
                (start.z + end.z) / 2,
                10,
                (end.x - start.x) / 2,
                (end.y - start.y) / 2,
                (end.z - start.z) / 2,
                0.1
        );

        if(hitResult != null)
        {
            hitResult.getEntity().hurt(serverLevel.damageSources().playerAttack(player), 5);
        }
    }
}
