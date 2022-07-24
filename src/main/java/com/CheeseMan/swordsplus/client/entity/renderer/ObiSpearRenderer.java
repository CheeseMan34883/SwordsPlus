package com.CheeseMan.swordsplus.client.entity.renderer;

import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ObiSpearRenderer extends EntityRenderer<ObiSpearEntity> {
	
	public static final ResourceLocation OBI_SPEAR_LOCATION = new ResourceLocation("textures/entity/obi_spear/obi_spear.png");
	
	public ObiSpearRenderer(EntityRendererManager manager) {
		super(manager);
	
	}
	
	@Override
	public ResourceLocation getTextureLocation(ObiSpearEntity p_110775_1_) {
		return OBI_SPEAR_LOCATION;
	}
	
	
		

}
