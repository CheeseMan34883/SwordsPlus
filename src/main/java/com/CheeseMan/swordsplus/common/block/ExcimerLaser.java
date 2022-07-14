package com.CheeseMan.swordsplus.common.block;


import javax.naming.Context;

import com.CheeseMan.swordsplus.common.te.ExcimerLaserTileEntity;
import com.CheeseMan.swordsplus.core.init.TileEntityTypesInit;
import com.google.common.util.concurrent.Service.State;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExcimerLaser extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	
	public ExcimerLaser() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(10f, 25f).harvestLevel(2)
				.sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.POWERED, false));
		

	}
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.EXCIMER_LASER_TILE_ENTITY_TYPE.get().create();
	}
	
	
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos,
			PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!player.isCrouching() && !worldIn.isClientSide() && worldIn != null) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof ExcimerLaserTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (ExcimerLaserTileEntity) te, pos); 
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.SUCCESS;
	}
	
	
	
	@Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, BlockStateProperties.POWERED);
    }
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer,
			ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if(stack.hasCustomHoverName()) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof ExcimerLaserTileEntity) {
				((ExcimerLaserTileEntity) te).setCustomName(stack.getDisplayName());
			}
		}
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}
	@Override
	public int getAnalogOutputSignal(BlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
		return Container.getRedstoneSignalFromBlockEntity(p_180641_2_.getBlockEntity(p_180641_3_));
	}
	
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState oldState,
			boolean isMoving) {
		// code that drops items upon breaking TE
		if(!state.is(oldState.getBlock())) {
            TileEntity entity = worldIn.getBlockEntity(pos);
            if(entity instanceof ExcimerLaserTileEntity) {
                ExcimerLaserTileEntity tileEntity = (ExcimerLaserTileEntity) entity;
                InventoryHelper.dropContents(worldIn, pos, tileEntity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            
        }
		//makes sure the TE drops on breaking
		super.onRemove(state, worldIn, pos, oldState, isMoving);
		
	}

}
