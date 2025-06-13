package com.mc3699.codmod.entity.swarmCod;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.EntityAnimations;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class SwarmCodEntityModel extends HierarchicalModel<SwarmCodEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(
                    Codmod.MOD_ID,
                    "swarm_cod"
            ), "main"
    );
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart tailfin;
    private final ModelPart waist;

    public SwarmCodEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");
        this.tailfin = this.body.getChild("tailfin");
        this.waist = this.body.getChild("waist");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -4.0F, 1.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(20, -6)
                        .addBox(0.0F, -5.0F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(22, -1)
                        .addBox(0.0F, 0.0F, 3.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.9992F, -2.0008F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 0)
                        .addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 0.0F)
        );

        PartDefinition leftFin = body.addOrReplaceChild(
                "leftFin",
                CubeListBuilder.create()
                        .texOffs(24, 4)
                        .addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6109F)
        );

        PartDefinition rightFin = body.addOrReplaceChild(
                "rightFin",
                CubeListBuilder.create()
                        .texOffs(24, 1)
                        .addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6109F)
        );

        PartDefinition tailfin = body.addOrReplaceChild(
                "tailfin",
                CubeListBuilder.create()
                        .texOffs(20, 1)
                        .addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 8.0F)
        );

        PartDefinition waist = body.addOrReplaceChild(
                "waist",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        body.render(poseStack, vertexConsumer, i, i1);
    }

    @Override
    public ModelPart root() {
        return body;
    }

    @Override
    public void setupAnim(
            SwarmCodEntity swarmCodEntity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.root().xRot = 0;
        float animTime = animationProgress / 20;
        animate(swarmCodEntity, EntityAnimations.SWARM_COD_WALK_ANIM, animTime, 1f);
    }

    private void animate(SwarmCodEntity entity, AnimationDefinition animation, float time, float scale) {
        KeyframeAnimations.animate(this, animation, (long) (time * 1000L), scale, new Vector3f(0, 0, 0));
    }
}