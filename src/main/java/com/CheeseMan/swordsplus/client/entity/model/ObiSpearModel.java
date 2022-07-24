package com.CheeseMan.swordsplus.client.entity.model;

import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

//Made with Blockbench 4.3.0
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
//Paste this class into your mod and generate all required imports


public class ObiSpearModel extends EntityModel<ObiSpearEntity> {
	private final ModelRenderer Feather;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer bb_main;

	public ObiSpearModel() {
		texWidth = 32;
		texHeight = 16;

		Feather = new ModelRenderer(this);
		Feather.setPos(-3.0F, 22.5F, -6.75F);
		setRotationAngle(Feather, -2.6445F, 0.445F, -2.2415F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-4.3085F, -0.8644F, -5.8013F);
		Feather.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.2729F, 0.286F, 0.7813F);
		cube_r1.texOffs(0, 0).addBox(0.0F, -3.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-4.3085F, -0.8644F, -5.8013F);
		Feather.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.3927F, 0.0F);
		cube_r2.texOffs(0, 0).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(0.0F, -4.0F, -14.0F, 0.0F, 4.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-2.0F, -4.0F, -9.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-1.0F, -3.0F, -7.0F, 2.0F, 2.0F, 17.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Feather.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(ObiSpearEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		// TODO Auto-generated method stub
		
	}
}