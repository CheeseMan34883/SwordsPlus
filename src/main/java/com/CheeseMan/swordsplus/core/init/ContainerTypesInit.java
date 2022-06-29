package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.container.ExcimerLaserContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, SwordsPlus.MOD_ID);

	public static final RegistryObject<ContainerType<ExcimerLaserContainer>> EXCIMER_LASER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("excimer_laser", () -> IForgeContainerType.create(ExcimerLaserContainer::new));
}
