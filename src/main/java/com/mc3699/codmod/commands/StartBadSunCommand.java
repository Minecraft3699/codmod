package com.mc3699.codmod.commands;

import com.mc3699.codmod.handlers.BadSunEvents;
import com.mc3699.codmod.network.ClientBadSunSyncPayload;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.network.PacketDistributor;

public class StartBadSunCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sunBeBad")
                .requires((dis) -> dis.hasPermission(3))
                .executes(context ->{
                    BadSunEvents.isBadSunDay = true;
                    PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0xECD38A, false));
                    PacketDistributor.sendToAllPlayers(new ClientBadSunSyncPayload(true));
                    return 1;
                })
        );
    }

}
