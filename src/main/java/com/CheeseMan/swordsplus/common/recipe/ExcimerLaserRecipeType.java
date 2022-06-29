package com.CheeseMan.swordsplus.common.recipe;

import com.CheeseMan.swordsplus.SwordsPlus;

import net.minecraft.item.crafting.IRecipeType;

public class ExcimerLaserRecipeType implements IRecipeType<ExcimerLaserRecipe>{
	@Override
	public String toString() {
		return SwordsPlus.MOD_ID + ":excimer_laser_recipe";
	}
}
