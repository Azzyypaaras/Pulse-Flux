package azzy.fabric.forgottenfruits.render.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class CauldronParticle extends SpriteBillboardParticle {

    public CauldronParticle(ClientWorld world, double x, double y, double z, double d, double e, double f) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale *= this.random.nextFloat() * 0.4F + 0.2F;
        this.velocityX = d * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.velocityY = e * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.velocityZ = f * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
        } else {
            this.velocityY += 0.002D;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.8500000238418579D;
            this.velocityY *= 0.8500000238418579D;
            this.velocityZ *= 0.8500000238418579D;
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider provider) {
            this.spriteProvider = provider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CauldronParticle particle = new CauldronParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
