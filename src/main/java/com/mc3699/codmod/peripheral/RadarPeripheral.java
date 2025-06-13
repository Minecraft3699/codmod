package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.radar.RadarBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RadarPeripheral implements IPeripheral {
    private final RadarBlockEntity blockEntity;

    public RadarPeripheral(RadarBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "radar";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }

    @LuaFunction
    public Map<String, Object> scan(IComputerAccess computer) {
        Map<String, Object> result = new HashMap<>();
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            for (Player player : serverLevel.getServer().getPlayerList().getPlayers()) {
                if (player.level().dimension() == blockEntity.getLevel().dimension()) {
                    Vec3 pos = player.position();
                    Map<String, Double> coords = new HashMap<>();
                    coords.put("x", pos.x);
                    coords.put("y", pos.y);
                    coords.put("z", pos.z);
                    result.put(player.getName().getString(), coords);
                }
            }
        }
        return result;
    }

    @LuaFunction
    public Map<Integer, Map<String, Double>> getEntitiesByType(String entityTypeId) {
        Map<Integer, Map<String, Double>> result = new HashMap<>();
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel) {
            EntityType<?> entityType = EntityType.byString(entityTypeId).orElse(null);
            if (entityType != null) {
                int index = 1;
                for (Entity entity : serverLevel.getEntities().getAll()) {
                    if (entity.getType() == entityType &&
                        entity.level().dimension() == blockEntity.getLevel().dimension()) {
                        Vec3 pos = entity.position();
                        Map<String, Double> coords = new HashMap<>();
                        coords.put("x", pos.x);
                        coords.put("y", pos.y);
                        coords.put("z", pos.z);
                        result.put(index++, coords);
                    }
                }
            }
        }
        return result;
    }
}
