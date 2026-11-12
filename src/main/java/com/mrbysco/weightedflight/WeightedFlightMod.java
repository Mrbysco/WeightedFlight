package com.mrbysco.weightedflight;

import com.mojang.logging.LogUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(WeightedFlightMod.MOD_ID)
public class WeightedFlightMod {
	public static final String MOD_ID = "weightedflight";
	private static final Logger LOGGER = LogUtils.getLogger();

	public WeightedFlightMod(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, WeightedConfig.commonSpec);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	public static double getDrag(LivingEntity livingEntity) {
		if (!(livingEntity instanceof Player)) return 1.0;

		double pieces = Math.round(livingEntity.getArmorCoverPercentage() * 4.0) / 4.0;
		double armor = Math.min(livingEntity.getArmorValue() / 20.0, 1.0);
		double toughness = Math.min(livingEntity.getAttributeValue(Attributes.ARMOR_TOUGHNESS) / 12.0, 1.0);

		double toughnessShare = WeightedConfig.COMMON.toughnessShare.get();
		double pieceShare = WeightedConfig.COMMON.armorPieceShare.get();

		double armorQuality = Mth.lerp(toughnessShare, armor, toughness);
		double weight = Mth.lerp(pieceShare, armorQuality, pieces);

		return 1.0 - Math.pow(weight, WeightedConfig.COMMON.armorWeightCurve.get()) * WeightedConfig.COMMON.maxElytraSlowdown.get();
	}
}
