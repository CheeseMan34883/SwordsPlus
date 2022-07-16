package com.CheeseMan.swordsplus.core.util;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.entity.WizardEntity;
import com.CheeseMan.swordsplus.core.init.EntityTypesInit;
import com.CheeseMan.swordsplus.core.init.SpawnEggItemInit;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SwordsPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
	
	@SubscribeEvent
	public static void addEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityTypesInit.WIZARD.get(), WizardEntity.setAttributes().build());
		
	}
	
	@SubscribeEvent
	public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
		SpawnEggItemInit.initSpawnEggs();
	}
	
	
	
}
