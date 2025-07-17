package com.mc3699.codmod.commands;

import com.mc3699.codmod.colors.ColorManager;
import com.mc3699.codmod.event.BadSunEvent;
import com.mc3699.codmod.handlers.BadSunEvents;
import com.mc3699.codmod.network.ClientBadSunSyncPayload;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public class StopBadSunCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sunBeGood")
                .requires((dis) -> dis.hasPermission(3))
                .executes(context ->{
                    BadSunEvents.isBadSunDay = false;
                    PacketDistributor.sendToAllPlayers(new ClientBadSunSyncPayload(false));
                    PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0, true));
                    return 1;
                })
        );
    }

}
