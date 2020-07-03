package azzy.fabric.azzyfruits.mixin;

import azzy.fabric.azzyfruits.registry.ParticleRegistry;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class ParticleRegistryMixin {


    private Class STOPHIDINGYOULITTLESHIT() throws ClassNotFoundException {
        Class aaa = Class.forName("net.minecraft.client.particle.ParticleManager.SpriteAwareFactory");
        return aaa;
    }

    @Shadow protected abstract <T extends ParticleEffect> void registerFactory(ParticleType<T> type, ParticleFactory<T> factory);

    @Inject(method =  "registerDefaultFactories", at = @At("HEAD"))
    private void registerDefaultFactories(CallbackInfo ci){
        
        Class jank = null;

        try {
            jank = STOPHIDINGYOULITTLESHIT();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.registerFactory(ParticleRegistry.CAULDRON_BUBBLES, jank.cast(azzy.fabric.azzyfruits.render.particle.CauldronParticle::new));
    }
}
