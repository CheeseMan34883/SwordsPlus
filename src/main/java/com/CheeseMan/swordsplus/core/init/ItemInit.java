package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.client.renderer.SPItemSTackRenderer;
import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.CheeseMan.swordsplus.common.items.Battery;
import com.CheeseMan.swordsplus.common.items.SpearItem;
import com.CheeseMan.swordsplus.common.items.Trumpet;
import com.CheeseMan.swordsplus.common.material.ModToolMaterial;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			SwordsPlus.MOD_ID);

	// Items

	public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register("brass_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> CARBON_STEEL_INGOT = ITEMS.register("carbon_steel_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> FOIL = ITEMS.register("foil",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> QCARBON = ITEMS.register("qcarbon",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> IRON_HANDLE = ITEMS.register("iron_handle",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> VANADIUM_INGOT = ITEMS.register("vanadium_ingot",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> RAW_VANADIUM = ITEMS.register("raw_vanadium",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Battery> BATTERY = ITEMS.register("battery", Battery::new);

	public static final RegistryObject<Item> OBSIDIAN_TIP = ITEMS.register("obsidian_tip",
			() -> new Item(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	 public static final RegistryObject<SpawnEggItemInit> WIZARD_SPAWN_EGG = ITEMS.register("wizard_spawn_egg",
	            () -> new SpawnEggItemInit(EntityTypesInit.WIZARD, 0x991f46, 0x174042,
	                    new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));
	 

	// Swords
	public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
			() -> new SwordItem(ModToolMaterial.BRONZE_TOOL, 4, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
			() -> new SwordItem(ModToolMaterial.COPPER_TOOL, 2, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword",
			() -> new SwordItem(ModToolMaterial.TIN_TOOL, 3, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> CARBON_STEEL_SWORD = ITEMS.register("carbon_steel_sword",
			() -> new SwordItem(ModToolMaterial.CARBON_STEEL_TOOL, 3, -2.55f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> TRUMPET = ITEMS.register("trumpet",
			() -> new Trumpet(ModToolMaterial.BRASS_TOOL, 2, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> RAPID_SWORD = ITEMS.register("rapid_sword",
			() -> new SwordItem(ModToolMaterial.RAPID_TOOL, 0, 3.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> TITANIUM_SWORD = ITEMS.register("titanium_sword",
			() -> new SwordItem(ModToolMaterial.TITANIUM_TOOL, 0, -2f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> ALUMINUM_PRACTICE_SWORD = ITEMS.register("aluminum_practice_sword",
			() -> new SwordItem(ModToolMaterial.ALUMINUM_TOOL, 1, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	public static final RegistryObject<Item> VANADIUM_SWORD = ITEMS.register("vanadium_sword",
			() -> new SwordItem(ModToolMaterial.VANADIUM_TOOL, 1, -2.4f,
					new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS)));

	// Spears
	public static final RegistryObject<SpearItem> IGNITED_SPEAR = ITEMS.register("ignited_spear", () -> new SpearItem(ModToolMaterial.OBI_SPEAR, new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS).setISTER(() -> SPItemSTackRenderer::getInstance)));
	
}
