package com.CheeseMan.swordsplus.common.entity;

import com.CheeseMan.swordsplus.core.init.EntityTypesInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ObiSpearEntity extends AbstractArrowEntity {

    protected static final DataParameter<ItemStack> ITEM = EntityDataManager.defineId(ObiSpearEntity.class, DataSerializers.ITEM_STACK);
    private int loyaltyLevel = 0, returningTicks = 0;
    private boolean dealtDamage = false;

    public ObiSpearEntity(EntityType<ObiSpearEntity> type, World p_i48546_2_) {
        super(type, p_i48546_2_);
    }

    public ObiSpearEntity(World p_i48546_2_,LivingEntity thrower, ItemStack item) {
        super(EntityTypesInit.OBI_SPEAR.get(),thrower, p_i48546_2_);
        this.entityData.set(ITEM, item);
        this.loyaltyLevel = EnchantmentHelper.getLoyalty(item);
    }

    @Override
    public void tick() {
        if(this.inGroundTime > 4){
            this.dealtDamage = true;
        }
        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (loyaltyLevel > 0 && !this.shouldReturnToThrower()) {
                if (!this.level.isClientSide() && this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove();
            } else if (loyaltyLevel > 0) {
                this.setNoPhysics(true);
                Vector3d vector3d = new Vector3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * (double)loyaltyLevel, this.getZ());
                if (this.level.isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double)loyaltyLevel;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        super.tick();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            f += EnchantmentHelper.getDamageBonus(this.getPickupItem(), livingentity.getMobType());
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = DamageSource.trident(this, entity1 == null ? this : entity1);
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        this.playSound(soundevent, f1, 1.0F);
    }

    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d p_213866_1_, Vector3d p_213866_2_) {
        return this.dealtDamage ? null : super.findHitEntity(p_213866_1_, p_213866_2_);
    }

    private boolean shouldReturnToThrower() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("item", getPickupItem().save(new CompoundNBT()));
        nbt.putInt("loyalty", this.loyaltyLevel);
        nbt.putInt("returningTicks", this.returningTicks);
        nbt.putBoolean("dealtDamage", this.dealtDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(ITEM, ItemStack.of(nbt.getCompound("item")));
        this.loyaltyLevel = nbt.getInt("loyalty");
        this.dealtDamage = nbt.getBoolean("dealtDamage");
        this.returningTicks = nbt.getInt("returningTicks");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEM, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getPickupItem() {
        return this.entityData.get(ITEM);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }
}
