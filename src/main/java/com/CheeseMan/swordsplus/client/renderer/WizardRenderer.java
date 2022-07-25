package com.CheeseMan.swordsplus.client.renderer;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.client.entity.model.Wizard;
import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WizardRenderer extends MobRenderer<WizardEntity, Wizard<WizardEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(SwordsPlus.MOD_ID,
			"textures/entity/wizard/wizard.png");
	
	

	public WizardRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new Wizard<>(), 0.5F);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResourceLocation getTextureLocation(WizardEntity p_110775_1_) {
		// TODO Auto-generated method stub
		return TEXTURE;
	}

}
