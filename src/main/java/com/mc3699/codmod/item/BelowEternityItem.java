package com.mc3699.codmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BelowEternityItem extends Item {
    private static final int COOLDOWN_TICKS = 2400;
    private static final double SUMMON_RANGE = 3.0;
    private static final int DESPAWN_TICKS = 1200;
    public static final Logger LOGGER = LogManager.getLogger();

    private Warden summonedWarden = null;
    private int wardenLifetime = 0;

    public BelowEternityItem(Properties properties) {
        super(properties
                .stacksTo(1)
                .rarity(Rarity.EPIC)
                .durability(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(itemStack);
            }

            if (summonedWarden != null && summonedWarden.isAlive()) {
                return InteractionResultHolder.fail(itemStack);
            }

            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

            Vec3 playerPos = player.position();
            BlockPos spawnPos = findSafeSpawnPosition(level, playerPos);

            if (spawnPos != null && level instanceof ServerLevel serverLevel) {
                Warden warden = EntityType.WARDEN.create(level);
                if (warden != null) {
                    warden.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5,
                            level.random.nextFloat() * 360F, 0.0F);

                    warden.finalizeSpawn(serverLevel,
                            serverLevel.getCurrentDifficultyAt(warden.blockPosition()),
                            MobSpawnType.COMMAND, null);

                    warden.getAttribute(Attributes.MAX_HEALTH).setBaseValue(75.0);
                    warden.setHealth(75.0f);
                    warden.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15.0);

                    warden.setPersistenceRequired();
                    warden.getPersistentData().putBoolean("NoDrops", true);

                    serverLevel.addFreshEntity(warden);

                    serverLevel.playSound(null, spawnPos, SoundEvents.WARDEN_EMERGE, SoundSource.NEUTRAL, 1.0F, 0.8F);
                    serverLevel.playSound(null, spawnPos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.NEUTRAL, 0.8F, 1.2F);

                    summonedWarden = warden;
                    wardenLifetime = 0;

                    return InteractionResultHolder.success(itemStack);
                }
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private BlockPos findSafeSpawnPosition(Level level, Vec3 center) {
        BlockPos centerPos = BlockPos.containing(center);

        for (int attempts = 0; attempts < 20; attempts++) {
            double angle = level.random.nextDouble() * Math.PI * 2;
            double distance = 2.0 + level.random.nextDouble() * (SUMMON_RANGE - 2.0);

            int x = (int) (center.x + Math.cos(angle) * distance);
            int z = (int) (center.z + Math.sin(angle) * distance);

            for (int y = centerPos.getY() + 5; y >= centerPos.getY() - 10; y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockPos posAbove = pos.above();
                BlockPos posAbove2 = pos.above(2);

                if (!level.getBlockState(pos).isAir() &&
                        level.getBlockState(posAbove).isAir() &&
                        level.getBlockState(posAbove2).isAir()) {
                    return posAbove;
                }
            }
        }

        return centerPos;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && summonedWarden != null && summonedWarden.isAlive()) {
            wardenLifetime++;
            if (wardenLifetime >= DESPAWN_TICKS) {
                LOGGER.debug("he gone");
                summonedWarden.remove(Entity.RemovalReason.DISCARDED);
                summonedWarden = null;
                wardenLifetime = 0;
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
