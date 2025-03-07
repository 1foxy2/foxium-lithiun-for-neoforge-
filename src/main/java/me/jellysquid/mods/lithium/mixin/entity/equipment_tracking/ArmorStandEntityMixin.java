package me.jellysquid.mods.lithium.mixin.entity.equipment_tracking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.jellysquid.mods.lithium.common.entity.EquipmentEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorStand.class)
public abstract class ArmorStandEntityMixin extends Entity implements EquipmentEntity {
    public ArmorStandEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @WrapOperation(
            method = {"readAdditionalSaveData", "brokenByAnything"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;"),
            require = 4
    )
    private <E> E trackEquipChange(NonNullList<E> list, int index, E element, Operation<E> original) {
        E prevElement = original.call(list, index, element);
        this.trackEquipChange(prevElement, element);
        return prevElement;
    }

    @Unique
    private <E> void trackEquipChange(E prevElement, E element) {
        if ((!this.level().isClientSide()) && element instanceof ItemStack newStack && prevElement instanceof ItemStack prevStack) {
            this.lithium$onEquipmentReplaced(prevStack, newStack);
        }
    }
}
