package com.CheeseMan.swordsplus.client.renderer;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class SPItemSTackRenderer extends ItemStackTileEntityRenderer {

    private static final SPItemSTackRenderer INSTANCE = new SPItemSTackRenderer();

    public static SPItemSTackRenderer getInstance() {
        return INSTANCE;
    }

    public static final Set<IItemModelProvider> MODLES = Sets.newHashSet(new IgnitedSpearRenderer());

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        MODLES.stream().filter(provider -> provider.getRepresentiveItem() == stack.getItem()).forEach(p -> p.render(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay));
    }

    public interface IItemModelProvider{

        void render(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay);

        /**
         *
         * @return a resourceLocation to a texture without textures/
         */
        ResourceLocation getTextureLocation();
        /**
         *
         * @return the item which should be rendered with this renderer
         */
        Item getRepresentiveItem();

        RenderMaterial getRenderMaterial(ItemStack stack);

        /**
         *
         * @return the model which should be rendered
         */
        EntityModel<?> getModel(ItemStack stack);
    }

    public static RenderMaterial create(ResourceLocation texture){
        return new RenderMaterial(PlayerContainer.BLOCK_ATLAS, texture);
    }


}
