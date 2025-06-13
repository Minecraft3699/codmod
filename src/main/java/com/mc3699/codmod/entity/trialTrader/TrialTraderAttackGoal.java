package com.mc3699.codmod.entity.trialTrader;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

import java.util.List;

public class TrialTraderAttackGoal extends Goal {
    private final TrialTraderEntity entity;
    List<String> lines = List.of("That isn't yours", "Give that back");
    int defaultChatDelay = 60;
    int chatDelay = defaultChatDelay;


    public TrialTraderAttackGoal(TrialTraderEntity entity) {
        this.entity = entity;
    }

    private void sendChat(String message) {
        entity.getTarget().sendSystemMessage(Component.literal(message));
    }

    @Override
    public void start() {
        sendChat("I know what you did");
    }

    @Override
    public boolean canUse() {
        return entity.getTarget() != null && entity.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.getTarget() != null && entity.getTarget().isAlive();
    }

    @Override
    public void stop() {
        //sendChat("Do not come back...");
        super.stop();
    }

    @Override
    public void tick() {
        // handle chat
        if (chatDelay > 0) {
            chatDelay--;
        } else {
            sendChat(lines.get(0));
            chatDelay = defaultChatDelay;
        }

        // handle chase
        PathNavigation navigation = entity.getNavigation();
        navigation.moveTo(entity.getTarget(), 1.5f);
        super.tick();
    }
}
