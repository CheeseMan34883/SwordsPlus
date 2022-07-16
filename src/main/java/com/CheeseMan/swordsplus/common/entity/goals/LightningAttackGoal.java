package com.CheeseMan.swordsplus.common.entity.goals;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LightningAttackGoal extends Goal{
	WizardEntity wizard;
	LivingEntity entity = this.wizard.getTarget();
	private int chargeTime;
	
	public LightningAttackGoal(WizardEntity wizard) {
		this.wizard = wizard;
	}

	@Override
	public boolean canUse() {
		
		
		return this.entity != null;
	}	
	@Override
	public void start() {
		chargeTime = 0;
	}
	
	@Override
	public void stop() {
		this.wizard.setCharging(false);
	}
	@Override
	public void tick() {
		if (this.entity.distanceToSqr(this.wizard) < 4096.0D && this.wizard.canSee(entity)) {
			World worldIn = this.wizard.level;
			this.chargeTime++;
		}
		if (this.chargeTime == 15 && this.wizard.level.isNight()) {
			BlockPos pos = this.entity.blockPosition();
			if (this.wizard.level.canSeeSky(pos)) {
				 LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.wizard.level);
				 lightningboltentity.moveTo(Vector3d.atBottomCenterOf(pos));
		         this.wizard.level.addFreshEntity(lightningboltentity);
		         this.chargeTime = -20;
			}
		}
		else if(this.chargeTime > 0) {
			this.chargeTime--;
		}
		this.wizard.setCharging(this.chargeTime > 10);
	}
}
