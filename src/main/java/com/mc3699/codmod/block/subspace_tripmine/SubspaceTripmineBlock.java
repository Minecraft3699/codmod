package com.mc3699.codmod.block.subspace_tripmine;

import com.mc3699.codmod.registry.CodDamageTypes;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.joml.Vector3f;

import java.util.List;

public class SubspaceTripmineBlock extends Block {
    //lokey evil for this one >:3
    public static final BooleanProperty VISIBLE = BooleanProperty.create("visible");
    public static final BooleanProperty ARMED = BooleanProperty.create("armed");
    public static final IntegerProperty BLINK_STATE = IntegerProperty.create("blink_state", 0, 1); // 0=invisible 1=blinking

    public SubspaceTripmineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VISIBLE, true)
                .setValue(ARMED, false)
                .setValue(BLINK_STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VISIBLE, ARMED, BLINK_STATE);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide && oldState.getBlock() != this) {
            level.scheduleTick(pos, this, 40);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) { //the cycle of blinking
        if (!state.getValue(ARMED)) {
            level.playSound(null, pos, CodSounds.TRIPMINE_ARMED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, state.setValue(VISIBLE, false).setValue(ARMED, true), 3);
            level.scheduleTick(pos, this, 70 + random.nextInt(100));
        } else if (state.getValue(VISIBLE)) {
            level.playSound(null, pos, CodSounds.TRIPMINE_ARMED.get(), SoundSource.BLOCKS, 0.2F, 0.1F);
            level.setBlock(pos, state.setValue(VISIBLE, false).setValue(BLINK_STATE, 0), 3);
            level.scheduleTick(pos, this, 70 + random.nextInt(100));
        } else {
            level.playSound(null, pos, CodSounds.TRIPMINE_ARMED.get(), SoundSource.BLOCKS, 0.2F, 0.1F);
            level.setBlock(pos, state.setValue(VISIBLE, true).setValue(BLINK_STATE, 1), 3);
            level.scheduleTick(pos, this, 10);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level instanceof ServerLevel serverLevel && state.getValue(ARMED)) {
            // breaks server too much smh
            // not anymore its hollow and has a limit now :D
            spawnParticleSphere(serverLevel, pos, 8);
            level.playSound(null, pos, CodSounds.TRIPMINE.get(), SoundSource.BLOCKS, 3, 1); //NO FUCKING WONDER THE ENTIRE DIM COULD HEAR IT... (also sound was not mono)
            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            DamageSource tripmineDamage = new DamageSource(level
                    .registryAccess()
                    .lookupOrThrow(Registries.DAMAGE_TYPE)
                    .getOrThrow(CodDamageTypes.SUBSPACE_TRIPMINE));

            AABB damageBounds = new AABB(pos).inflate(8);
            List<LivingEntity> nearEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, damageBounds);
            nearEntities.forEach((target) -> {
                if (target.distanceToSqr(pos.getCenter()) <= 8 * 8) {
                    target.hurt(tripmineDamage, 1000);
                }
            });
        }
    }

    @Override
    protected VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.1, 0, 0.1, 0.9, 0.8, 0.9);
    }

    public void spawnParticleSphere(ServerLevel serverLevel, BlockPos center, double radius) { //holow like the knight
        int radiusCeil = (int) Math.ceil(radius);
        int particleCount = 0;
        int particleLimit = 1000;

        for (int x = -radiusCeil; x <= radiusCeil; x++) {
            for (int y = -radiusCeil; y <= radiusCeil; y++) {
                for (int z = -radiusCeil; z <= radiusCeil; z++) {
                    if (particleCount >= particleLimit) {
                        return;
                    }

                    double distSq = x * x + y * y + z * z;
                    double dist = Math.sqrt(distSq);

                    if (dist >= radius - 0.5 && dist <= radius + 0.5) {
                        serverLevel.sendParticles(
                                new DustParticleOptions(new Vector3f(1.0F, 0.0F, 1.0F), 3.0F),
                                center.getX() + x + 0.5,
                                center.getY() + y + 0.5,
                                center.getZ() + z + 0.5,
                                1, // Particle count
                                0.0, 0.0, 0.0, // Spread
                                0.0 // Speed
                        );
                        particleCount++;
                    }
                }
            }
        }
    }
}
