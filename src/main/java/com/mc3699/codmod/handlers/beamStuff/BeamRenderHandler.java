package com.mc3699.codmod.handlers.beamStuff;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(value = Dist.CLIENT)
public class BeamRenderHandler {

    private static final Map<UUID, BeamInstance> activeBeams = new HashMap<>();

    public static void spawnBeam(Vec3 start, Vec3 end, float width, int lifeTime, int color)
    {
        activeBeams.put(UUID.randomUUID(), new BeamInstance(start, end, width, lifeTime, color));
    }

    @SubscribeEvent
    public static void render(RenderLevelStageEvent event)
    {
        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        PoseStack pose = event.getPoseStack();
        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        activeBeams.values().removeIf(beamInstance -> --beamInstance.lifeTime <= 0);

        MultiBufferSource buffer = minecraft.renderBuffers().bufferSource();

        for(BeamInstance beam : activeBeams.values()) {
            BeamRenderer.renderBeam(pose, buffer, beam.startPos.subtract(camPos), beam.endPos.subtract(camPos), beam.color, beam.width, camera.getPartialTickTime(), minecraft.level.getGameTime(), beam.lifeTime);
        }
    }

    private static class BeamInstance {
        public int lifeTime;
        public Vec3 startPos;
        public Vec3 endPos;
        public float width;
        public int color;

        BeamInstance(Vec3 start, Vec3 end, float width, int lifeTime, int color) {
            this.lifeTime = lifeTime;
            this.endPos = end;
            this.startPos = start;
            this.width = width;
            this.color = color;
        }
    }

}
