package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class BadSunClientEvents {

    private static boolean isBadSunDay = false;

    public static void setBadSunDay(boolean badSunDay) {
        isBadSunDay = badSunDay;
    }

    private static boolean isInSun(ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if (server == null) return false;
        if (!player.level().isDay()) return false;

        BlockPos pos = BlockPos.containing(player.position().add(0, 1, 0));
        ServerLevel level = player.serverLevel();

        Vec3 sunPos = getSunDirection(level, 0).scale(1000);

        return level.canSeeSky(pos) && level.clip(new ClipContext(
                player.position().add(new Vec3(0,1.8,0)),
                sunPos,
                ClipContext.Block.VISUAL,
                ClipContext.Fluid.NONE,
                player
        )).getType() == HitResult.Type.MISS;
    }

    public static Vec3 getSunDirection(Level level, float partialTicks) {
        float sunAngle = level.getSunAngle(partialTicks);
        double x = -Math.sin(sunAngle);
        double y = Math.cos(sunAngle);
        double z = 0;

        return new Vec3(x, y, z).normalize();
    }

    @SubscribeEvent
    public static void sunBlackBoxRender(RenderGuiLayerEvent.Post event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.level != null)
        {

            Vec3 sunPos = getSunDirection(minecraft.level, 0).scale(1000);

            boolean canSeeSun = minecraft.level.clip(new ClipContext(
                    minecraft.player.position().add(new Vec3(0,1.9,0)),
                    sunPos,
                    ClipContext.Block.VISUAL,
                    ClipContext.Fluid.NONE,
                    minecraft.player
            )).getType() == HitResult.Type.MISS;

            Vec3 sunDir = getSunDirection(minecraft.level, minecraft.getFrameTimeNs());
            Vec2 sunScreen = projectDirectionToScreen(sunDir, minecraft);

            if((sunScreen == null || !canSeeSun) || !isBadSunDay) return;

            GuiGraphics graphics = event.getGuiGraphics();
            int size = 48;
            int x1 = Mth.floor(sunScreen.x - size);
            int y1 = Mth.floor(sunScreen.y - size);
            int x2 = Mth.floor(sunScreen.x + size);
            int y2 = Mth.floor(sunScreen.y + size);

            graphics.fill(x1, y1, x2, y2, 0xFF000000);
        }
    }

    public static Vec2 projectDirectionToScreen(Vec3 dirWorld, Minecraft mc) {
        Camera cam = mc.gameRenderer.getMainCamera();
        Quaternionf inverseCamRot = new Quaternionf(cam.rotation()).conjugate();
        Vector3f dirCamera = new Vector3f(
                (float) dirWorld.x,
                (float) dirWorld.y,
                (float) dirWorld.z
        ).normalize().rotate(inverseCamRot);
        if (dirCamera.z >= 0) return null;
        float fovRad = (float) Math.toRadians(mc.options.fov().get() * mc.player.getFieldOfViewModifier());
        float aspect = (float) mc.getWindow().getWidth() /
                (float) mc.getWindow().getHeight();
        float tanHalfFov = (float) Math.tan(fovRad / 2.0f);

        float ndcX = (dirCamera.x / -dirCamera.z) / (tanHalfFov * aspect);
        float ndcY = (dirCamera.y / -dirCamera.z) / tanHalfFov;

        float screenX = (ndcX * 0.5f + 0.5f) * mc.getWindow().getGuiScaledWidth();
        float screenY = (0.5f - ndcY * 0.5f) * mc.getWindow().getGuiScaledHeight();

        return new Vec2(screenX, screenY);
    }

}
