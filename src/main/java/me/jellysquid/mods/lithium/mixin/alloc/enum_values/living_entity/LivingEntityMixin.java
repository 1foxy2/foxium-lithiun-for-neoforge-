package me.jellysquid.mods.lithium.mixin.alloc.enum_values.living_entity;

import me.jellysquid.mods.lithium.common.util.EquipmentSlotConstants;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Redirect(
            method = "collectEquipmentChanges()Ljava/util/Map;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/EquipmentSlot;values()[Lnet/minecraft/world/entity/EquipmentSlot;"
            )
    )
    private EquipmentSlot[] removeAllocation() {
        return EquipmentSlotConstants.ALL;
    }
}
