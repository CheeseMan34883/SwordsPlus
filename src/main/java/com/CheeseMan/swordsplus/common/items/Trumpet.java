package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.core.util.ModSoundEvents;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class Trumpet extends SwordItem{

	public Trumpet(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
		
	}
	
	 public static void stop(PlaySoundEvent event){  
		 event.getManager().stop(event.getSound());
	 }
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
		worldIn.playSound(player, player.blockPosition(), ModSoundEvents.FANFARE.get(), SoundCategory.BLOCKS, 1, 1);
		return super.use(worldIn, player, hand);
	}

}
