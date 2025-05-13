package com.mc3699.codmod.test;


import com.mc3699.codmod.Codmod;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

//@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Codmod.MODID)
public class SignAIHandler {



    //@SubscribeEvent
    public static void onChat(ServerChatEvent event)
    {
        /*
        String inputMessage = event.getRawText();
        OllamaClient.queryOllama("mistral-nemo:latest", inputMessage, event.getPlayer().getServer())
            .thenAccept(response ->
                {

                    if(event.getPlayer().level() instanceof ServerLevel serverLevel)
                    {
                        AISignGen.updateSign(serverLevel, event.getPlayer().getOnPos(), response);
                    }


                    //event.getPlayer().getServer().getPlayerList().broadcastSystemMessage(Component.literal(response), false);
                }
            );
         */
    }

}
