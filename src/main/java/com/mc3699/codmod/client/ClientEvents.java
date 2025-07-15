package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.wisp.BaseWispEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.wendigodrip.thebrokenscript.api.util.Boxes;
import foundry.veil.api.client.render.MatrixStack;
import foundry.veil.api.event.VeilRenderLevelStageEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import org.joml.Matrix4fc;

import java.util.Objects;

@EventBusSubscriber(value = Dist.CLIENT, modid = Codmod.MOD_ID)
public class ClientEvents {
    static {
        register();
    }



    @SubscribeEvent
    public static void onClientTickEnd(ClientTickEvent.Post event) {
        BaseWispEntityRenderer.cleanup();
    }

    public static void register() {
//        VeilEventPlatform.INSTANCE.onVeilRenderLevelStage(ClientEvents::onRenderLevel);
    }

    private static void onRenderLevel(
            VeilRenderLevelStageEvent.Stage stage,
            LevelRenderer levelRenderer,
            MultiBufferSource.BufferSource bufferSource,
            MatrixStack matrixStack,
            Matrix4fc frustumMatrix,
            Matrix4fc projectionMatrix,
            int renderTick,
            DeltaTracker deltaTracker,
            Camera camera,
            Frustum frustum
    ) {
        if (stage != VeilRenderLevelStageEvent.Stage.AFTER_LEVEL) {
            return;
        }

        Minecraft john = Minecraft.getInstance();
        // ^^ man this guy sucks

        LocalPlayer player = Objects.requireNonNull(john.player);
        ClientLevel level = Objects.requireNonNull(john.level);
//        LevelRenderer levelRenderer = john.levelRenderer;

        float sunAngle = level.getSunAngle(0f) + (Mth.PI / 2);

        // yaw = -90, towards +x, axis = z

        float x = (float) (Math.cos(sunAngle)) * 700f;
        float y = (float) (Math.sin(sunAngle)) * 700f;
        float z = 0;

        Vec3 sunPos = new Vec3(x, y, z);
        Vec3 camPos = camera.getPosition();
        PoseStack ps = matrixStack.toPoseStack();

//        Vector3f transform = camera.rotation().transform(new Vec3(1, 1, 0).toVector3f());

        ps.pushPose();
//        ps.translate(transform.x * camPos.x, transform.y * camPos.y, transform.z * camPos.z); // idk why i thought this would work lol
        ps.translate(-camPos.x, -camPos.y, -camPos.z);
//        ps.translate(-sunPos.x, -sunPos.y, -sunPos.z);

        DebugRenderer.renderFilledBox(
                ps,
                bufferSource,
                Boxes.INSTANCE.aabb(sunPos.add(player.position()), 1).inflate(20),
                0f,
                1f,
                1f,
                1f
        );

        ps.popPose();
    }
}
