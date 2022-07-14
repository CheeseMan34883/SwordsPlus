package com.CheeseMan.swordsplus.core.util;

import java.util.List;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.items.Trumpet;
import com.CheeseMan.swordsplus.core.init.ItemInit;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = SwordsPlus.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ForgeEvents {
	
	
	@SubscribeEvent
	public static void onSoundPlayed(PlaySoundEvent event){
		if (event.getSound().getLocation().equals(ModSoundEvents.FANFARE.get().getLocation())){
			Trumpet.stop(event);
			
		}
		//System.out.println(event.getSound().getLocation().equals(ModSoundEvents.FANFARE.get().getLocation()));
		
	}
	@SubscribeEvent
	public static void addCustomTrades(VillagerTradesEvent event) {
		if (event.getType() == VillagerProfession.WEAPONSMITH) {
			Int2ObjectMap<List<ITrade>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ItemInit.VANADIUM_SWORD.get(), 1);
            int villagerLevel = 1;
            
            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    stack,4,12,0.09F));
			}
		
	}
	
	
}
