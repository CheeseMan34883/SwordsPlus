package com.CheeseMan.swordsplus.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;

//Made with Blockbench 4.2.5
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
//Paste this class into your mod and generate all required imports


public class Wizard<T extends Entity> extends SegmentedModel<T> implements IHasHead, IHeadToggle {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer nose;
	private final ModelRenderer hat;
	private final ModelRenderer Hood;
	private final ModelRenderer arms;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;

	public Wizard() {
		texWidth = 64;
		texHeight = 128;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, true);
		body.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, true);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(head);
		head.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, true);

		nose = new ModelRenderer(this);
		nose.setPos(0.0F, -2.0F, 0.0F);
		head.addChild(nose);
		nose.texOffs(0, 0).addBox(-1.0F, 0.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F, true);
		nose.texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);

		hat = new ModelRenderer(this);
		hat.setPos(5.0F, -8.03F, -5.0F);
		head.addChild(hat);
		

		Hood = new ModelRenderer(this);
		Hood.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(Hood);
		Hood.texOffs(0, 104).addBox(-5.0F, -10.75F, -4.5F, 10.0F, 11.0F, 9.0F, 0.0F, false);

		arms = new ModelRenderer(this);
		arms.setPos(0.0F, 3.0F, -1.0F);
		body.addChild(arms);
		setRotationAngle(arms, -0.75F, 0.0F, 0.0F);
		arms.texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, true);
		arms.texOffs(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
		arms.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);

		leg0 = new ModelRenderer(this);
		leg0.setPos(2.0F, 12.0F, 0.0F);
		body.addChild(leg0);
		leg0.texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		leg1 = new ModelRenderer(this);
		leg1.setPos(-2.0F, 12.0F, 0.0F);
		body.addChild(leg1);
		leg1.texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		boolean flag = false;
	      if (entity instanceof AbstractVillagerEntity) {
	         flag = ((AbstractVillagerEntity)entity).getUnhappyCounter() > 0;
	      }

	      this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
	      this.head.xRot = headPitch * ((float)Math.PI / 180F);
	      if (flag) {
	         this.head.zRot = 0.3F * MathHelper.sin(0.45F * ageInTicks);
	         this.head.xRot = 0.4F;
	      } else {
	         this.head.zRot = 0.0F;
	      }

	      this.arms.y = 3.0F;
	      this.arms.z = -1.0F;
	      this.arms.xRot = -0.75F;
	      this.leg0.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	      this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	      this.leg0.yRot = 0.0F;
	      this.leg1.yRot = 0.0F;
	   }

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void hatVisible(boolean p_217146_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModelRenderer getHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		// TODO Auto-generated method stub
		return null;
	}

	
}