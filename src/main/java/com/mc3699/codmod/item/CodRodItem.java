package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CodRodItem extends MaceItem {
    public CodRodItem(Properties props) {
        super(props);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            Vec3 codSpawnPos = entity.getPosition(0);

            DamageSource dam = new DamageSource(player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.DAMAGE_TYPE)
                    .getOrThrow(CodDamageTypes.COD_ROD));

            if (entity instanceof ServerPlayer hitPlayer) {
                entity.hurt(dam, 1000f);
            } else {
                entity.remove(Entity.RemovalReason.DISCARDED);
            }

            serverLevel.playSound(
                    null,
                    BlockPos.containing(codSpawnPos),
                    SoundEvents.DRAGON_FIREBALL_EXPLODE,
                    SoundSource.PLAYERS
            );

            for (int i = 0; i < 8; i++) {
                Entity cod = new Cod(EntityType.COD, serverLevel);
                cod.setPos(codSpawnPos);
                int randomVelX = serverLevel.random.nextInt(-4, 4);
                int randomVelZ = serverLevel.random.nextInt(-4, 4);
                int randomHeight = serverLevel.random.nextInt(8, 24);
                serverLevel.sendParticles(
                        ParticleTypes.EXPLOSION,
                        codSpawnPos.x,
                        codSpawnPos.y,
                        codSpawnPos.z,
                        2,
                        0,
                        1,
                        0,
                        randomVelX
                );
                serverLevel.sendParticles(
                        ParticleTypes.FLAME,
                        codSpawnPos.x,
                        codSpawnPos.y,
                        codSpawnPos.z,
                        32,
                        0,
                        1,
                        0,
                        randomVelX * 0.01
                );
                serverLevel.sendParticles(
                        ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        codSpawnPos.x,
                        codSpawnPos.y,
                        codSpawnPos.z,
                        4,
                        0,
                        1,
                        0,
                        0.01
                );
                cod.setDeltaMovement(randomVelX * 0.1, randomHeight * 0.05, randomVelZ * 0.1);
                serverLevel.addFreshEntity(cod);
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        return !(miningEntity instanceof Player player) || !player.isCreative();
    }
}
