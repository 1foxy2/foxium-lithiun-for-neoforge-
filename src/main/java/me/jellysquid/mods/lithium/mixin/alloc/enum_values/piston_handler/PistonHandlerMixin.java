package me.jellysquid.mods.lithium.mixin.alloc.enum_values.piston_handler;

import me.jellysquid.mods.lithium.common.util.DirectionConstants;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonStructureResolver.class)
public class PistonHandlerMixin {

    @Redirect(
            method = "addBranchingBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Direction;values()[Lnet/minecraft/core/Direction;"
            )
    )
    private Direction[] removeAllocation() {
        return DirectionConstants.ALL;
    }
}
