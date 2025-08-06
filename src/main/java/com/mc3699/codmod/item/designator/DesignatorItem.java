package com.mc3699.codmod.item.designator;

import com.mc3699.codmod.item.transponder.TransponderIDScreen;
import com.mc3699.codmod.network.OpenScreenPayload;
import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodSounds;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.network.Packet;
import dan200.computercraft.api.network.PacketSender;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesignatorItem extends Item {
    public DesignatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        if (stack.getItem() instanceof DesignatorItem) {

            if(player instanceof ServerPlayer serverPlayer)
            {
                if(player.level() instanceof ServerLevel serverLevel && !player.isShiftKeyDown()) {
                    int rednetChannel = stack.getOrDefault(CodComponents.CHANNEL, 1000);
                    BlockPos clicked = context.getClickedPos();

                    Map<String, Integer> sendPos = new HashMap<>();
                    sendPos.put("x", clicked.getX());
                    sendPos.put("y", clicked.getY());
                    sendPos.put("z", clicked.getZ());

                    serverLevel.playSound(null, player.getBlockPosBelowThatAffectsMyMovement(), CodSounds.DESIGNATE_TARGET.value(), SoundSource.MASTER);

                    player.getCooldowns().addCooldown(CodItems.DESIGNATOR.get(), 20);
                    sendMessage(serverLevel, rednetChannel, sendPos, player.position());
                    return InteractionResult.PASS;
                }

                if(player.isShiftKeyDown())
                {
                    PacketDistributor.sendToPlayer(serverPlayer, new OpenScreenPayload("designator"));
                }
            }


        }

        return InteractionResult.FAIL;
    }

    private void sendMessage(ServerLevel serverLevel, int rednetChannel, Map<String,Integer> sendPos, Vec3 txPos)
    {
        ComputerCraftAPI.getWirelessNetwork(serverLevel.getServer()).transmitSameDimension(new Packet(rednetChannel, rednetChannel, sendPos, new PacketSender() {
            @Override
            public Level getLevel() {
                return serverLevel;
            }

            @Override
            public Vec3 getPosition() {
                return txPos;
            }

            @Override
            public String getSenderID() {
                return "designator";
            }
        }), 10000);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Channel: " + stack.getOrDefault(CodComponents.CHANNEL, 1000)).withStyle(ChatFormatting.GRAY));
    }

    public static void setChannel(ItemStack stack, int channel) {
        stack.set(CodComponents.CHANNEL, channel);
    }

    public static int getChannel(ItemStack stack) {
        return stack.getOrDefault(CodComponents.CHANNEL, 1000);
    }
}
