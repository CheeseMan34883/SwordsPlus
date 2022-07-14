package com.CheeseMan.swordsplus.core.init;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.CheeseMan.swordsplus.common.entity.SunSpearEntity;
import com.CheeseMan.swordsplus.common.entity.WizardEntity;

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

	public static final RegistryObject<EntityType<SunSpearEntity>> SUN_SPEAR = ENTITY_TYPES.register("sun_spear",
			() -> EntityType.Builder.<SunSpearEntity>of(SunSpearEntity::new, EntityClassification.MISC)
					.sized(0.5f, 0.5f).build(new ResourceLocation(SwordsPlus.MOD_ID, "sun_spear").toString()));

	public static final RegistryObject<EntityType<WizardEntity>> WIZARD = ENTITY_TYPES.register("wizard",
			() -> EntityType.Builder.<WizardEntity>of(WizardEntity::new, EntityClassification.CREATURE)
					.sized(0.6f, 1.95f).clientTrackingRange(10)
					.build(new ResourceLocation(SwordsPlus.MOD_ID, "wizard").toString()));

}
