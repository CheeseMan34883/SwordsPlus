package com.CheeseMan.swordsplus.common.entity.goals;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireballAttackGoal extends Goal{

    public static final int LIGHTNING_COOLDOWN = 30;

	private final WizardEntity wizard;
	private int chargeTime;
    private boolean lightning = false;
	
	public FireballAttackGoal(WizardEntity wizard) {
		this.wizard = wizard;
		
	}
	
	@Override
	public boolean canUse() {
		return this.wizard.getTarget() != null;
	}
	
	@Override
	public void start() {
		this.chargeTime = 0;
        lightning = this.wizard.getRandom().nextDouble() < 0.3;
	}
	
	@Override
	public void stop() {
		this.wizard.setCharging(false);
	}
	
	@Override
	public void tick() {
        if(lightning)
            lightningTick();
        else
            fireballTick();
	}

    private void fireballTick(){
        LivingEntity entity = this.wizard.getTarget();
        double d0 = 64.0D;
        if (entity.distanceToSqr(this.wizard) < 4096.0D && this.wizard.canSee(entity)) {
            World world = this.wizard.level;
            ++this.chargeTime;
            if (this.chargeTime == 5 && !this.wizard.isSilent()) {
                //world.levelEvent((PlayerEntity)null, 1015, this.wizard.blockPosition(), 0);
            }
            if (this.chargeTime == 10) {
                double d1 = 4.0D;
                Vector3d vector3d = this.wizard.getViewVector(1.0F);
                double d2 = entity.getX() - (this.wizard.getX() + vector3d.x * 4.0D);
                double d3 = entity.getY(0.5D) - (0.5D + this.wizard.getY(0.5D));
                double d4 = entity.getZ() - (this.wizard.getZ() + vector3d.z * 4.0D);
                if (!this.wizard.isSilent()) {
                    world.levelEvent((PlayerEntity)null, 1016, this.wizard.blockPosition(), 0);
                }

                FireballEntity fireballentity = new FireballEntity(world, this.wizard, d2, d3, d4);
                fireballentity.explosionPower = this.wizard.getExplosionPower();
                fireballentity.setPos(this.wizard.getX() + vector3d.x * 4.0D, this.wizard.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 4.0D);
                world.addFreshEntity(fireballentity);
                this.chargeTime = 0;
            }
        } else if (this.chargeTime > 0) {
            --this.chargeTime;
        }

        this.wizard.setCharging(this.chargeTime > 10);
    }

    public void lightningTick() {
        LivingEntity entity = this.wizard.getTarget();
        double d0 = 64.0D;
        if (entity.distanceToSqr(this.wizard) < 4096.0D && this.wizard.canSee(entity)) {
            World world = this.wizard.level;
            ++this.chargeTime;
            if (this.chargeTime == 5 && !this.wizard.isSilent()) {

            }
            if (this.chargeTime == 10) {
                double d1 = 4.0D;
                Vector3d vector3d = this.wizard.getViewVector(1.0F);
                double d2 = entity.getX() - (this.wizard.getX() + vector3d.x * 4.0D);
                double d3 = entity.getY(0.5D) - (0.5D + this.wizard.getY(0.5D));
                double d4 = entity.getZ() - (this.wizard.getZ() + vector3d.z * 4.0D);
                if (!this.wizard.isSilent()) {
                    world.levelEvent((PlayerEntity)null, 1016, this.wizard.blockPosition(), 0);
                }

                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
                lightningboltentity.setDamage(wizard.getLightningDamage());

                lightningboltentity.setPos(this.wizard.getX() + vector3d.x * 4.0D, this.wizard.getY(0.5D) + 0.5D, lightningboltentity.getZ() + vector3d.z * 4.0D);
                world.addFreshEntity(lightningboltentity);
                this.chargeTime = -20;
            }
        } else if (this.chargeTime > 0) {
            --this.chargeTime;
        }

        this.wizard.setCharging(this.chargeTime > 10);
    }
	
}

