package com.mc3699.codmod.item.transponder;

import com.mc3699.codmod.network.TransponderSetIDPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.awt.*;

public class TransponderIDScreen extends Screen {

    private final ItemStack stack;
    public TransponderIDScreen(ItemStack stack) {
        super(Component.literal("Transponder"));
        this.stack = stack;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        EditBox textField = new EditBox(font, width/2 - 100, height / 2 - 10 , 200, 20, Component.literal("ID"));
        textField.setValue(TransponderItem.getID(stack));
        addRenderableWidget(textField);
        addRenderableWidget(Button.builder(Component.literal("Save"), button -> {
            PacketDistributor.sendToServer(new TransponderSetIDPayload(textField.getValue()));
            minecraft.setScreen(null);
        }).pos(width / 2 - 50, height / 2 + 20).size(100,20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(font, title, width / 2, height / 2 - 30, 0xFFFFFF);
    }
}
