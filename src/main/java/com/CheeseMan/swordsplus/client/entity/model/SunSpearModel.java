package com.CheeseMan.swordsplus.client.entity.model;

import com.CheeseMan.swordsplus.common.entity.SunSpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunSpearModel extends EntityModel<SunSpearEntity>{
	
	public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sun_spear/sun_spear.png");
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	
	
	public SunSpearModel() {
		super(RenderType::entitySolid);
		texWidth = 16;
		texHeight = 16;
		
		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 10).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(0, 11).addBox(0.0F, -4.0F, -14.0F, 0.0F, 4.0F, 5.0F, 0.0F, true);
		bb_main.texOffs(17, 10).addBox(-2.0F, -4.0F, -9.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
		bb_main.texOffs(0, 9).addBox(-1.0F, -3.0F, -7.0F, 2.0F, 2.0F, 17.0F, 0.0F, false);
		
		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-7.3085F, -2.3644F, -12.5513F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.2729F, 0.286F, 0.7813F);
		cube_r1.texOffs(19, 0).addBox(0.0F, -3.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-7.3085F, -2.3644F, -12.5513F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.3927F, 0.0F);
		cube_r2.texOffs(19, 0).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);
	}
	

	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(SunSpearEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		// TODO Auto-generated method stub
		
	}
}
	

