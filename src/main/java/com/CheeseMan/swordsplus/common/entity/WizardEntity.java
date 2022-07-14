package com.CheeseMan.swordsplus.common.entity;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.CheeseMan.swordsplus.core.init.ItemInit;
import com.google.common.collect.ImmutableMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtCustomerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.entity.ai.goal.UseItemGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WizardEntity extends AbstractVillagerEntity {

	
	
	public static final Int2ObjectMap<VillagerTrades.ITrade[]> WIZARD_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[] {
			new WizardEntity.ItemsForValuablesTrade(ItemInit.RAPID_SWORD.get(), 4, 1, 3, 6, 0.2f),
			new WizardEntity.ItemsForValuablesTrade(ItemInit.OBSIDIAN_TIP.get(), 0, 0, 0, 0, 0.1f)
	}));
	
	@Nullable
	private BlockPos target;
	
	
	public WizardEntity(EntityType<? extends AbstractVillagerEntity> p_i50185_1_, World p_i50185_2_) {
		super(p_i50185_1_, p_i50185_2_);
		this.forcedLoading = true;
	}

	@Override
	protected void rewardTradeXp(MerchantOffer p_213713_1_) {
		if (p_213713_1_.shouldRewardExp()) {
			int i = 3 + this.random.nextInt(4);
			this.level.addFreshEntity(
					new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
		}
	}

	private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> map) {
	      return new Int2ObjectOpenHashMap<>(map);
	}
	
	@Override
	protected void updateTrades() {
		VillagerTrades.ITrade[] trades = WizardEntity.WIZARD_TRADES.get(1);
		VillagerTrades.ITrade[] trades1 = WizardEntity.WIZARD_TRADES.get(2);
		if (trades != null && trades1 != null) {
			MerchantOffers offers = this.getOffers();
			this.addOffersFromItemListings(offers, trades, 1);
			int i = this.random.nextInt(trades1.length);
			VillagerTrades.ITrade trades2 = trades1[i];
			MerchantOffer offer = trades2.getOffer(this, this.random);
			if (offer != null) {
				offers.add(offer);
			}
			
		}
	}
	
	@Override
	protected ActionResultType mobInteract(PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (stack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.isTrading() && !this.isBaby()) {
			if (handIn == Hand.MAIN_HAND) {
				playerIn.awardStat(Stats.TALKED_TO_VILLAGER);
			}
			if (this.getOffers().isEmpty()) {
	            return ActionResultType.sidedSuccess(this.level.isClientSide);
	         }
			else {
	            if (!this.level.isClientSide) {
	               this.setTradingPlayer(playerIn);
	               this.openTradingScreen(playerIn, this.getDisplayName(), 1);
	            }

	            return ActionResultType.sidedSuccess(this.level.isClientSide);
	         }
		}
		else {
			return super.mobInteract(playerIn, handIn);
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		if (this.target != null) {
			nbt.put("WizardTarget", NBTUtil.writeBlockPos(this.target));
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		if (nbt.contains("WizardTarget")) {
	         this.target = NBTUtil.readBlockPos(nbt.getCompound("WizardTarget"));
	      }

	      this.setAge(Math.max(0, this.getAge()));
	}
	
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}
	
	

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(0, new UseItemGoal<>(this, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE), SoundEvents.GENERIC_DRINK, (wizard) -> {
	         return wizard.isOnFire();
	      }));
		this.goalSelector.addGoal(0, new UseItemGoal<>(this, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.REGENERATION), SoundEvents.GENERIC_DRINK, (wizard) -> {
	         return (wizard.getHealth() < wizard.getMaxHealth());
	      }));
		this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new LookAtCustomerGoal(this));
		this.goalSelector.addGoal(2, new MoveToGoal(this, 2.0D, 0.35D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
		this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
	    this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.35D));
	    this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
	    this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
		
		this.goalSelector.addGoal(10, new );
		
	}
	
	public void setWizardTarget(@Nullable BlockPos p_213726_1_) {
	      this.target = p_213726_1_;
	   }

	@Nullable
	public BlockPos getWizardTarget() {
	      return this.target;
	}


	static class MoveToGoal extends Goal{
		final WizardEntity wizard;
		final double stopDistance;
		final double speedModifier;
		
		 MoveToGoal(WizardEntity entity, double stopDistance, double speedModifier) {
			this.wizard = entity;
			this.stopDistance = stopDistance;
	        this.speedModifier = speedModifier;
	        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	        
		}
		 
		public void stop() {
			this.wizard.setWizardTarget((BlockPos)null);
		}
		
		@Override
		public boolean canUse() {
			BlockPos blockpos = this.wizard.getWizardTarget();
			return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
		}
		
		public void tick() {
			BlockPos pos = this.wizard.getWizardTarget();
			if (pos != null && wizard.getNavigation().isDone()) {
				 if (this.isTooFarAway(pos, 10.0D)) {
		               Vector3d vector3d = (new Vector3d((double)pos.getX() - this.wizard.getX(), (double)pos.getY() - this.wizard.getY(), (double)pos.getZ() - this.wizard.getZ())).normalize();
		               Vector3d vector3d1 = vector3d.scale(10.0D).add(this.wizard.getX(), this.wizard.getY(), this.wizard.getZ());
		               wizard.getNavigation().moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
				 }
				 else {
					 wizard.getNavigation().moveTo((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), this.speedModifier);
				 }
			}
		}
		
		private boolean isTooFarAway(BlockPos pos, double p_220846_2_) {
	        return !pos.closerThan(this.wizard.position(), p_220846_2_);
	     }
		
	}
	
	static class StayCloseToTower extends WaterAvoidingRandomWalkingGoal {

		public StayCloseToTower(CreatureEntity entity, double p_i47301_2_) {
			super(entity, p_i47301_2_);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected Vector3d getPosition() {
			
			super.getPosition();
			
		}
		
	}
	
//	static class IntramuralForValuables {
//		
//	}
	static class ValuablesForItems implements VillagerTrades.ITrade{
		private final Item item;
	    private final int cost;
	    private final int maxUses;
	    private final int wizardXp;
	    private final float priceMultiplier;
	
	    public ValuablesForItems(IItemProvider p_i50539_1_, int p_i50539_2_, int p_i50539_3_, int p_i50539_4_) {
	         this.item = p_i50539_1_.asItem();
	         this.cost = p_i50539_2_;
	         this.maxUses = p_i50539_3_;
	         this.wizardXp = p_i50539_4_;
	         this.priceMultiplier = 0.05F;
	      }
	    
	    @Override
		public MerchantOffer getOffer(Entity entityIn, Random rand) {
	    	ItemStack itemstack = new ItemStack(this.item, this.cost);
	        return new MerchantOffer(itemstack, (new ItemStack(Items.NETHERITE_INGOT)), this.maxUses, this.wizardXp, this.priceMultiplier);
		}
	    
		
	}
	static class ItemsForValuablesTrade implements VillagerTrades.ITrade {
	      private final ItemStack itemStack;
	      private final int valuablesCost;
	      private final int numberOfItems;
	      private final int maxUses;
	      private final int wizardXp;
	      private final float priceMultiplier;

	      public ItemsForValuablesTrade(Block p_i50528_1_, int p_i50528_2_, int p_i50528_3_, int p_i50528_4_, int p_i50528_5_) {
	         this(new ItemStack(p_i50528_1_), p_i50528_2_, p_i50528_3_, p_i50528_4_, p_i50528_5_);
	      }

	      public ItemsForValuablesTrade(Item p_i50529_1_, int p_i50529_2_, int p_i50529_3_, int p_i50529_4_) {
	         this(new ItemStack(p_i50529_1_), p_i50529_2_, p_i50529_3_, 12, p_i50529_4_);
	      }

	      public ItemsForValuablesTrade(Item p_i50530_1_, int p_i50530_2_, int p_i50530_3_, int p_i50530_4_, int p_i50530_5_, float f) {
	         this(new ItemStack(p_i50530_1_), p_i50530_2_, p_i50530_3_, p_i50530_4_, p_i50530_5_, f);
	      }

	      public ItemsForValuablesTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
	         this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
	      }

	      public ItemsForValuablesTrade(ItemStack stack, int cost, int numberOfItems, int uses, int xp, float pM) {
	         this.itemStack = stack;
	         this.valuablesCost = cost;
	         this.numberOfItems = numberOfItems;
	         this.maxUses = uses;
	         this.wizardXp = xp;
	         this.priceMultiplier = pM;
	      }

	      

		public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
	         return new MerchantOffer(new ItemStack(Items.NETHERITE_INGOT, this.valuablesCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.wizardXp, this.priceMultiplier);
	      }
	   }

}

