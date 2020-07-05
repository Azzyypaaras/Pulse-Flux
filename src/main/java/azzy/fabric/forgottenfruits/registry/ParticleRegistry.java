package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.ForgottenFruits;
import azzy.fabric.forgottenfruits.render.particle.CauldronParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {

    public static DefaultParticleType CAULDRON_BUBBLES;

    public static void init() {
        CAULDRON_BUBBLES = register("cauldron_bubbles", true);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.CAULDRON_BUBBLES, CauldronParticle.Factory::new);
    }

    public static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(ForgottenFruits.MOD_ID, name), FabricParticleTypes.simple(alwaysShow));
    }
}
