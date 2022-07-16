package com.CheeseMan.swordsplus.common.entity.goals;

import java.util.EnumSet;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;
import com.CheeseMan.swordsplus.core.init.ItemInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class MeleeWizardGoal extends Goal{
	protected WizardEntity wizard;
	private boolean canPenalize = false;
	private int ticksUntilNextPathRecalculation;
	private Path path;
	private double pathedTargetX;
	private double pathedTargetY;
	private double pathedTargetZ;
	private final double speedModifier;
	private final boolean followingTargetEvenIfNotSeen;
	private long lastCanUseCheck;
	private int ticksUntilNextAttack;
	private final int attackInterval = 20;
	private int failedPathFindingPenalty = 0;
	private ItemStack sword = new ItemStack(ItemInit.CARBON_STEEL_SWORD.get());
	
	public MeleeWizardGoal(WizardEntity wizard, boolean follow, double speed) {
		this.wizard = wizard;
		this.speedModifier = speed;
		this.followingTargetEvenIfNotSeen = follow;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	
	
	@Override
	public boolean canUse() {
		long i = this.wizard.level.getGameTime();
	      if (i - this.lastCanUseCheck < 20L) {
	         return false;
	      } else {
	         this.lastCanUseCheck = i;
	         LivingEntity livingentity = this.wizard.getTarget();
	         if (livingentity == null) {
	            return false;
	         } else if (!livingentity.isAlive()) {
	            return false;
	         } else {
	           if (canPenalize) {
	               if (--this.ticksUntilNextPathRecalculation <= 0) {
	                  this.path = this.wizard.getNavigation().createPath(livingentity, 0);
	                  this.ticksUntilNextPathRecalculation = 4 + this.wizard.getRandom().nextInt(7);
	                  return this.path != null;
	               } else {
	                  return true;
	               }
	            }
	            this.path = this.wizard.getNavigation().createPath(livingentity, 0);
	            if (this.path != null) {
	               return true;
	            } else {
	               return this.getAttackReachSqr(livingentity) >= this.wizard.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
	            }
	         }
	      }
	   }
	public boolean canContinueToUse() {
	      LivingEntity livingentity = this.wizard.getTarget();
	      if (livingentity == null) {
	         return false;
	      } else if (!livingentity.isAlive()) {
	         return false;
	      } else if (!this.followingTargetEvenIfNotSeen) {
	         return !this.wizard.getNavigation().isDone();
	      } else if (!this.wizard.isWithinRestriction(livingentity.blockPosition())) {
	         return false;
	      } else {
	         return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
	      }
	   }

	   public void start() {
	      this.wizard.getNavigation().moveTo(this.path, this.speedModifier);
	      this.wizard.setAggressive(true);
	      this.ticksUntilNextPathRecalculation = 0;
	      this.ticksUntilNextAttack = 0;
	   }

	   public void stop() {
	      LivingEntity livingentity = this.wizard.getTarget();
	      if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
	         this.wizard.setTarget((LivingEntity)null);
	      }

	      
	      this.wizard.getNavigation().stop();
	   }
	
	protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
	      double d0 = this.getAttackReachSqr(p_190102_1_);
	      if (p_190102_2_ <= d0 && this.ticksUntilNextAttack <= 0) {
	         this.resetAttackCooldown();
	         this.wizard.setItemInHand(Hand.MAIN_HAND, this.sword.copy());
	         this.wizard.swing(Hand.MAIN_HAND);
	         this.wizard.doHurtTarget(p_190102_1_);
	      }

	   }
	public void tick() {
	      LivingEntity livingentity = this.wizard.getTarget();
	      this.wizard.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
	      double d0 = this.wizard.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
	      this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
	      if ((this.followingTargetEvenIfNotSeen || this.wizard.getSensing().canSee(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.wizard.getRandom().nextFloat() < 0.05F)) {
	         this.pathedTargetX = livingentity.getX();
	         this.pathedTargetY = livingentity.getY();
	         this.pathedTargetZ = livingentity.getZ();
	         this.ticksUntilNextPathRecalculation = 4 + this.wizard.getRandom().nextInt(7);
	         if (this.canPenalize) {
	            this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
	            if (this.wizard.getNavigation().getPath() != null) {
	               net.minecraft.pathfinding.PathPoint finalPathPoint = this.wizard.getNavigation().getPath().getEndNode();
	               if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
	                  failedPathFindingPenalty = 0;
	               else
	                  failedPathFindingPenalty += 10;
	            } else {
	               failedPathFindingPenalty += 10;
	            }
	         }
	         if (d0 > 1024.0D) {
	            this.ticksUntilNextPathRecalculation += 10;
	         } else if (d0 > 256.0D) {
	            this.ticksUntilNextPathRecalculation += 5;
	         }

	         if (!this.wizard.getNavigation().moveTo(livingentity, this.speedModifier)) {
	            this.ticksUntilNextPathRecalculation += 15;
	         }
	      }

	      this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
	      this.checkAndPerformAttack(livingentity, d0);
	   }
	   protected void resetAttackCooldown() {
	      this.ticksUntilNextAttack = 5;
	   }

	   protected boolean isTimeToAttack() {
	      return this.ticksUntilNextAttack <= 0;
	   }

	   protected int getTicksUntilNextAttack() {
	      return this.ticksUntilNextAttack;
	   }

	   protected int getAttackInterval() {
	      return 5;
	   }
	   protected double getAttackReachSqr(LivingEntity p_179512_1_) {
		      return (double)(this.wizard.getBbWidth() * 2.0F * this.wizard.getBbWidth() * 2.0F + p_179512_1_.getBbWidth());
	   }
	   
}
