package com.mrbysco.weightedflight;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WeightedConfig {

	public static class Common {
		public final ModConfigSpec.DoubleValue armorPieceShare;
		public final ModConfigSpec.DoubleValue toughnessShare;
		public final ModConfigSpec.DoubleValue maxElytraSlowdown;
		public final ModConfigSpec.DoubleValue armorWeightCurve;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Flight settings")
					.push("flight");

			armorPieceShare = builder
					.comment("How much the number of armor pieces matters compared to their armor value (0 = value only, 1 = pieces only)")
					.defineInRange("armorPieceShare", 0.25, 0.0, 1.0);

			toughnessShare = builder
					.comment("How much armor toughness matters compared to armor value, makes Netherite heavier than Diamond")
					.defineInRange("toughnessShare", 0.3, 0.0, 1.0);

			maxElytraSlowdown = builder
					.comment("Maximum slowdown per tick for a fully armored player (0 to disable)")
					.defineInRange("maxElytraSlowdown", 0.15, 0.0, 0.5);

			armorWeightCurve = builder
					.comment("How quickly the slowdown ramps up (1 = linear, higher = light armor barely matters)")
					.defineInRange("armorWeightCurve", 2.0, 0.1, 5.0);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
