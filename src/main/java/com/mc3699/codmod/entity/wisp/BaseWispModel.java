package com.mc3699.codmod.entity.wisp;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.EntityAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class BaseWispModel extends HierarchicalModel<BaseWispEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(
                    Codmod.MOD_ID,
                    "yellow_wisp"
            ), "main"
    );
    private final ModelPart body;
    private final ModelPart base;
    private final ModelPart outer;

    public BaseWispModel(ModelPart root) {
        this.body = root.getChild("body");
        this.base = this.body.getChild("base");
        this.outer = this.body.getChild("outer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        PartDefinition base = body.addOrReplaceChild(
                "base",
                CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, 0.0F)
        );

        PartDefinition outer = body.addOrReplaceChild(
                "outer",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.5F)),
                PartPose.offset(0.0F, -4.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public ModelPart root() {
        return body;
    }

    private void animate(BaseWispEntity entity, AnimationDefinition animation, float time, float scale) {
        KeyframeAnimations.animate(this, animation, (long) (time * 1000L), scale, new Vector3f(0, 0, 0));
    }

    @Override
    public void setupAnim(
            BaseWispEntity baseWispEntity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.root().xRot = 0;
        float animTime = animationProgress / 20;
        animate(baseWispEntity, EntityAnimations.WISP_MAIN, animTime, 1f);
    }
}