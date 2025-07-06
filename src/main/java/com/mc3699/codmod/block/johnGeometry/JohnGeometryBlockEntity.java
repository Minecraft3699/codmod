package com.mc3699.codmod.block.johnGeometry;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class JohnGeometryBlockEntity extends BlockEntity {
    public JohnGeometryBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.JOHN_GEOMETRY.get(), pos, blockState);
    }


    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T t) {


        if (level instanceof ServerLevel serverLevel) {
            AABB searchRange = new AABB(pos).inflate(1);

            List<Player> playerList = serverLevel.getEntitiesOfClass(Player.class, searchRange);

            if (!playerList.isEmpty()) {
                // gotta make sure everyone knows john geometry is around
                serverLevel.playSound(null, pos, CodSounds.GEOMETRY.get(), SoundSource.MASTER, 512, 1);

                serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                Vec3 codSpawnPos = pos.getCenter();

                for (int i = 0; i < 24; i++) {
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
                    cod.setDeltaMovement(randomVelX * 0.1, randomHeight * 0.07, randomVelZ * 0.1);
                    serverLevel.addFreshEntity(cod);
                }

            }
        }

    }
}
