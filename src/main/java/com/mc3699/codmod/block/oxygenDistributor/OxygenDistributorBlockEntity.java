package com.mc3699.codmod.block.oxygenDistributor;

import com.mc3699.codmod.handlers.OxygenHandler;
import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class OxygenDistributorBlockEntity extends BlockEntity {

    private int range = 4;

    public OxygenDistributorBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.OXYGEN_DISTRIBUTOR.get(), pos, blockState);
    }

    public int getRange() {
        return range;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("range", range);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.range = tag.getInt("range");
        super.loadAdditional(tag,registries);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {

        if(level instanceof ServerLevel serverLevel && serverLevel.getGameTime() % 20 == 0 && be instanceof OxygenDistributorBlockEntity distributor)
        {
            AABB searchArea = new AABB(pos).inflate(distributor.getRange());
            List<LivingEntity> nearEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, searchArea);

            for(LivingEntity entity : nearEntities)
            {
                if(pos.getCenter().distanceTo(entity.position()) > distributor.getRange())
                {
                    CompoundTag tag = entity.getPersistentData();
                    tag.putInt(OxygenHandler.OXYGEN_KEY, 300);
                }
            }
        }
    }
}
