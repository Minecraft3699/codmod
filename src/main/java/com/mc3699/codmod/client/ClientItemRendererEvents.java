package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.detector.DetectorItemRenderer;
import com.mc3699.codmod.registry.CodItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientItemRendererEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return DetectorItemRenderer.INSTANCE;
                    }
                }, CodItems.DETECTOR.get()
        );

        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player,
                                                           HumanoidArm arm, ItemStack itemInHand,
                                                           float partialTick, float equipProcess,
                                                           float swingProcess) {
                        if (player.isUsingItem() && player.getUseItem() == itemInHand) {
                            poseStack.translate(0.75,0.1,0.45);
                            poseStack.mulPose(Axis.XN.rotationDegrees(15));
                            poseStack.mulPose(Axis.YN.rotationDegrees(10));
                            poseStack.mulPose(Axis.ZP.rotationDegrees(45));
                        }
                        return false;
                    }
                },
                CodItems.STARSTRUCK_SPEAR.get()
        );
    }

}
