package com.CheeseMan.swordsplus.core.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

	private final Item item;
	
	public CustomEnergyStorage(Item item, int capacity) {
		super(capacity);
		this.item = item;
	
	}

	public CustomEnergyStorage(Item item, int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
		this.item = item;
	
	}

	public CustomEnergyStorage(Item item, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
		this.item = item;
	
	}

	public CustomEnergyStorage(Item item, int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
		this.item = item;
	
	}
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		
		return super.extractEnergy(maxExtract, simulate);
	}
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		
		return super.receiveEnergy(maxReceive, simulate);
	}
	public void setEnergy(int energy) {
		this.energy = Math.max(0, Math.min(energy, this.capacity));
	}

}
