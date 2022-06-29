package com.CheeseMan.swordsplus.common.te;

import javax.annotation.Nullable;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.block.ExcimerLaser;
import com.CheeseMan.swordsplus.common.container.ExcimerLaserContainer;
import com.CheeseMan.swordsplus.common.recipe.ExcimerLaserRecipe;
import com.CheeseMan.swordsplus.core.init.ItemInit;
import com.CheeseMan.swordsplus.core.init.RecipeInit;
import com.CheeseMan.swordsplus.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

public class ExcimerLaserTileEntity extends LockableLootTileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	public static int slots = 4, WORKING_TIME = 60;
	
	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);
	
	private int fuelCounter;
	private int maxFuelCounter = 0;
	private int counter;

	private ITextComponent customName;

	public ExcimerLaserTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		
	}
	public ExcimerLaserTileEntity() {
		this(TileEntityTypesInit.EXCIMER_LASER_TILE_ENTITY_TYPE.get());
		counter = 0;
		fuelCounter = -1;
	}
	
	public int getFuelCounter() {
		return fuelCounter;
	}

	public void setFuelCounter(int fuelCounter) {
		this.fuelCounter = fuelCounter;
	}

	public int getMaxFuelCounter() {
		return maxFuelCounter;
	}

	public void setMaxFuelCounter(int maxFuelCounter) {
		this.maxFuelCounter = maxFuelCounter;
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void setCustomName(ITextComponent name) {
		this.customName = name;
	}
	
	
	public ITextComponent getName() {
		return this.customName != null ? this.customName : this.getDefaultName();
	}
	
	@Override
	public int getContainerSize() {
		return slots;
	}
	
	@Override
	public ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + SwordsPlus.MOD_ID + ".excimer_laser");
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.getName();
	}
	
	@Nullable
	public ITextComponent getCustomName() {
		return this.customName;
	}
	
	public static boolean isBattery(ItemStack stack) {
		if (stack.getItem() == ItemInit.BATTERY.get()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInv) {
		return new ExcimerLaserContainer(id, playerInv, this);
	}
	@Override
	public NonNullList<ItemStack> getItems() {
		return this.items;
	}
	@Override
	public void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}
	@Override
	public void tick() {
		if(level.isClientSide()) {
            return;
		}
		BlockState state = level.getBlockState(getBlockPos());
        if (state.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(getBlockPos(), state.setValue(BlockStateProperties.POWERED, counter > 0),
                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);

        }
        if(fuelCounter > 0)
            fuelCounter--;
        if(getItem(0).isEmpty() || getItem(1).isEmpty() || getItem(2).isEmpty()){
            reset();
            return;
           }
        // this checks whether there is a matching recipe, and then checks whether fuel
        // is available and the checks whether the outputSlot is occupied
        ExcimerLaserRecipe recipe = getRecipe();
        if(recipe == null || !canStartWorking(recipe)){
          reset();
           return;
        }
        if(counter <= 0){
            //ill create this method in a second
            startWorking(recipe);
        }
        if(counter > 0){
          counter--;
          if(counter == 0){
            //ill create the method in a second
            finishWork(recipe);
          }
        }
}
	
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		compound.putInt("counter", counter);
        compound.putInt("fuelCounter", fuelCounter); 
        compound.putInt("maxfuelCounter", maxFuelCounter);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.items);
		}
		return compound;
	}
	@Override 
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.counter = nbt.getInt("counter");
		this.maxFuelCounter = nbt.getInt("maxfuelCounter");
		this.fuelCounter = nbt.getInt("fuelCounter");
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.trySaveLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}
	private boolean canStartWorking(ExcimerLaserRecipe recipe){
	    //when the output slot is Empty u can smelt of course
	    if(getItem(3).isEmpty())
	      return true;
	    //when in the there is an item in the output slot that matches the current output u can also start working
	    if(getItem(3).getItem() == recipe.assemble(this).getItem())
	      return true;
	  //default is u can't start working
	  return false;
	}
	private void reset() {
		this.counter = 0;
		
	}
	/**
	*executed when a recipe is starting to be processed
	*/
	private void startWorking(ExcimerLaserRecipe recipe){
	    counter = WORKING_TIME;
	}
	/**
	* executed when the recipe is finished, use to shring the input and set the output
	*/
	private void finishWork(ExcimerLaserRecipe recipe){
	    for(int i =1;i<= 2;i++){
	        getItem(i).shrink(1);
	    }
	    if(getItem(3).isEmpty())
	      setItem(3, recipe.assemble(this).copy());
	    else if(getItem(3).getItem() == recipe.assemble(this).getItem())
	        getItem(3).grow(1);
	}
//	private boolean checkFuel(){
//        if(fuelCounter >= 0){
//            fuelCounter--;
//            return true;
//        }    
//        
//        else if(!getItem(0).isEmpty()){
//                    fuelCounter = ForgeHooks.getBurnTime(getItem(0));
//                    maxFuelCounter = fuelCounter;
//                    getItem(0).shrink(1);
//                     return true;
//        }
//        return false;    
//    }

	@Nullable
	private ExcimerLaserRecipe getRecipe() {
		return this.level.getRecipeManager().getRecipeFor(RecipeInit.EXCIMER_LASER_RECIPE, this, this.level)
				.orElse(null);
	
	}
	
	
}
