package com.CheeseMan.swordsplus.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ObiSpearModel extends EntityModel<Entity> {
    private final ModelRenderer Feather;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer bb_main;

    public ObiSpearModel() {
        texWidth = 44;
        texHeight = 32;

        Feather = new ModelRenderer(this);
        Feather.setPos(-3.0F, 1.5F, -6.75F);
        setRotationAngle(Feather, -2.6445F, 0.445F, -2.2415F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-4.3085F, 0.8644F, -5.8013F);
        Feather.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.2729F, 0.286F, 0.7813F);
        cube_r1.texOffs(12, -11).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-4.3085F, 0.8644F, -5.8013F);
        Feather.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.3927F, 0.0F);
        cube_r2.texOffs(12, -8).addBox(0.0F, -3.0F, -2.0F, 0.0F, 3.0F, 11.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 0.0F, 0.0F);
        bb_main.texOffs(31, 0).addBox(-2.0F, 2.0F, -14.0F, 4.0F, 0.0F, 5.0F, 0.0F, false);
        bb_main.texOffs(34, 1).addBox(0.0F, 0.0F, -14.0F, 0.0F, 4.0F, 5.0F, 0.0F, true);
        bb_main.texOffs(32, 10).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(4, 15).addBox(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 15.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
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
}
