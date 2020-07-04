package azzy.fabric.forgottenfruits.mixin;

import azzy.fabric.forgottenfruits.registry.ParticleRegistry;
import azzy.fabric.forgottenfruits.render.particle.CauldronParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class ParticleFactoryMixin {
    @Invoker
    abstract <T extends ParticleEffect> void callRegisterFactory(ParticleType<T> type, ParticleFactory<T> factory);

    @Inject(method = "registerDefaultFactories", at = @At("HEAD"))
    private void registerDefaultFactories(CallbackInfo callbackInfo) {
        callRegisterFactory(ParticleRegistry.CAULDRON_BUBBLES, new CauldronParticle.Factory());
    }
}
