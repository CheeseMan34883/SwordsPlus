package com.CheeseMan.swordsplus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.ContainerTypesInit;
import com.CheeseMan.swordsplus.core.init.EntityTypesInit;
import com.CheeseMan.swordsplus.core.init.FeatureInit;
import com.CheeseMan.swordsplus.core.init.ItemInit;
import com.CheeseMan.swordsplus.core.init.RecipeInit;
import com.CheeseMan.swordsplus.core.init.StructureInit;
import com.CheeseMan.swordsplus.core.init.TileEntityTypesInit;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;
import com.CheeseMan.swordsplus.core.util.ConfiguredStructures;
import com.CheeseMan.swordsplus.core.util.ModSoundEvents;
import com.mojang.serialization.Codec;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
		
		

		EntityTypesInit.ENTITY_TYPES.register(bus);
		BlockInit.BLOCKS.register(bus);
		ItemInit.ITEMS.register(bus);
		TileEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
		ContainerTypesInit.CONTAINER_TYPES.register(bus);
		StructureInit.STRUCTURES.register(bus);
		bus.addListener(this::setup);
		ModSoundEvents.register(bus);
		
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
		forgeBus.addListener(EventPriority.HIGH, FeatureInit::addOres);
		forgeBus.register(this);

	}
	
	/**
     * Here, setupStructures will be ran after registration of all structures are finished.
     * This is important to be done here so that the Deferred Registry has already ran and
     * registered/created our structure for us.
     *
     * Once after that structure instance is made, we then can now do the rest of the setup
     * that requires a structure instance such as setting the structure spacing, creating the
     * configured structure instance, and more.
     */
    public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            StructureInit.setupStructures();
            ConfiguredStructures.registerConfiguredStructures();
        });
    }
    
    /**
     * This is the event you will use to add anything to any biome.
     * This includes spawns, changing the biome's looks, messing with its surfacebuilders,
     * adding carvers, spawning new features... etc
     *
     * Here, we will use this to add our structure to all biomes.
     */
    public void biomeModification(final BiomeLoadingEvent event) {
    	RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if(types.contains(BiomeDictionary.Type.PLAINS)) {
            List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();

            structures.add(() -> StructureInit.WIZARD_TOWER.get().configured(IFeatureConfig.NONE));
        }
    }
    
    private static Method GETCODEC_METHOD;
    
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            //Ugly Terraforged
            try {
                Method GETCODEC_METHOD =
                        ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(
                        (Codec<? extends ChunkGenerator>)GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception e) {
                LogManager.getLogger().error("Was unable to check if " + serverWorld.dimension().location()
                        + " is using Terraforged's ChunkGenerator.");
            }

            //no superflat
            if (serverWorld.getChunkSource().generator instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }
            
            Map<Structure<?>, StructureSeparationSettings> tempMap =
                    new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(StructureInit.WIZARD_TOWER.get(),
                    DimensionStructuresSettings.DEFAULTS.get(StructureInit.WIZARD_TOWER.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
        
    }

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS))
					.setRegistryName(block.getRegistryName()));
		});
	}
}
