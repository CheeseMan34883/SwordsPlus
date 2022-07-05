package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.CheeseMan.swordsplus.common.items.ObiSpear;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SwordsPlus.MOD_ID);

	public static final RegistryObject<EntityType<ObiSpearEntity>> OBI_SPEAR = ENTITY_TYPES.register("obi_spear",
			() -> EntityType.Builder.<ObiSpearEntity>of(ObiSpearEntity::new, EntityClassification.MISC)
					.sized(0.5f, 0.5f).build(new ResourceLocation(SwordsPlus.MOD_ID, "obi_spear").toString()));

}
