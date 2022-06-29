package com.CheeseMan.swordsplus.core.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

	public CustomEnergyStorage(int capacity) {this(capacity, capacity, capacity, 0);}

	public CustomEnergyStorage(int capacity, int maxTransfer)
	{
		this(capacity, maxTransfer, maxTransfer, 0);
	}

	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		this(capacity, maxReceive, maxExtract, 0);
	}

	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy){
		super(capacity, maxReceive, maxExtract, energy);
	}

	/**
	 * just to save the data to the nbt to recover it when u leave the world
	 */
	public CompoundNBT save(CompoundNBT nbt){
		nbt.putInt("energy", this.energy);
		nbt.putInt("maxExtract", this.maxExtract);
		nbt.putInt("maxReceive", this.maxReceive);
		nbt.putInt("capacity", this.capacity);
		return nbt;
	}

	/**
	 * loads the storage from the nbt after u joined the world
	 */
	public void load(CompoundNBT nbt){
		this.energy = nbt.getInt("energy");
		this.maxExtract = nbt.getInt("maxExtract");
		this.maxReceive = nbt.getInt("maxReceive");
		this.capacity = nbt.getInt("capacity");
	}

}
