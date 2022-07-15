package com.CheeseMan.swordsplus.common.entity.goals;

import com.CheeseMan.swordsplus.common.entity.WizardEntity;
import com.CheeseMan.swordsplus.core.init.BlockInit;
import com.CheeseMan.swordsplus.core.init.StructureInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Comparator;

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
            }else{
                vector3d = this.findNearestTower();
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

    protected Vector3d findNearestTower(){
        if (!wizard.level.isClientSide()) {
            BlockPos nearestStructure = ((ServerWorld)(wizard.level)).findNearestMapFeature(StructureInit.WIZARD_TOWER.get(), new BlockPos(wizard.position()), 100, true);
            if (nearestStructure != null){
                //TODO change the cauldron to ur custom block
                BlockPos wizard_pedestal = BlockPos.betweenClosedStream(new AxisAlignedBB(nearestStructure).inflate(50)).filter(pos -> wizard.level.getBlockState(pos).getBlock() == BlockInit.WIZARD_PEDESTAL.get()).min(Comparator.comparing(pos -> pos.distSqr(nearestStructure))).orElse(BlockPos.ZERO);
                wizard.setWizardTarget(wizard_pedestal);
                return convert(wizard_pedestal);
            }
        }
        return Vector3d.ZERO;
    }

    public static Vector3d convert(BlockPos pos){
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        Vector3d pos = super.getPosition();
        while (pos == null || pos.distanceToSqr(convert(wizard.getWizardTarget())) <Math.pow(RADIUS, 2)){
            pos = super.getPosition();
        }
        return pos;
    }
}
