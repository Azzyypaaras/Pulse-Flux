package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.mixin.ParticleFactoryMixin;
import azzy.fabric.azzyfruits.util.interaction.UnprotectedParticleType;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {

    public static DefaultParticleType CAULDRON_BUBBLES;

    public static void init(){
        CAULDRON_BUBBLES = register("cauldron_bubbles", true);
        FabricParticleTypes.simple();
    }



    //public static <T extends ParticleEffect> void registerFactory(ParticleType<T> type, ParticleFactory<T> factory){
    //    ((ParticleFactoryMixin) CAULDRON_BUBBLES).callRegisterFactory(CAULDRON_BUBBLES, (UnprotectedSpriteAwareFactory.SpriteAwareFactory)(azzy.fabric.azzyfruits.render.particle.CauldronParticle::new));
    //}

    public static DefaultParticleType register(String name, boolean alwaysShow){
        return (DefaultParticleType) Registry.register(Registry.PARTICLE_TYPE, name, UnprotectedParticleType.createParticleType(alwaysShow));
    }
}
