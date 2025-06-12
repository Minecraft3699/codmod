package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.uavController.UAVControllerBlockEntity;
import com.mc3699.codmod.entity.uav.UAVEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UAVControllerPeripheral implements IPeripheral {
    private final UAVControllerBlockEntity blockEntity;

    public UAVControllerPeripheral(UAVControllerBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "uav_controller";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }

    @LuaFunction
    public final boolean summonUAV() {
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            if (blockEntity.getUAVEntity(serverLevel) != null) {
                return false;
            }

            BlockPos pos = blockEntity.getBlockPos().above();
            UAVEntity uav = new UAVEntity(serverLevel);
            uav.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            uav.setLinkedController(pos);
            serverLevel.addFreshEntity(uav);
            blockEntity.setUavUUID(uav.getUUID());
            blockEntity.setChanged();
            return true;
        }
        return false;
    }

    @LuaFunction
    public final boolean setTarget(double x, double y, double z, int altitude, boolean followTerrain) {
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            UAVEntity uav = blockEntity.getUAVEntity(serverLevel);
            if (uav != null) {
                uav.setTargetPos(new Vec3(x, y, z));
                uav.setAltitude(altitude);
                uav.setTerrainFollowing(followTerrain);
                return true;
            }
            return false;
        }
        return false;
    }

    @LuaFunction
    public final boolean hasArrived(int threshold) {
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            UAVEntity uav = blockEntity.getUAVEntity(serverLevel);
            if (uav != null) {
                if (uav.isAtTarget(threshold)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @LuaFunction
    public final Map<String, Double> getPos() {
        Map<String, Double> result = new HashMap<>();
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            UAVEntity uav = blockEntity.getUAVEntity(serverLevel);
            if (uav != null) {
                Vec3 pos = uav.getPosition(0);
                result.put("x", pos.x);
                result.put("y", pos.y);
                result.put("z", pos.z);
            } else {
                result.put("x", 0.0);
                result.put("y", 0.0);
                result.put("z", 0.0);
            }
        }
        return result;
    }

    @LuaFunction
    public final Map<Integer, Map<String, Object>> scanEntities(double radius) {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            UAVEntity uav = blockEntity.getUAVEntity(serverLevel);
            if (uav != null) {
                Vec3 uavPos = uav.getPosition(0);
                AABB aabb = new AABB(
                        uavPos.x - radius, uavPos.y - radius, uavPos.z - radius,
                        uavPos.x + radius, uavPos.y + radius, uavPos.z + radius
                );
                List<Entity> entities = serverLevel.getEntities((Entity) null, aabb, entity -> entity != uav);
                int index = 0;
                for (Entity entity : entities) {
                    Map<String, Object> entityData = new HashMap<>();
                    entityData.put("type", entity.getType().getDescriptionId());
                    entityData.put("x", entity.getX());
                    entityData.put("y", entity.getY());
                    entityData.put("z", entity.getZ());
                    entityData.put("uuid", entity.getUUID().toString());
                    result.put(index++, entityData);
                }
            }
        }
        return result;
    }
}