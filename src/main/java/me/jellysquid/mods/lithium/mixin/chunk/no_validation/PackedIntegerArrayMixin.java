package me.jellysquid.mods.lithium.mixin.chunk.no_validation;

import net.minecraft.util.SimpleBitStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleBitStorage.class)
public class PackedIntegerArrayMixin {
    @Redirect(
            method = {"getAndSet", "set(II)V", "get(I)I"},
            at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/Validate;inclusiveBetween(JJJ)V", remap = false)

    )
    public void skipValidation(long start, long end, long value) {
    }
}
