package com.mc3699.codmod.entity.codmissile;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CodMissileModel<T extends Entity> extends HierarchicalModel<CodMissileEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Codmod.MODID, "cod_missile"), "main");
	private final ModelPart bodyRoot;

	public CodMissileModel(ModelPart root) {
		this.bodyRoot = root.getChild("bodyRoot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bodyRoot = partdefinition.addOrReplaceChild("bodyRoot", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, -20.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 11).addBox(1.0F, -12.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tailfin_r1 = bodyRoot.addOrReplaceChild("tailfin_r1", CubeListBuilder.create().texOffs(20, 1).addBox(0.0F, -4.0F, 8.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -14.0F, 0.0F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition tailfin_r2 = bodyRoot.addOrReplaceChild("tailfin_r2", CubeListBuilder.create().texOffs(20, 1).addBox(0.0F, -4.0F, 8.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r1 = bodyRoot.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 11).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head_r1 = bodyRoot.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.9992F, -4.0008F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, -6).addBox(0.0F, -5.0F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -4.0F, 1.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return bodyRoot;
	}

	@Override
	public void setupAnim(CodMissileEntity codMissileEntity, float v, float v1, float v2, float v3, float v4) {

	}
}