package com.mc3699.codmod.block.mantleKey;

import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MantleKeyBlockEntity extends BlockEntity {

    private int beamLength = 1;

    public MantleKeyBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.MANTLE_KEY.get(), pos, blockState);
    }

    public void setBeamLength(int beamLen) {
        this.beamLength = beamLen;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        setChanged();
    }

    public int getBeamLength() {
        return this.beamLength;
    }

    public static void applyRedstone(ServerLevel level, BlockPos startPos, Direction facing, int range)
    {
        BlockPos pos = startPos;
        for(int i = 0; i < range; i++)
        {
            pos = pos.offset(facing.getNormal());
            if(!level.isLoaded(pos))
            {
                return;
            }

            BlockState state = level.getBlockState(pos);
            if(state.hasProperty(RedStoneWireBlock.POWER))
            {
                BlockState newState = state.setValue(RedStoneWireBlock.POWER, 15);
                level.setBlock(pos, newState, 3);
            }
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        MantleKeyBlockEntity blockEntity = (MantleKeyBlockEntity) level.getBlockEntity(blockPos);


        if(level instanceof ServerLevel serverLevel)
        {
            applyRedstone(serverLevel, blockPos, blockState.getValue(MantleKeyBlock.FACING), blockEntity.getBeamLength());
            if(blockEntity.getBeamLength() <= 64)
            {
                blockEntity.setBeamLength(blockEntity.getBeamLength() + 1);
            }
        }

    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("beam_length", beamLength);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        beamLength = tag.getInt("beam_length");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


}
