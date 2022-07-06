package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.CheeseMan.swordsplus.core.itemgroup.SwordsPlusItemGroup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ObiSpear extends Item implements IVanishable{

	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public ObiSpear() {
		super(new Item.Properties().tab(SwordsPlusItemGroup.SWORDSPLUS).stacksTo(1).durability(1200));
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 10.0D, AttributeModifier.Operation.ADDITION));
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
	      return 7200;
	}
	
	 public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityIn, int p_77615_4_) {
	      if (entityIn instanceof PlayerEntity) {
	         PlayerEntity playerentity = (PlayerEntity)entityIn;
	         int i = this.getUseDuration(stack) - p_77615_4_;
	         if (i >= 10) {
	        	 if (!worldIn.isClientSide) {
		        	 ObiSpearEntity obiSpearEntity = new ObiSpearEntity(worldIn, playerentity, stack);
		        	 obiSpearEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 2.5F + 0.5F, 1.0F);
		        	 if (playerentity.abilities.instabuild) {
		        		 obiSpearEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
		        	 }
		        	 worldIn.addFreshEntity(obiSpearEntity);
		        	 worldIn.playSound((PlayerEntity)null, obiSpearEntity, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
		        	 
		        	 if (!playerentity.abilities.instabuild) {
	                     playerentity.inventory.removeItem(stack);
	                     
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
		 else {
	         playerIn.startUsingItem(handIn);
	         return ActionResult.consume(itemstack);
	      }
	 }
	 
	 public boolean hurtEnemy(ItemStack stack, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
	      stack.hurtAndBreak(1, p_77644_3_, (p_220048_0_) -> {
	         p_220048_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	      });
	      return true;
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
