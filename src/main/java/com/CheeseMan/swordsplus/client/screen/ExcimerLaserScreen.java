package com.CheeseMan.swordsplus.client.screen;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.container.ExcimerLaserContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExcimerLaserScreen extends ContainerScreen<ExcimerLaserContainer> {

	private static final ResourceLocation EXCIMER_LASER_GUI = new ResourceLocation(SwordsPlus.MOD_ID,
			"textures/gui/excimer_laser_gui.png");

	public ExcimerLaserScreen(ExcimerLaserContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 168;

	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1f, 1f, 1f, 1f);
		this.minecraft.textureManager.bind(EXCIMER_LASER_GUI);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
		if(menu.isPowered()){
			this.blit(matrixStack, x + 38, y + 29, 0, 170, (int)(93d*menu.getCounterPercentage()), 17);
		}
		//what is this?
		/*
		int fuelGaugeHeight = (int)(67*menu.getCounterPercentage());
        if(menu.getFuelCounterPercentage() > 0)
          this.blit(matrixStack, x + 146, y + 12 + 67 - fuelGaugeHeight, 178, 26, 23, fuelGaugeHeight);
		 */
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		this.font.draw(matrixStack, this.inventory.getDisplayName(), (float) this.inventoryLabelX,
				(float) this.inventoryLabelY, 4210752);
	}

}
