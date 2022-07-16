package com.CheeseMan.swordsplus.common.entity.goals;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LightningAttackGoal extends Goal{
	WizardEntity wizard;
	World worldIn = this.wizard.level;
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
		
		if (this.wizard.canSee(entity)) {
			this.chargeTime++;
		}
		if (this.chargeTime == 15 && worldIn.isNight() && worldIn instanceof ServerWorld) {
			BlockPos pos = this.entity.blockPosition();
			if (worldIn.canSeeSky(pos)) {
				 LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(worldIn);
				 lightningboltentity.moveTo(Vector3d.atBottomCenterOf(pos));
		         worldIn.addFreshEntity(lightningboltentity);
		         this.chargeTime = -15;
			}
		}
		else if(this.chargeTime > 0) {
			this.chargeTime--;
		}
		this.wizard.setCharging(this.chargeTime > 10);
	}
}