package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.wisp.BaseWispEntityRenderer;
import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.wendigodrip.thebrokenscript.api.util.Boxes;
import foundry.veil.api.client.render.MatrixStack;
import foundry.veil.api.event.VeilRenderLevelStageEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.joml.Matrix4fc;

import java.util.Objects;

@EventBusSubscriber(value = Dist.CLIENT, modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.MOD    )
public class ClientEvents {

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColors itemColors = event.getItemColors();

        itemColors.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 1) {
                        int color = stack.getOrDefault(CodComponents.SAUSAGE_COLOR, 0xFF00FF); // 0xRRGGBB
                        return 0xFF000000 | color; // no alpha
                    }
                    return -1;
                },
                CodItems.SAUSAGE.get()
        );



    }

}
