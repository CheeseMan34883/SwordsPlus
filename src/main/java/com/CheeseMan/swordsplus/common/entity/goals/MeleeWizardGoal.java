package com.CheeseMan.swordsplus.common.entity.goals;

import java.util.EnumSet;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;

public class MeleeWizardGoal extends Goal{
	protected WizardEntity wizard;
	private boolean canPenalize = false;
	private int ticksUntilNextPathRecalculation;
	private Path path;
	private double pathedTargetX;
	private double pathedTargetY;
	private double pathedTargetZ;
	
	
	public MeleeWizardGoal(WizardEntity wizard) {
		this.wizard = wizard;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	
	
	@Override
	public boolean canUse() {
		LivingEntity livingentity = wizard.getTarget();
		if (livingentity == null) {
            return false;
        }
		else if (!livingentity.isAlive()) {
            return false;
         } 
         else {
           if (canPenalize) {
               if (--this.ticksUntilNextPathRecalculation <= 0) {
                  this.path = this.wizard.getNavigation().createPath(livingentity, 0);
                  this.ticksUntilNextPathRecalculation = 4 + this.wizard.getRandom().nextInt(7);
                  return this.path != null;
               }
               return false;
           }
         }
		return false;
	}
}
