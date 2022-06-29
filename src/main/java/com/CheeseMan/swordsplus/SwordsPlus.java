package com.CheeseMan.swordsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.ContainerTypesInit;
import com.CheeseMan.swordsplus.core.init.FeatureInit;
import com.CheeseMan.swordsplus.core.init.ItemInit;
import com.CheeseMan.swordsplus.core.init.RecipeInit;
import com.CheeseMan.swordsplus.core.init.TileEntityTypesInit;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;
import com.CheeseMan.swordsplus.core.util.ModSoundEvents;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("swordsplus")
@Mod.EventBusSubscriber(modid = SwordsPlus.MOD_ID, bus = Bus.MOD)

public class SwordsPlus {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "swordsplus";

	public static final ResourceLocation modLoc(String name) {
		return new ResourceLocation(MOD_ID, name);
	}

	public SwordsPlus() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);

		BlockInit.BLOCKS.register(bus);
		ItemInit.ITEMS.register(bus);
		TileEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
		ContainerTypesInit.CONTAINER_TYPES.register(bus);
		ModSoundEvents.register(bus);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS))
					.setRegistryName(block.getRegistryName()));
		});
	}
}
