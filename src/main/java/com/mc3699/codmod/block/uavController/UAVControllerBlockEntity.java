package com.mc3699.codmod.block.uavController;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.entity.uav.UAVEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class UAVControllerBlockEntity extends BlockEntity {

    private UUID uavUUID = null;

    public UAVControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.UAV_CONTROLLER.get(), pos, blockState);
    }

    public UUID getUavUUID() {
        return uavUUID;
    }

    public UAVEntity getUAVEntity(ServerLevel serverLevel)
    {
        if(uavUUID != null)
        {
            return (UAVEntity) serverLevel.getEntity(uavUUID);
        }
        return null;
    }

    public void setUavUUID(UUID uavUUID) {
        this.uavUUID = uavUUID;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(uavUUID != null)
        {
            tag.putUUID("linked", uavUUID);
        }
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(tag.contains("linked"))
        {
            uavUUID = tag.getUUID("linked");
        }
        super.loadAdditional(tag, registries);
    }
}
