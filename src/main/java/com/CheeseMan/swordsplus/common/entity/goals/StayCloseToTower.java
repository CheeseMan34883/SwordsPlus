package com.CheeseMan.swordsplus.common.entity.goals;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;
import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.StructureInit;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class StayCloseToTower extends WaterAvoidingRandomWalkingGoal {
    //maybe make a config for it
    public static final int RADIUS = 70;

    protected final WizardEntity wizard;

    public StayCloseToTower(WizardEntity entity, double speedModifier) {
        super(entity, speedModifier);
        this.wizard = entity;
    }

    public StayCloseToTower(WizardEntity entity, double speedModifier, float probability) {
        super(entity, speedModifier, probability);
        this.wizard = entity;
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle()) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(this.interval) != 0) {
                    return false;
                }
            }
            Vector3d vector3d;
            if (wizard.hasFoundTower()) {
                vector3d = this.getPosition();
            } else {
                vector3d = this.findNearestTower();
                System.out.println(vector3d);
            }
            if (vector3d == null) {
                return false;
            } else {
                this.wantedX = vector3d.x;
                this.wantedY = vector3d.y;
                this.wantedZ = vector3d.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    protected Vector3d findNearestTower() {
        if (!wizard.level.isClientSide()) {
            BlockPos nearestStructure = ((ServerWorld) (wizard.level)).findNearestMapFeature(StructureInit.WIZARD_TOWER.get(), wizard.blockPosition(), 100, false);
            System.out.println(nearestStructure);
            if (nearestStructure != null) {
                
                BlockPos nearestStructure2 = nearestStructure.offset(0, 50, 0);
                BlockPos wizard_pedestal = getWizardPedestal(nearestStructure2, 100);
                wizard.setWizardTarget(wizard_pedestal);
                return convert(wizard_pedestal);
            }
        }
        return Vector3d.ZERO;
    }

    private BlockPos getWizardPedestal(BlockPos pos, int range) {
        for (int y = -50; y < 50; y++) {
            for (int x = -range; x < range; x++) {
                for (int z = -range; z < range; z++) {
                    if (wizard.level.getBlockState(pos.offset(x, y, z)).getBlock() == BlockInit.WIZARD_PEDESTAL.get()){
                        return pos.offset(x, y, z);
                    }
                }
            }
        }
        return BlockPos.ZERO;
    }

    public static Vector3d convert(BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        if (!this.wizard.level.isDay() || this.mob.getRandom().nextDouble() < 0.1) {
            System.out.println("run home");
            return convert(this.wizard.getWizardTarget());
        }
        Vector3d pos = super.getPosition();
        int searchSteps = 0;
        while (pos == null || pos.distanceToSqr(convert(wizard.getWizardTarget())) > Math.pow(RADIUS, 2)) {
            pos = super.getPosition();
            if (searchSteps > 1000) {
                System.out.println("nothing in range");
                return convert(wizard.getWizardTarget());
            }
            searchSteps++;
        }
        return pos;
    }
}
