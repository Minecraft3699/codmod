package com.mc3699.codmod.client;

import com.mc3699.codmod.item.designator.DesignatorScreen;
import com.mc3699.codmod.item.transponder.TransponderIDScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientScreenHandler {

    public static void openScreen(String id)
    {
        Player player = Minecraft.getInstance().player;
        if(player != null)
        {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            switch(id)
            {
                case "designator" -> Minecraft.getInstance().setScreen(new DesignatorScreen(stack));
                case "transponder" -> Minecraft.getInstance().setScreen(new TransponderIDScreen(stack));
            }
        }
    }

}
