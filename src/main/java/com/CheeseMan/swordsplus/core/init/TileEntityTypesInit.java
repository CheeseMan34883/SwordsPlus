package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.te.ExcimerLaserTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, SwordsPlus.MOD_ID);

	public static final RegistryObject<TileEntityType<ExcimerLaserTileEntity>> EXCIMER_LASER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("excimer_laser", () -> TileEntityType.Builder.of(ExcimerLaserTileEntity::new, BlockInit.EXCIMER_LASER.get()).build(null));

}
