package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.launchpad.LaunchPadBlockEntity;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import dan200.computercraft.api.lua.*;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.checkerframework.checker.signature.qual.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaunchPadPeripheral implements IPeripheral {

    private final LaunchPadBlockEntity blockEntity;

    public LaunchPadPeripheral(LaunchPadBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @LuaFunction
    public final void deliver(ILuaContext context, IArguments arguments) throws LuaException {

        int x = arguments.getInt(0);
        int y = arguments.getInt(1);
        int z = arguments.getInt(2);


        if(blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide())
        {
            ServerLevel serverLevel = (ServerLevel) blockEntity.getLevel();
            Vec3 missileSpawnPos = blockEntity.getBlockPos().getCenter().add(0,0.1,0);
            CodMissileEntity missileEntity = new CodMissileEntity(serverLevel, x,y,z,"delivery",null,blockEntity.getItems());
            serverLevel.addFreshEntity(missileEntity);
            missileEntity.setPos(missileSpawnPos);
        }

    }


    @LuaFunction
    public final void fireWithFunction(int x, int y, int z, String type, String argument) {
        if(blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide())
        {
            ServerLevel serverLevel = (ServerLevel) blockEntity.getLevel();
            Vec3 missileSpawnPos = blockEntity.getBlockPos().getCenter().add(0,0.1,0);
            CodMissileEntity missileEntity = new CodMissileEntity(serverLevel, x,y,z,type,argument, new ItemStackHandler(27));
            serverLevel.addFreshEntity(missileEntity);
            missileEntity.setPos(missileSpawnPos);
        }
    }

    @Override
    public String getType() {
        return "launch_pad";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
