package com.mc3699.codmod.block.backrooms;

import com.mc3699.codmod.entity.wisp.BaseWispEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3dc;

import java.util.Map;
import java.util.WeakHashMap;

public class CeilingLightBlockEntityRenderer implements BlockEntityRenderer<CeilingLightBlockEntity> {

    private PointLight light;
    private final BlockEntityRendererProvider.Context context;

    public CeilingLightBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }


    private static final Map<CeilingLightBlockEntity, PointLight> lightMap = new WeakHashMap<>();

    @Override
    public void render(CeilingLightBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        //TODO
    }

}
