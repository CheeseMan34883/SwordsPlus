package com.CheeseMan.swordsplus.common.items;

import com.CheeseMan.swordsplus.common.entity.ObiSpearEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpearItem extends TieredItem implements IVanishable {

    private final Multimap<Attribute, AttributeModifier> spearAttributes;

    public SpearItem(IItemTier tier, Properties p_i48459_2_) {
        super(tier, p_i48459_2_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mapBuilder = ImmutableMultimap.builder();
        mapBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 2.0D + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        mapBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9D, AttributeModifier.Operation.ADDITION));
        this.spearAttributes = mapBuilder.build();
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int duration) {
        int useDuration = getUseDuration(stack) - duration;
        if (useDuration >= 10) {
            if (!world.isClientSide()) {
                throwSpear(world, entity, stack);
            }
            if (entity instanceof PlayerEntity)
                ((PlayerEntity) entity).awardStat(Stats.ITEM_USED.get(this));
        }
    }

    private static void throwSpear(World world, LivingEntity thrower, ItemStack stack) {
        stack.hurtAndBreak(1, thrower, e -> e.broadcastBreakEvent(thrower.getUsedItemHand()));
        ObiSpearEntity spear = new ObiSpearEntity(world, thrower, stack);
        spear.shootFromRotation(thrower, thrower.xRot, thrower.yRot, 0.0F, 2.25F, 1.0F);
        // set pickup status and remove the itemstack
        if (thrower instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) thrower;
            if (player.isCreative()) {
                spear.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            } else {
                player.inventory.removeItem(stack);
            }
        }else{
            spear.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
        }
        world.addFreshEntity(spear);
        world.playSound(null, spear, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean canAttackBlock(BlockState p_195938_1_, World p_195938_2_, BlockPos p_195938_3_, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return ActionResult.fail(stack);
        }
        player.startUsingItem(hand);
        return ActionResult.consume(stack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? this.spearAttributes : super.getAttributeModifiers(slot, stack);
    }
}
