package com.mc3699.codmod.technology.foundation;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class BaseMachineScreen<T extends BaseMachineMenu> extends AbstractContainerScreen<T> {

    private final ResourceLocation bgTexture;

    public BaseMachineScreen(T menu, Inventory playerInventory, Component title, ResourceLocation bgTexture) {
        super(menu, playerInventory, title);
        this.bgTexture = bgTexture;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShaderTexture(0,bgTexture);
        guiGraphics.blit(bgTexture, leftPos, topPos, 0,0, imageWidth, imageHeight);
    }
}
