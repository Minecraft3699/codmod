package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.launchpad.LaunchPadBlockEntity;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import com.mc3699.codmod.registry.CodEntities;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

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


        if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) blockEntity.getLevel();
            Vec3 missileSpawnPos = blockEntity.getBlockPos().getCenter().add(0, 0.1, 0);
            CodMissileEntity missileEntity = new CodMissileEntity(
                    CodEntities.COD_MISSILE.get(),
                    serverLevel,
                    x,
                    y,
                    z,
                    "delivery",
                    null,
                    blockEntity.getItems()
            );
            serverLevel.addFreshEntity(missileEntity);
            missileEntity.setPos(missileSpawnPos);
        }

    }


    @LuaFunction
    public final void fireWithFunction(int x, int y, int z, String type, String argument) {
        if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) blockEntity.getLevel();
            Vec3 missileSpawnPos = blockEntity.getBlockPos().getCenter().add(0, 0.1, 0);
            CodMissileEntity missileEntity = new CodMissileEntity(
                    CodEntities.COD_MISSILE.get(),
                    serverLevel,
                    x,
                    y,
                    z,
                    type,
                    argument,
                    new ItemStackHandler(27)
            );
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
