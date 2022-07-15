package com.CheeseMan.swordsplus.common.entity.goals;

import javax.annotation.Nullable;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;

public class UseWaterBucketGoal extends Goal{

	private final WizardEntity wizard;
	private final SoundEvent sound;
	private final ItemStack waterbucket = new ItemStack(Items.WATER_BUCKET);
	
	public UseWaterBucketGoal(WizardEntity wizard, @Nullable SoundEvent sound) {
		this.wizard = wizard;
		this.sound = sound;
	}
	
	
	
	@Override
	public boolean canUse() {
		if (this.wizard.isOnFire()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return this.wizard.isUsingItem();
	}
	
	@Override
	public void start() {
		this.wizard.setItemSlot(EquipmentSlotType.MAINHAND, this.waterbucket.copy());
		this.wizard.startUsingItem(Hand.MAIN_HAND);
		
	}
	@Override
	public void stop() {
		this.wizard.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
		if (this.sound != null) {
			this.wizard.playSound(this.sound, 1.0F, this.wizard.getRandom().nextFloat() *0.2F + 0.9F);
		}
	}

	

}
