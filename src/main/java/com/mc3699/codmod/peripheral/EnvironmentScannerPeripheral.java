package com.mc3699.codmod.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnvironmentScannerPeripheral implements IPeripheral {

    private final ITurtleAccess access;

    public EnvironmentScannerPeripheral(ITurtleAccess access) {
        this.access = access;
    }

    @LuaFunction
    public final Map<Integer, Map<String, Object>> scanEntities(double radius)
    {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        AABB searchArea = new AABB(this.access.getPosition()).inflate(radius);
        BlockPos origin = this.access.getPosition();

        if (access.getLevel() instanceof ServerLevel serverLevel)
        {
            List<net.minecraft.world.entity.Entity> entities = serverLevel.getEntities((Entity) null, searchArea, entity -> true);
            int index = 1;
            for (Entity entity : entities) {
                Map<String, Object> entityData = new HashMap<>();
                entityData.put("type", entity.getType().builtInRegistryHolder().key().location().toString());
                entityData.put("name", entity.getName().getString());
                entityData.put("x", entity.getX() - origin.getX());
                entityData.put("y", entity.getY() - origin.getY());
                entityData.put("z", entity.getZ() - origin.getZ());
                entityData.put("uuid", entity.getUUID().toString());
                result.put(index++, entityData);
            }
        }

        return result;
    }

    @LuaFunction
    public final Map<Integer, Map<String, Object>> scanBlocks(double radius)
    {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        AABB searchArea = new AABB(this.access.getPosition()).inflate(radius);
        BlockPos origin = this.access.getPosition();

        if (access.getLevel() instanceof ServerLevel serverLevel)
        {
            int index = 1;
            for (BlockPos pos : BlockPos.betweenClosedStream(searchArea).toList()) {
                BlockState state = serverLevel.getBlockState(pos);
                Map<String, Object> blockData = new HashMap<>();
                blockData.put("type", state.getBlock().builtInRegistryHolder().key().location().toString());
                blockData.put("x", pos.getX() - origin.getX());
                blockData.put("y", pos.getY() - origin.getY());
                blockData.put("z", pos.getZ() - origin.getZ());
                result.put(index++, blockData);
            }
        }

        return result;
    }

    @LuaFunction
    public final String getDirection()
    {
        return this.access.getDirection().toString();
    }

    @LuaFunction
    public final Map<String, Integer> getPos()
    {
        BlockPos pos = this.access.getPosition();
        Map<String, Integer> result = new HashMap<>();
        result.put("x", pos.getX());
        result.put("y", pos.getY());
        result.put("z", pos.getZ());
        return result;
    }

    @Override
    public String getType() {
        return "environment_scanner";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }


}
