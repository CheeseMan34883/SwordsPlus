package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;

import com.CheeseMan.swordsplus.core.util.CustomEnergyStorage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Battery extends Item{

	public Battery() {
		super(new Item.Properties()
			.tab(SwordsPlusItemGroup.SWORDSPLUS)
			.food(new Food.Builder().nutrition(0).saturationMod(1.0f)
			.effect(() -> new EffectInstance(Effects.HARM, 100, 5), 1f)
			.effect(() -> new EffectInstance(Effects.WITHER, 2000, 10), 1f)
			.alwaysEat()
			.build()
			));
	}
	@Override
	public int getBurnTime(ItemStack itemStack, IRecipeType<?> recipeType) {
		return 2000;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
		IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
		if(storage != null){
			 text.add(new TranslationTextComponent("tooltip." + SwordsPlus.MOD_ID + ".battery.energy", storage.getEnergyStored(), storage.getMaxEnergyStored()));
		}
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new CapabilityProvider(stack, 1000);
	}

	public static class CapabilityProvider implements ICapabilitySerializable<CompoundNBT>{

		private ItemStack stack;
		private CustomEnergyStorage storage;

		public CapabilityProvider(ItemStack stack, int initialCapacity){
			this.stack = stack;
			//now it can store 10000RF, can max transfer and receive 100RF/tick and has a custom initialEnergy
			this.storage = new CustomEnergyStorage(10000, 100, 100, initialCapacity);
		}

		//returns the cap like in a normal te
		@Nonnull
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
			if(cap == CapabilityEnergy.ENERGY){
				return LazyOptional.of(() -> storage).cast();
			}
			return null;
		}

		@Override
		public CompoundNBT serializeNBT() {
			CompoundNBT nbt = new CompoundNBT();
			nbt.put("energy", this.storage.save(new CompoundNBT()));
			return nbt;
		}

		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			this.storage.load(nbt.getCompound("energy"));
		}

		public ItemStack getStack() {
			return stack;
		}
	}

}
