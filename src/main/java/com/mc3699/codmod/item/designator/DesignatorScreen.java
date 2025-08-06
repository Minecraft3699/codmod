package com.mc3699.codmod.item.designator;

import com.mc3699.codmod.item.transponder.TransponderItem;
import com.mc3699.codmod.network.DesignatorChannelPayload;
import com.mc3699.codmod.network.TransponderSetIDPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class DesignatorScreen extends Screen {

    private final ItemStack stack;

    public DesignatorScreen(ItemStack stack) {
        super(Component.literal("Target Designator"));
        this.stack = stack;
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        EditBox textField = new EditBox(font, width/2 - 20, height / 2 - 10 , 40, 20, Component.literal("Set Channel"));
        textField.setFilter(text -> text.matches("\\d*"));
        textField.setValue(String.valueOf(DesignatorItem.getChannel(stack)));
        textField.setMaxLength(5);
        addRenderableWidget(textField);
        addRenderableWidget(Button.builder(Component.literal("Set Channel"), button -> {

            if(Integer.parseInt(textField.getValue()) > 65535) {textField.setValue("65535");}

            PacketDistributor.sendToServer(new DesignatorChannelPayload(Integer.parseInt(textField.getValue())));
            minecraft.setScreen(null);
        }).pos(width / 2 - 50, height / 2 + 20).size(100,20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(font, title, width / 2, height / 2 - 30, 0xFFFFFF);
    }
}
