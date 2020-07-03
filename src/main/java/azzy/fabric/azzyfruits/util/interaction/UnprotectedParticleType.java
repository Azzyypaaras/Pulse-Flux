package azzy.fabric.azzyfruits.util.interaction;

import net.minecraft.particle.DefaultParticleType;

public class UnprotectedParticleType extends DefaultParticleType {
    private UnprotectedParticleType(boolean bl) {
        super(bl);
    }

    public static DefaultParticleType createParticleType(boolean alwayShow){
        return (DefaultParticleType) new UnprotectedParticleType(alwayShow);
    }
}
