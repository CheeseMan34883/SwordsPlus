package com.CheeseMan.swordsplus.client.entity.renderer.item;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.client.entity.model.ObiSpearModel;
import com.CheeseMan.swordsplus.client.entity.renderer.item.SPItemSTackRenderer.IItemModelProvider;
import com.CheeseMan.swordsplus.core.init.ItemInit;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ObiSpearRenderer implements SPItemSTackRenderer.IItemModelProvider {

    protected static final ResourceLocation TEXTURE = SwordsPlus.modLoc("items/obi_spear.png");

    @Override
    public void render(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        IVertexBuilder ivertexbuilder = getRenderMaterial(stack).sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, getModel(stack).renderType(getRenderMaterial(stack).atlasLocation()), true, stack.hasFoil()));
        getModel(stack).renderToBuffer(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1f, 1f,1f,1f);
        matrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return TEXTURE;
    }

    @Override
    public Item getRepresentiveItem() {
        return ItemInit.OBI_SPEAR.get();
    }

    @Override
    public RenderMaterial getRenderMaterial(ItemStack stack) {
        return SPItemSTackRenderer.create(TEXTURE);
    }

    @Override
    public EntityModel<?> getModel(ItemStack stack) {
        return new ObiSpearModel();
    }
}
