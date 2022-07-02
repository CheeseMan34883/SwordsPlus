package com.CheeseMan.swordsplus.common.container;

import java.util.Objects;

import com.CheeseMan.swordsplus.common.te.ExcimerLaserTileEntity;
import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExcimerLaserContainer extends Container {

	public final ExcimerLaserTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;
	private IIntArray array = getIIntArray();

	public ExcimerLaserContainer(final int windowId, final PlayerInventory playerInv, final ExcimerLaserTileEntity te) {
		super(ContainerTypesInit.EXCIMER_LASER_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());

		// Tile Entity
		this.addSlot(new BatterySlot(te, 0, 11, 29));
		this.addSlot(new Slot(te, 1, 31, 11));
		this.addSlot(new Slot(te, 2, 31, 47));
		this.addSlot(new Slot(te, 3, 144, 29) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		// Main Player Inventory
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 168 - (4 - row) * 18 - 10));
			}
		}

		// Player Hotbar
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot(playerInv, col, 8 + col * 18, 144));
		}
		this.addDataSlots(this.array);
	}

	public ExcimerLaserContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}

	private static ExcimerLaserTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
		Objects.requireNonNull(data, "Packet Buffer cannot be null.");
		final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if (te instanceof ExcimerLaserTileEntity) {
			return (ExcimerLaserTileEntity) te;
		}
		throw new IllegalStateException("Tile Entity is not correct");

	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlockInit.EXCIMER_LASER.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			if (index < ExcimerLaserTileEntity.slots
					&& !this.moveItemStackTo(stack1, ExcimerLaserTileEntity.slots, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (!this.moveItemStackTo(stack1, 0, ExcimerLaserTileEntity.slots, false)) {
				return ItemStack.EMPTY;
			}

			if (stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}

	public static class BatterySlot extends Slot {

		public BatterySlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return ExcimerLaserTileEntity.isBattery(stack);
		}

	}

	public double getCounterPercentage() {
		double counter = this.array.get(0);
		if (counter == 0)
			return 0d;
		double maxCounter = ExcimerLaserTileEntity.WORKING_TIME;
		return 1d - (counter / maxCounter);
	}

	public double getFuelCounterPercentage() {
		double fuelCounter = this.array.get(1);
		double maxFuelCounter = this.array.get(2);
		return fuelCounter / maxFuelCounter;
	}

	public boolean isPowered() {
		return te.getLevel().getBlockState(te.getBlockPos()).getValue(BlockStateProperties.POWERED);
	}

	private IIntArray getIIntArray() {
		return new IIntArray() {

			@Override
			public void set(int index, int value) {
				switch (index) {
				case 0:
					te.setCounter(value);
					break;
				case 1:
					te.setFuelCounter(value);
					break;
				case 2:
					te.setMaxFuelCounter(value);
					break;
				default:
					break;
				}

			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 3;
			}

			@Override
			public int get(int index) {
				switch (index) {
				case 0:
					return te.getCounter();
				case 1:
					return te.getFuelCounter();
				case 2:
					return te.getMaxFuelCounter();
				default:
					return 0;
				}

			}

		};
	}

}
