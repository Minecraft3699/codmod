package com.mc3699.codmod.entity.codmissile;

import com.mc3699.codmod.entity.parachuteChest.ParachuteChestEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CodMissileEntity extends Entity {
    private static final int LAUNCH_TIMER = 60;
    private static final double ASCENT_SPEED = 1.5;
    private static final double TERMINAL_SPEED = -2.0;
    private final ItemStackHandler items;
    private double targetX, targetY, targetZ;
    private int phaseTime;
    private int phase;
    private String function;
    private String argument;

    public CodMissileEntity(
            EntityType<CodMissileEntity> type,
            Level level,
            double targetX,
            double targetY,
            double targetZ,
            String function,
            @Nullable String argument,
            @Nullable ItemStackHandler items
    ) {
        super(type, level);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.function = function;
        this.argument = argument;
        this.items = items;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (level() instanceof ServerLevel serverLevel) {
            phaseTime++;
            switch (phase) {
                // start launch / countdown phase
                case 0:
                    serverLevel.sendParticles(
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            position().x,
                            position().y,
                            position().z,
                            10,
                            0,
                            -0.1,
                            0,
                            0.01
                    );
                    serverLevel.sendParticles(
                            ParticleTypes.WHITE_SMOKE,
                            position().x,
                            position().y,
                            position().z,
                            20,
                            0,
                            -0.1,
                            0,
                            0.02
                    );
                    serverLevel.sendParticles(
                            ParticleTypes.FLAME,
                            position().x,
                            position().y,
                            position().z,
                            50,
                            0,
                            -0.1,
                            0,
                            0.02
                    );
                    move(MoverType.SELF, getDeltaMovement());
                    if (phaseTime > LAUNCH_TIMER) {
                        phase = 1;
                        phaseTime = 0;
                        setDeltaMovement(0, ASCENT_SPEED, 0);
                    }
                    break;
                //fly to worldheight
                case 1:
                    if (phaseTime == 2) {
                        serverLevel.playSound(
                                null,
                                BlockPos.containing(position()),
                                CodSounds.MISSILE_LAUNCH.get(),
                                SoundSource.PLAYERS,
                                100,
                                1
                        );
                        serverLevel.playSound(
                                null,
                                BlockPos.containing(position()),
                                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                                SoundSource.PLAYERS,
                                10.0F,
                                1.0F
                        );
                    }
                    move(MoverType.SELF, getDeltaMovement());
                    serverLevel.sendParticles(
                            ParticleTypes.FLAME,
                            position().x,
                            position().y,
                            position().z,
                            300,
                            0,
                            -0.1,
                            0,
                            0.01
                    );
                    serverLevel.sendParticles(
                            ParticleTypes.FLAME,
                            position().x,
                            position().y,
                            position().z,
                            300,
                            0,
                            -0.1,
                            0,
                            0.03
                    );
                    serverLevel.sendParticles(
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            position().x,
                            position().y,
                            position().z,
                            100,
                            0,
                            -3,
                            0,
                            0.05
                    );
                    if (getY() >= 320) {
                        phase = 2;
                        phaseTime = 0;
                        setDeltaMovement(0, 0, 0);
                    }
                    break;
                // teleport (honeslty can probably go in case 3 but idc)
                case 2:
                    setPos(targetX + 0.5, 320, targetZ + 0.5);
                    phase = 3;
                    phaseTime = 0;
                    setDeltaMovement(0, TERMINAL_SPEED, 0);
                    break;
                // re-entry
                case 3:
                    if (phaseTime == 2) {
                        serverLevel.playSound(
                                null,
                                BlockPos.containing(position()),
                                CodSounds.MISSILE_LAUNCH.get(),
                                SoundSource.PLAYERS,
                                100,
                                1
                        );
                    }
                    serverLevel.sendParticles(
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            position().x,
                            position().y,
                            position().z,
                            100,
                            0,
                            2,
                            0,
                            0.3
                    );
                    move(MoverType.SELF, getDeltaMovement());

                    // bandaid fix for parachute chest drop lol
                    if (function.equals("delivery")) {
                        performFunction(serverLevel, function, "");
                    }

                    if (onGround() || getY() <= targetY) {
                        performFunction(serverLevel, function, argument);
                    }
                    break;
            }
        }
    }


    protected void performFunction(ServerLevel serverLevel, String function, String argument) {
        switch (function) {
            case "cod_explosion": {
                explode(serverLevel);
            }
            break;

            case "delivery": {
                //placeBarrel(serverLevel, blockPosition(), fillInventory(ItemRegistration.INTEGRITY_COOKIE.get(),0,27));
                summonParachuteChest(serverLevel, items);
            }
            break;

            case "summon": {
                summonEntity(serverLevel, argument);
            }
            break;


        }
        serverLevel.playSound(
                null,
                BlockPos.containing(position()),
                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                SoundSource.PLAYERS,
                50.0F,
                1.0F
        );
        discard();
    }


    private List<ItemStack> fillInventory(Item item, int startSlot, int endSlot) {
        List<ItemStack> filledInventory = new ArrayList<>(27);
        for (int i = startSlot; i < endSlot; i++) {
            filledInventory.add(new ItemStack(item, 64));
        }
        return filledInventory;
    }

    protected void summonParachuteChest(ServerLevel serverLevel, ItemStackHandler items) {
        ParachuteChestEntity chestEntity = new ParachuteChestEntity(
                CodEntities.PARACHUTE_CHEST.get(),
                serverLevel,
                items
        );
        chestEntity.setPos(position());
        serverLevel.addFreshEntity(chestEntity);
    }

    protected void summonEntity(ServerLevel serverLevel, String entityID) {
        EntityType<?> entityType = EntityType.byString(entityID).orElse(EntityType.COD);
        Entity entity = entityType.create(serverLevel);
        if (entity != null) {
            entity.setPos(position());
            serverLevel.addFreshEntity(entity);
        }
    }

    protected void placeBarrel(ServerLevel serverLevel, BlockPos placePos, List<ItemStack> items) {
        serverLevel.setBlock(placePos, Blocks.BARREL.defaultBlockState(), 3);
        BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) serverLevel.getBlockEntity(placePos);
        int slot = 0;
        for (ItemStack item : items) {
            barrelBlockEntity.setItem(slot, item);
            slot++;
        }
    }

    protected void explode(ServerLevel serverLevel) {
        Vec3 codSpawnPos = position().add(0, 1, 0);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        targetX = compoundTag.getDouble("targetX");
        targetY = compoundTag.getDouble("targetY");
        targetZ = compoundTag.getDouble("targetZ");
        phase = compoundTag.getInt("phase");
        phaseTime = compoundTag.getInt("phaseTime");
        function = compoundTag.getString("function");
        argument = compoundTag.getString("argument");
        if (compoundTag.contains("inventory")) {
            items.deserializeNBT(level().registryAccess(), compoundTag.getCompound("inventory"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putDouble("targetX", targetX);
        compoundTag.putDouble("targetY", targetY);
        compoundTag.putDouble("targetZ", targetZ);
        compoundTag.putInt("phase", phase);
        compoundTag.putInt("phaseTime", phaseTime);
        compoundTag.putString("function", function);
        compoundTag.putString("argument", argument);
        compoundTag.put("inventory", items.serializeNBT(level().registryAccess()));
    }
}