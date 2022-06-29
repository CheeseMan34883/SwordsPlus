package com.CheeseMan.swordsplus.core.util;

import com.CheeseMan.swordsplus.SwordsPlus;
import com.CheeseMan.swordsplus.common.items.Trumpet;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
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
		System.out.println(event.getSound().getLocation().equals(ModSoundEvents.FANFARE.get().getLocation()));
		
	}
}
