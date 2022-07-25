package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class VillagerInit {

	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister
			.create(ForgeRegistries.POI_TYPES, SwordsPlus.MOD_ID);

	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
			.create(ForgeRegistries.PROFESSIONS, SwordsPlus.MOD_ID);

//	public static final RegistryObject<PointOfInterestType> WIZARD_POI = POI_TYPES.register("wizard_poi",
//			() -> new PointOfInterestType("wizard_poi",
//					PointOfInterestType.getBlockStates(BlockInit.WIZARD_PEDESTAL.get()), 1, 1));
//
//	public static final RegistryObject<VillagerProfession> WIZARD = VILLAGER_PROFESSIONS.register("wizard",
//			() -> new VillagerProfession(
//					"wizard", WIZARD_POI.get(), ImmutableSet.of(ItemInit.CARBON_STEEL_INGOT.get(),
//							ItemInit.ALUMINUM_INGOT.get(), ItemInit.VANADIUM_INGOT.get()),
//					ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC));

//	public static void registerPOIs() {
//		try {
//			ObfuscationReflectionHelper
//					.findMethod(PointOfInterestType.class, "registerBlockStates", PointOfInterestType.class)
//					.invoke(null, WIZARD_POI.get());
//		} catch (InvocationTargetException | IllegalAccessException exception) {
//			exception.printStackTrace();
//		}
//	}

	public static void register(IEventBus bus) {
		POI_TYPES.register(bus);
		VILLAGER_PROFESSIONS.register(bus);
	}
}
