package com.CheeseMan.swordsplus.core.itemgroup;

import com.CheeseMan.swordsplus.core.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SwordsPlusItemGroup extends ItemGroup{

	public static final SwordsPlusItemGroup SWORDSPLUS = new SwordsPlusItemGroup(ItemGroup.TABS.length, "swordsplus");
	
	public SwordsPlusItemGroup(int index, String label) {
		super(index, label);
		
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.TIN_INGOT.get());
	}
	
}
