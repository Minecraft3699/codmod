package com.mc3699.codmod.entity.parachuteChest;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ParachuteChestEntityModel extends HierarchicalModel<ParachuteChestEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(
                    Codmod.MOD_ID,
                    "parachute_chest"
            ), "main"
    );
    private final ModelPart chest;
    private final ModelPart bb_main;

    public ParachuteChestEntityModel(ModelPart root) {
        this.chest = root.getChild("chest");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition chest = partdefinition.addOrReplaceChild(
                "chest",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.0F, 7.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0)
                        .addBox(-7.0F, 1.0F, -7.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 19)
                        .addBox(-7.0F, -8.0F, -7.0F, 14.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
        );

        PartDefinition bb_main = partdefinition.addOrReplaceChild(
                "bb_main",
                CubeListBuilder.create()
                        .texOffs(0, 46)
                        .addBox(-11.0F, -32.5F, -8.0F, 22.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        PartDefinition cube_r1 = bb_main.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(0, 46)
                        .mirror()
                        .addBox(-2.0F, -2.0F, -8.0F, 12.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                        .mirror(false),
                PartPose.offsetAndRotation(-19.0F, -26.0F, 0.0F, 0.0F, 0.0F, -0.48F)
        );

        PartDefinition cube_r2 = bb_main.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create()
                        .texOffs(0, 43)
                        .addBox(-10.0F, -2.0F, -8.0F, 12.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(19.0F, -26.0F, 0.0F, 0.0F, 0.0F, 0.48F)
        );

        PartDefinition cube_r3 = bb_main.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create()
                        .texOffs(0, 44)
                        .addBox(-1.0F, -19.0F, -1.0F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.0F, -13.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
        );

        PartDefinition cube_r4 = bb_main.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create()
                        .texOffs(0, 44)
                        .addBox(-1.0F, -19.0F, -1.0F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return bb_main;
    }

    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            int color
    ) {
        poseStack.pushPose();
        poseStack.translate(0, 1.5, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        bb_main.render(poseStack, buffer, packedLight, packedOverlay, color);
        chest.render(poseStack, buffer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(ParachuteChestEntity parachuteChestEntity, float v, float v1, float v2, float v3, float v4) {

    }
}