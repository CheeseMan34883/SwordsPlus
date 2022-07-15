package com.CheeseMan.swordsplus.common.entity;

import javax.annotation.Nullable;

import com.CheeseMan.swordsplus.common.items.ObiSpear;
import com.CheeseMan.swordsplus.core.init.EntityTypesInit;
import com.CheeseMan.swordsplus.core.init.ItemInit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ObiSpearEntity extends AbstractArrowEntity{
	
	private static final DataParameter<Byte> ID_LOYALTY = EntityDataManager.defineId(ObiSpearEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(ObiSpearEntity.class, DataSerializers.BOOLEAN);
	private ItemStack obiSpearItem = new ItemStack(ItemInit.OBI_SPEAR.get());
	private boolean dealtDamage;
	public int clientSideReturnTridentTickCount;
	
	public ObiSpearEntity(EntityType<? extends AbstractArrowEntity> p_i50148_1_, World worldIn) {
		super(p_i50148_1_, worldIn);
	}
	
	public ObiSpearEntity(World worldIn, LivingEntity entityIn, ItemStack stack) {
		super(EntityTypesInit.OBI_SPEAR.get(), entityIn, worldIn);
		this.obiSpearItem = stack.copy();
		this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(stack));
	    this.entityData.set(ID_FOIL, stack.hasFoil());
	}
	
	@OnlyIn(Dist.CLIENT)
	public ObiSpearEntity(World worldIn, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
		super(EntityTypesInit.OBI_SPEAR.get(), p_i48791_2_, p_i48791_4_, p_i48791_6_, worldIn);
		
	}
	
	protected void defineSynchedData() {
	    super.defineSynchedData();
	    this.entityData.define(ID_LOYALTY, (byte)0);
	    this.entityData.define(ID_FOIL, false);
	}
	
	public void tick() {
	      if (this.inGroundTime > 4) {
	         this.dealtDamage = true;
	      }
	      Entity ent = this.getOwner();	
	      if ((this.dealtDamage || this.isNoPhysics()) && ent != null) {
	          int i = this.entityData.get(ID_LOYALTY);
	          if (i > 0 && !this.isAcceptibleReturnOwner()) {
	             if (!this.level.isClientSide && this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
	                this.spawnAtLocation(this.getPickupItem(), 0.1F);
	             }

	             this.remove();
	          }
	      }
		super.tick();
	}
	
	private boolean isAcceptibleReturnOwner() {
	      Entity entity = this.getOwner();
	      if (entity != null && entity.isAlive()) {
	         return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
	      } else {
	         return false;
	      }
	}
	
	public ItemStack getPickupItem() {
	      return this.obiSpearItem.copy();
	   }

	   @OnlyIn(Dist.CLIENT)
	   public boolean isFoil() {
	      return this.entityData.get(ID_FOIL);
	   }

	   @Nullable
	   protected EntityRayTraceResult findHitEntity(Vector3d p_213866_1_, Vector3d p_213866_2_) {
	      return this.dealtDamage ? null : super.findHitEntity(p_213866_1_, p_213866_2_);
	   }
	   
	   protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		      Entity entity = p_213868_1_.getEntity();
		      float f = 8.0F;
		      if (entity instanceof LivingEntity) {
		         LivingEntity livingentity = (LivingEntity)entity;
		         f += EnchantmentHelper.getDamageBonus(this.obiSpearItem, livingentity.getMobType());
		      }

		      Entity entity1 = this.getOwner();
		      DamageSource damagesource = DamageSource.trident(this, (Entity)(entity1 == null ? this : entity1));
		      this.dealtDamage = true;
		      SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
		      if (entity.hurt(damagesource, f)) {
		         if (entity.getType() == EntityType.ENDERMAN) {
		            return;
		         }

		         if (entity instanceof LivingEntity) {
		            LivingEntity livingentity1 = (LivingEntity)entity;
		            if (entity1 instanceof LivingEntity) {
		               EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
		               EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
		            }

		            this.doPostHurtEffects(livingentity1);
		         }
		      }
		      this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));  

	   }
	   
	   protected SoundEvent getDefaultHitGroundSoundEvent() {
		      return SoundEvents.TRIDENT_HIT_GROUND;
	   }
	   
	   public void playerTouch(PlayerEntity p_70100_1_) {
		      Entity entity = this.getOwner();
		      if (entity == null || entity.getUUID() == p_70100_1_.getUUID()) {
		         super.playerTouch(p_70100_1_);
		      }
	   }
	   
	   public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		      super.readAdditionalSaveData(p_70037_1_);
		      if (p_70037_1_.contains("Obi Spear", 10)) {
		         this.obiSpearItem = ItemStack.of(p_70037_1_.getCompound("Obi Spear"));
		      }

		      this.dealtDamage = p_70037_1_.getBoolean("DealtDamage");
		      this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.obiSpearItem));
	   }
	   
	   public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		      super.addAdditionalSaveData(p_213281_1_);
		      p_213281_1_.put("Obi Spear", this.obiSpearItem.save(new CompoundNBT()));
		      p_213281_1_.putBoolean("DealtDamage", this.dealtDamage);
	   }
	   
	   public void tickDespawn() {
		      int i = this.entityData.get(ID_LOYALTY);
		      if (this.pickup != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
		         super.tickDespawn();
		      }

	   }
	   
	   protected float getWaterInertia() {
		      return 0.99F;
	   }

	   @OnlyIn(Dist.CLIENT)
	   public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
		      return true;
	   }
}

