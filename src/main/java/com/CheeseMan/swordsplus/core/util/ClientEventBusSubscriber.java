package com.CheeseMan.swordsplus.core.util;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.client.entity.renderer.WizardRenderer;
import com.CheeseMan.swordsplus.client.screen.ExcimerLaserScreen;
import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.ContainerTypesInit;
import com.CheeseMan.swordsplus.core.init.EntityTypesInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SwordsPlus.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.register(ContainerTypesInit.EXCIMER_LASER_CONTAINER_TYPE.get(), ExcimerLaserScreen::new);
		
		
		RenderTypeLookup.setRenderLayer(BlockInit.WIZARD_PEDESTAL.get(), RenderType.cutout());
		RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.WIZARD.get(), WizardRenderer::new);
		
	}
}
