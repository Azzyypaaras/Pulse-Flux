package azzy.fabric.azzyfruits.mixin;

import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ParticleManager.class)
public interface ParticleFactoryMixin {

    @Invoker
    public <T extends ParticleEffect> void callRegisterFactory(ParticleType<T> type, ParticleFactory<T> factory);

}
