package com.mrbysco.weightedflight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mrbysco.weightedflight.WeightedFlightMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@WrapOperation(
			method = "travel",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;",
					ordinal = 2
			)
	)
	private Vec3 weightedflight$elytraDrag(Vec3 travelVector, double factorX, double factorY, double factorZ, Operation<Vec3> original) {
		double drag = WeightedFlightMod.getDrag((LivingEntity) (Object) this);
		return original.call(travelVector, factorX * drag, factorY, factorZ * drag);
	}
}
