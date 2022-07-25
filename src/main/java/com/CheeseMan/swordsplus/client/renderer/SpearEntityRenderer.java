package com.CheeseMan.swordsplus.client.renderer;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class SpearEntityRenderer extends EntityRenderer<ObiSpearEntity> {

    private final ItemRenderer itemRender;
    public SpearEntityRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        this.itemRender = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(ObiSpearEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn) {
        stack.pushPose();
        itemRender.renderStatic(entity.getPickupItem(), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, stack, buffer);
        stack.popPose();
        super.render(entity, entityYaw, partialTicks, stack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ObiSpearEntity p_110775_1_) {
        return SwordsPlus.modLoc("items/ignited_spear");
    }
}
