package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.block.ExcimerLaser;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			SwordsPlus.MOD_ID);

	// Blocks
	public static final RegistryObject<Block> BRASS_BLOCK = BLOCKS.register("brass_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> CARBON_STEEL_BLOCK = BLOCKS.register("carbon_steel_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 9f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(3).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5f, 9f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(3).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> VANADIUM_BLOCK = BLOCKS.register("vanadium_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(15f, 90f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(4).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));
	
	public static final RegistryObject<Block> EXCIMER_LASER = BLOCKS.register("excimer_laser", () -> new ExcimerLaser());
					

	// Ores
	public static final RegistryObject<Block> CARBON_STEEL_ORE = BLOCKS.register("carbon_steel_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	public static final RegistryObject<Block> BRONZE_ORE = BLOCKS.register("bronze_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> BRASS_ORE = BLOCKS.register("brass_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> ALUMINUM_ORE = BLOCKS.register("aluminum_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

	public static final RegistryObject<Block> VANADIUM_ORE = BLOCKS.register("vanadium_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));
}
