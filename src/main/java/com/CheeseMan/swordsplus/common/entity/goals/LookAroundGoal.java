package com.CheeseMan.swordsplus.common.entity.goals;

import java.util.EnumSet;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class LookAroundGoal extends Goal{
	private final WizardEntity wizard;
	
	public LookAroundGoal(WizardEntity wizard) {
		this.wizard = wizard;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return true;
	}
	
     public void tick() {
        if (this.wizard.getTarget() == null) {
           Vector3d vector3d = this.wizard.getDeltaMovement();
           this.wizard.yRot = -((float)MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI);
           this.wizard.yBodyRot = this.wizard.yRot;
        } else {
           LivingEntity livingentity = this.wizard.getTarget();
           double d0 = 64.0D;
           if (livingentity.distanceToSqr(this.wizard) < 4096.0D) {
              double d1 = livingentity.getX() - this.wizard.getX();
              double d2 = livingentity.getZ() - this.wizard.getZ();
              this.wizard.yRot = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
              this.wizard.yBodyRot = this.wizard.yRot;
           }
        }

     }
}
