package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Battery extends Item{

	public Battery() {
		super(new Item.Properties()
			.tab(SwordsPlusItemGroup.SWORDSPLUS)
			.food(new Food.Builder().nutrition(0).saturationMod(1.0f)
			.effect(new EffectInstance(Effects.HARM, 100, 5), 1f)
			.effect(new EffectInstance(Effects.WITHER, 2000, 10), 1f)
			.alwaysEat()
			.build()
			));
	}
	@Override
	public int getBurnTime(ItemStack itemStack, IRecipeType<?> recipeType) {
		return 2000;
	}
	

}
