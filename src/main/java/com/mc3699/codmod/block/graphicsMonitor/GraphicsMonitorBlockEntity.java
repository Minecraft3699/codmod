package com.mc3699.codmod.block.graphicsMonitor;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GraphicsMonitorBlockEntity extends BlockEntity {

    private final NativeImage[] frameBuffers = {
            new NativeImage(NativeImage.Format.RGBA, 1024, 1024, true),
            new NativeImage(NativeImage.Format.RGBA, 1024, 1024, true)
    };
    private int activeFrameBuffer = 0;
    private boolean dirty = true;

    public GraphicsMonitorBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.GRAPHICS_MONITOR.get(), pos, blockState);
    }

    public NativeImage getActiveFramebuffer() {
        return frameBuffers[activeFrameBuffer];
    }

    public void swapFramebuffer() {
        activeFrameBuffer = (activeFrameBuffer + 1) % 2;
        dirty = true;
        setChanged();
    }

    public boolean isDirty() { return dirty; }
    public void clearDirty() { dirty = false; }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("activeBuffer", activeFrameBuffer);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("activeBuffer")) {
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

    @Override
    public void onLoad() {
        super.onLoad();
        drawTestPattern();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void drawTestPattern() {
        NativeImage fb = getActiveFramebuffer();

        int width = fb.getWidth();
        int height = fb.getHeight();

        int white = rgba(255, 255, 255, 255);
        int black = rgba(0, 0, 0, 255);

        for (int x = 0; x < width; x++) {
            int color = (x / 32) % 2 == 0 ? white : black;

            for (int y = 0; y < height; y++) {
                fb.setPixelRGBA(x, y, 0xFFFFFFFF);
            }
        }

        dirty = true;
        setChanged();
    }

    private static int rgba(int r, int g, int b, int a) {
        return (a << 24) | (b << 16) | (g << 8) | r;
    }

}
