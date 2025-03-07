package me.jellysquid.mods.lithium.mixin.world.explosions.cache_exposure;

import me.jellysquid.mods.lithium.common.world.ExplosionCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Optimizations for Explosions: Remove duplicate {@link Explosion#getSeenPercent(Vec3, Entity)} calls.
 * @author Crosby
 */
@Mixin(Explosion.class)
public abstract class ExplosionMixin implements ExplosionCache {
    @Unique private float cachedExposure;
    @Unique private Entity cachedEntity;

    @Override
    public void lithium_fabric$cacheExposure(Entity entity, float exposure) {
        this.cachedExposure = exposure;
        this.cachedEntity = entity;
    }

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Explosion;getSeenPercent(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/Entity;)F"))
    private float returnCachedExposure(Vec3 source, Entity entity) {
        return this.cachedEntity == entity ? this.cachedExposure : Explosion.getSeenPercent(source, entity);
    }
}
