package com.mc3699.codmod.commands;

import com.mc3699.codmod.colors.ColorManager;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public class ColorCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setcolor")
                .then(Commands.argument("color", StringArgumentType.string()).executes(context -> {
                    int color = Integer.parseInt(StringArgumentType.getString(context, "color"), 16);
                    ColorManager.setFoliageColor(color);
                    PacketDistributor.sendToAllPlayers(new FoliageColorPayload(color, false));
                    context.getSource()
                            .sendSuccess(() -> Component.literal("Foliage color set to " + color), true);
                    return 1;
                }))
                .then(Commands.literal("reset").executes(context -> {
                    ColorManager.resetFoliageColor();
                    PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0, true));
                    context.getSource()
                            .sendSuccess(
                                    () -> Component.literal("Foliage color reset to biome defaults"),
                                    true
                            );
                    return 1;
                })));
    }
}
