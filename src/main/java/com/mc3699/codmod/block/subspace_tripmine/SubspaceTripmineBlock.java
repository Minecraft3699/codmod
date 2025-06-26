package com.mc3699.codmod.block.subspace_tripmine;

import com.mc3699.codmod.registry.CodDamageTypes;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import java.util.List;

public class SubspaceTripmineBlock extends Block {
    public SubspaceTripmineBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(level instanceof ServerLevel serverLevel)
        {
            spawnParticleSphere(entity, pos, 16);
            serverLevel.playSound(null, pos, CodSounds.TRIPMINE.get(), SoundSource.MASTER, 128,1);
            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            DamageSource tripmineDamage = new DamageSource(level
                    .registryAccess()
                    .lookupOrThrow(Registries.DAMAGE_TYPE)
                    .getOrThrow(CodDamageTypes.SUBSPACE_TRIPMINE));

            AABB damageBounds = new AABB(pos).inflate(16);
            List<LivingEntity> nearEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, damageBounds);
            nearEntities.forEach((target) ->{
                if(target.distanceToSqr(pos.getCenter()) <= 16*16)
                {
                    target.hurt(tripmineDamage, 1000);
                }
            });
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.1,0,0.1,0.9,0.8,0.9);
    }

    public void spawnParticleSphere(Entity entity, BlockPos center, double radius) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            int radiusCeil = (int) Math.ceil(radius);
            for (int x = -radiusCeil; x <= radiusCeil; x++) {
                for (int y = -radiusCeil; y <= radiusCeil; y++) {
                    for (int z = -radiusCeil; z <= radiusCeil; z++) {
                        BlockPos pos = center.offset(x, y, z);
                        if (pos.distSqr(center) <= radius * radius) {
                            serverLevel.sendParticles(
                                    new DustParticleOptions(new Vector3f(1.0F, 0.0F, 1.0F), 3.0F),
                                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                    1, // Particle count
                                    0.0, 0.0, 0.0, // Spread
                                    0.0 // Speed
                            );
                        }
                    }
                }
            }
        }
    }
}
