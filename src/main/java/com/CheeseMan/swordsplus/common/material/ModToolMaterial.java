package com.CheeseMan.swordsplus.common.material;

import java.util.function.Supplier;

import com.CheeseMan.swordsplus.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModToolMaterial implements IItemTier {
	
	BRONZE_TOOL(2, 350, 5f, 1.5f, 18, () -> Ingredient.of(ItemInit.BRONZE_INGOT.get())),
	TIN_TOOL(2, 203, 7f, 2.5f, 15, () -> Ingredient.of(ItemInit.TIN_INGOT.get())),
	CARBON_STEEL_TOOL(3, 1700, 9f, 4.8f, 7, () -> Ingredient.of(ItemInit.CARBON_STEEL_INGOT.get())),
	BRASS_TOOL(2, 704, 6.5f, 3.9f, 13, () -> Ingredient.of(ItemInit.BRASS_INGOT.get())),
	COPPER_TOOL(2, 176, 4.5f, 2.5f, 20, () -> Ingredient.of(ItemInit.COPPER_INGOT.get())),
	RAPID_TOOL(3, 307, 8f, 5.25f, 10, () -> Ingredient.of(ItemInit.QCARBON.get())),
	TITANIUM_TOOL(3, 1208, 4.5f, 5.75f, 13, () -> Ingredient.of(ItemInit.TITANIUM_INGOT.get())),
	ALUMINUM_TOOL(2, 118, 6.0F, 3.0f, 16, () -> Ingredient.of(ItemInit.ALUMINUM_INGOT.get())),
	VANADIUM_TOOL(3, 1098, 5.0f, 6.0f, 15, () -> Ingredient.of(ItemInit.VANADIUM_INGOT.get()));
	
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Ingredient repairMaterial;

	ModToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			Supplier<Ingredient> repairMaterial) {
			this.harvestLevel = harvestLevel;
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = attackDamage;
			this.enchantability = enchantability;
			this.repairMaterial = repairMaterial.get();
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial;
	}

}
