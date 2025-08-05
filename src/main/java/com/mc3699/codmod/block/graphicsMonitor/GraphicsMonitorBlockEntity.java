package com.mc3699.codmod.block.graphicsMonitor;

import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GraphicsMonitorBlockEntity extends BlockEntity {

    //TODO

    private BufferedImage[] frameBuffers = {
            new BufferedImage(1024,1024,BufferedImage.TYPE_INT_ARGB),
            new BufferedImage(1024,1024,BufferedImage.TYPE_INT_ARGB)
    };

    private int activeFrameBuffer = 0;

    public BufferedImage getActiveFramebuffer()
    {
        return frameBuffers[activeFrameBuffer];
    }

    public void swapFramebuffer() {
        activeFrameBuffer = (activeFrameBuffer + 1) % 2;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("activeBuffer", activeFrameBuffer);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(tag.contains("activeBuffer"))
        {
            activeFrameBuffer = tag.getInt("activeBuffer");
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public GraphicsMonitorBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.GRAPHICS_MONITOR.get(), pos, blockState);
    }
}
