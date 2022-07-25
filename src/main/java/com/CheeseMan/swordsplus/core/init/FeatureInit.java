package com.CheeseMan.swordsplus.core.init;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FeatureInit {


	public static void addOres(final BiomeLoadingEvent event) {
		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.BRASS_ORE.get().defaultBlockState(), 4,
				8, 25, 6);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.CARBON_STEEL_ORE.get().defaultBlockState(), 2, 1, 19, 3);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.ALUMINUM_ORE.get().defaultBlockState(),
				7, 20, 60, 8);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.BRONZE_ORE.get().defaultBlockState(), 8,
				2, 45, 7);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.TIN_ORE.get().defaultBlockState(), 9,
				25, 67, 9);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.COPPER_ORE.get().defaultBlockState(),
				10, 30, 60, 8);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.TITANIUM_ORE.get().defaultBlockState(),
				4, 6, 27, 4);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.VANADIUM_ORE.get().defaultBlockState(),
				2, 120, 180, 5);

	}

	public static void addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize, int min,
			int max, int amount) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.configured(new OreFeatureConfig(rule, state, veinSize))
						.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(min, 0, max))).squared()
						.count(amount));
	}
}
