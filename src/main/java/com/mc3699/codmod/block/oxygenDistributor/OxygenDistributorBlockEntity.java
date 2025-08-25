package com.mc3699.codmod.block.oxygenDistributor;

import com.mc3699.codmod.handlers.OxygenHandler;
import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OxygenDistributorBlockEntity extends BlockEntity {

    private int effectiveArea;
    private boolean shouldRender;

    public OxygenDistributorBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.OXYGEN_DISTRIBUTOR.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("range", effectiveArea);
        tag.putBoolean("render", shouldRender);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.effectiveArea = tag.getInt("range");
        this.shouldRender = tag.getBoolean("render");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("range", effectiveArea);
        tag.putBoolean("render", shouldRender);
        return tag;
    }

    public void setEffectiveArea(int effectiveArea) {
        this.effectiveArea = effectiveArea;
    }

    public int getEffectiveArea() {
        return this.effectiveArea;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
        setChanged();
        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean shouldRender() {
        return this.shouldRender;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {

        if(level instanceof ServerLevel serverLevel && serverLevel.getGameTime() % 20 == 0 && be instanceof OxygenDistributorBlockEntity distributor)
        {
            int newRange = 0;
            for(int i = 1; i < 6; i++)
            {
                BlockState checkState = serverLevel.getBlockState(pos.above(i));
                Vec3 checkedPos = pos.getCenter().add(0,i,0);
                if(checkState.is(BlockTags.LEAVES))
                {
                    newRange++;
                    serverLevel.sendParticles(ParticleTypes.CLOUD, checkedPos.x, checkedPos.y, checkedPos.z, 10,0,0,0,0.05);
                } else {
                    break;
                }
            }

            int range = newRange*6;
            distributor.setEffectiveArea(range);
            distributor.setChanged();
            serverLevel.sendBlockUpdated(pos, state, state, 3);

            AABB searchArea = new AABB(pos).inflate(range);
            List<LivingEntity> nearEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, searchArea);

            for(LivingEntity entity : nearEntities)
            {
                if(pos.getCenter().distanceTo(entity.position()) < range)
                {
                    CompoundTag tag = entity.getPersistentData();
                    tag.putInt(OxygenHandler.OXYGEN_KEY, 300);
                }
            }
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
