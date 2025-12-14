package com.mc3699.codmod.commands;

import com.mc3699.codmod.network.ImageFileRequestPayload;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

public class ImageCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sendImage").requires(src -> src.hasPermission(3)).then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("x", IntegerArgumentType.integer()).then(Commands.argument("y", IntegerArgumentType.integer()).then(Commands.argument("scaleX", IntegerArgumentType.integer(1)).then(Commands.argument("scaleY", IntegerArgumentType.integer(1)).then(Commands.argument("duration", IntegerArgumentType.integer(1)).then(Commands.argument("path", StringArgumentType.greedyString()).executes(ctx -> {

            ServerPlayer sender = ctx.getSource().getPlayerOrException();

            List<UUID> targetUUIDs = EntityArgument.getPlayers(ctx, "targets").stream().map(ServerPlayer::getUUID).toList();

            int x = IntegerArgumentType.getInteger(ctx, "x");
            int y = IntegerArgumentType.getInteger(ctx, "y");
            int scaleX = IntegerArgumentType.getInteger(ctx, "scaleX");
            int scaleY = IntegerArgumentType.getInteger(ctx, "scaleY");
            int duration = IntegerArgumentType.getInteger(ctx, "duration");
            String path = StringArgumentType.getString(ctx, "path");

            PacketDistributor.sendToPlayer(sender, new ImageFileRequestPayload(targetUUIDs, path, x, y, scaleX, scaleY, duration));

            return 1;
        })))))))));
    }

}
