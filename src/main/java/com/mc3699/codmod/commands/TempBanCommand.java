package com.mc3699.codmod.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.network.chat.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class TempBanCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tempban")
                .then(Commands.argument("player", StringArgumentType.string())
                        .then(Commands.argument("hours", IntegerArgumentType.integer(1))
                                .then(Commands.argument("reason", StringArgumentType.greedyString())
                                        .requires((dis) -> dis.hasPermission(3))
                                        .executes(context -> {
                                            String name = StringArgumentType.getString(context, "player");
                                            int hours = IntegerArgumentType.getInteger(context, "hours");
                                            String reason = StringArgumentType.getString(context, "reason");
                                            MinecraftServer server = context.getSource().getServer();

                                            Date banExpiry = new Date();
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(banExpiry);
                                            calendar.add(Calendar.HOUR, hours);
                                            banExpiry = calendar.getTime();

                                            Optional<GameProfile> profile = server.getProfileCache().get(name);
                                            if (profile.isEmpty()) {
                                                context.getSource().sendFailure(Component.literal("Player not found: " + name));
                                                return 0;
                                            }

                                            UserBanListEntry entry = new UserBanListEntry(
                                                    profile.get(),
                                                    new Date(),
                                                    context.getSource().getPlayer().getName().getString(),
                                                    banExpiry,
                                                    reason
                                            );
                                            server.getPlayerList().getBans().add(entry);
                                            ServerPlayer serverplayer = context.getSource().getServer().getPlayerList().getPlayer(profile.get().getId());

                                            if(serverplayer != null)
                                            {
                                                serverplayer.connection.disconnect(Component.literal("You have been temp-banned, join again for details."));
                                            }

                                            context.getSource().sendSuccess(
                                                    () -> Component.literal("Banned " + name + " for " + hours + " hours: " + reason),
                                                    true
                                            );
                                            return 1;
                                        })))));
    }
}