package com.CheeseMan.swordsplus.core.util;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.core.init.StructureInit;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {

	public static StructureFeature<?, ?> CONFIGURED_WIZARD_TOWER = StructureInit.WIZARD_TOWER.get()
			.configured(IFeatureConfig.NONE);
	
	public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(SwordsPlus.MOD_ID, "configured_wizard_tower"), CONFIGURED_WIZARD_TOWER);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureInit.WIZARD_TOWER.get(), CONFIGURED_WIZARD_TOWER);
    }

}
