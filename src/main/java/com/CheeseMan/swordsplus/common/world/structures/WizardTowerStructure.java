package com.CheeseMan.swordsplus.common.world.structures;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WizardTowerStructure extends Structure<NoFeatureConfig> {

	public WizardTowerStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		// TODO Auto-generated method stub
		return WizardTowerStructure.Start::new;
	}

	// Super Important
	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList
			.of(new MobSpawnInfo.Spawners(EntityType.HOGLIN, 100, 1, 2));

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
		return STRUCTURE_CREATURES;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed,
			SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos,
			NoFeatureConfig featureConfig) {
		
		BlockPos centerOfChunk = new BlockPos((chunkX <<4) + 7, 0, (chunkZ << 4) + 7);
		int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(),
				Heightmap.Type.WORLD_SURFACE_WG);
		
		
		IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

		
		return topBlock.getFluidState().isEmpty(); 
	}

	/**
	 * Handles calling up the structure's pieces class and height that structure
	 * will spawn at.
	 */
	public static class Start extends StructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ,
				MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator,
				TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

			// Turns the chunk coordinates into actual coordinates we can use
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;

			BlockPos centerPos = new BlockPos(x, 0, z);

			
			
			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							.get(new ResourceLocation(SwordsPlus.MOD_ID, "wizard_tower/start_pool")), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, centerPos, this.pieces, this.random,
					false, true); 
			
			
			this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);

            this.calculateBoundingBox();

            LogManager.getLogger().log(Level.DEBUG, "House at " +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0);
			}

			
		

	}

}
