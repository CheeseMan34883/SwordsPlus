package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.common.entity.SunSpearEntity;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SunSpear extends Item implements IVanishable{

	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public SunSpear() {
		super(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS).stacksTo(1).durability(190));
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.0D, AttributeModifier.Operation.ADDITION));
	    builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-2.2F, AttributeModifier.Operation.ADDITION));
	    this.defaultModifiers = builder.build();

	}
	
	
	
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
	      return !playerIn.isCreative(); 
	}
	
	public UseAction getUseAnimation(ItemStack stack) {
	      return UseAction.SPEAR;
	}
	
	public int getUseDuration(ItemStack stack) {
	      return 72000;
	}
	
//	 public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityIn, int p_77615_4_) {
//	      if (entityIn instanceof PlayerEntity) {
//	         PlayerEntity playerentity = (PlayerEntity)entityIn;
//	         int i = this.getUseDuration(stack) - p_77615_4_;
//	         if (i >= 10 ) {
//	        	 if (!worldIn.isClientSide ) {
//	        	 SunSpearEntity SunSpearEntity = new SunSpearEntity(worldIn, playerentity, stack);
//	        	 SunSpearEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 2.5F + 0.5F, 1.0F);
//	        	 if (playerentity.abilities.instabuild) {
//	        		 SunSpearEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//	        	 }
//	        	 worldIn.addFreshEntity(SunSpearEntity);
//	        	 worldIn.playSound((PlayerEntity)null, SunSpearEntity, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
//	        	 
//	        	 if (!playerentity.abilities.instabuild) {
//                     playerentity.inventory.removeItem(stack);
//                  }
//	         }
//	      }
//	    }
//	 }
	
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity p_77615_3_, int p_77615_4_) {
	      if (p_77615_3_ instanceof PlayerEntity) {
	         PlayerEntity playerentity = (PlayerEntity)p_77615_3_;
	         int i = this.getUseDuration(stack) - p_77615_4_;
	         if (i >= 10) {
	            int j = EnchantmentHelper.getRiptide(stack);
	            if (j <= 0 || playerentity.isInWaterOrRain()) {
	               if (!worldIn.isClientSide) {
	                  stack.hurtAndBreak(1, playerentity, (p_220047_1_) -> {
	                     p_220047_1_.broadcastBreakEvent(p_77615_3_.getUsedItemHand());
	                  });
	                  if (j == 0) {
	                     SunSpearEntity sunspearentity = new SunSpearEntity(worldIn, playerentity, stack);
	                     sunspearentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
	                     if (playerentity.abilities.instabuild) {
	                    	 sunspearentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
	                     }

	                     worldIn.addFreshEntity(sunspearentity);
	                     worldIn.playSound((PlayerEntity)null, sunspearentity, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
	                     if (!playerentity.abilities.instabuild) {
	                        playerentity.inventory.removeItem(stack);
	                     }
	                  }
	               }

	               playerentity.awardStat(Stats.ITEM_USED.get(this));
	               if (j > 0) {
	                  float f7 = playerentity.yRot;
	                  float f = playerentity.xRot;
	                  float f1 = -MathHelper.sin(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
	                  float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F));
	                  float f3 = MathHelper.cos(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
	                  float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
	                  float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
	                  f1 = f1 * (f5 / f4);
	                  f2 = f2 * (f5 / f4);
	                  f3 = f3 * (f5 / f4);
	                  playerentity.push((double)f1, (double)f2, (double)f3);
	                  playerentity.startAutoSpinAttack(20);
	                  if (playerentity.isOnGround()) {
	                     float f6 = 1.1999999F;
	                     playerentity.move(MoverType.SELF, new Vector3d(0.0D, (double)1.1999999F, 0.0D));
	                  }

	                  SoundEvent soundevent;
	                  if (j >= 3) {
	                     soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
	                  } else if (j == 2) {
	                     soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
	                  } else {
	                     soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
	                  }

	                  worldIn.playSound((PlayerEntity)null, playerentity, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
	               }

	            }
	         }
	      }
	   }
	 
	 public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		 ItemStack itemstack = playerIn.getItemInHand(handIn);
		 if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
			 return ActionResult.fail(itemstack);
		 }
		 else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !playerIn.isInWaterOrRain()) {
	         return ActionResult.fail(itemstack);
		 }    
		 else {
	         playerIn.startUsingItem(handIn);
	         return ActionResult.consume(itemstack);
	      }
	 }
	 
	 public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
	     target.addEffect(new EffectInstance(Effects.BLINDNESS, 200, 2)); 
		 return super.hurtEnemy(stack, target, attacker);
	 }
	 
	 public boolean mineBlock(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
	      if ((double)p_179218_3_.getDestroySpeed(p_179218_2_, p_179218_4_) != 0.0D) {
	         p_179218_1_.hurtAndBreak(2, p_179218_5_, (p_220046_0_) -> {
	            p_220046_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	         });
	      }

	      return true;
	 }
	 
	 public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
	      return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_111205_1_);
	 
	 }
	 
	 public int getEnchantmentValue() {
		 return 3;
	 }
}
