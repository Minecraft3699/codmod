package com.mc3699.codmod.peripheral;

import com.mojang.brigadier.ParseResults;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandPeripheral implements IPeripheral {

    private final IPocketAccess pocketAccess;

    public CommandPeripheral(IPocketAccess pocketAccess)
    {
        this.pocketAccess = pocketAccess;
    }

    @LuaFunction
    public Object[] executeCommand(String command) throws LuaException
    {
        Entity entity = pocketAccess.getEntity();
        if(!(entity instanceof ServerPlayer player))
        {
            throw new LuaException("U aren't a player dumbass");
        }

        Level level = player.level();
        List<String> output = new ArrayList<>();
        boolean[] success = {false};

        CommandSourceStack sourceStack = player.createCommandSourceStack()
                .withPermission(2)
                .withPosition(player.position())
                .withEntity(player)
                .withLevel(player.serverLevel())
                .withSource(new CommandSource() {
                    @Override
                    public void sendSystemMessage(Component component) {
                        output.add(component.getString());
                    }

                    @Override
                    public boolean acceptsSuccess() {
                        success[0] = true;
                        return true;
                    }

                    @Override
                    public boolean acceptsFailure() {
                        success[0] = false;
                        return true;
                    }

                    @Override
                    public boolean shouldInformAdmins() {
                        return false;
                    }
                });

        ParseResults<CommandSourceStack> parseResults = player.serverLevel().getServer().getCommands().getDispatcher().parse(command, sourceStack);
        level.getServer().getCommands().performCommand(parseResults, command);

    if(!output.isEmpty())
    {
        return new Object[]{String.join("\n", output)};
    }
    return new Object[]{success[0]};
    }

    @Override
    public String getType() {
        return "command_module";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
