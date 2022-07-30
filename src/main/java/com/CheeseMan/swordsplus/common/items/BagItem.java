package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BagItem extends Item {

	public BagItem() {
		super(new Item.Properties()
			.tab(SwordsPlusItemGroup.SWORDSPLUS)
			.stacksTo(1)	
			);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		return super.use(worldIn, playerIn, handIn);
	}
	

}
