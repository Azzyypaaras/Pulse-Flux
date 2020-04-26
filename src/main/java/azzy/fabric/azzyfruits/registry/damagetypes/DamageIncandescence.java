package azzy.fabric.azzyfruits.registry.damagetypes;

import net.minecraft.entity.damage.DamageSource;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class DamageIncandescence extends DamageSource {

    public static final DamageIncandescence INCANDESCENCE = (DamageIncandescence) new DamageIncandescence().setBypassesArmor().setExplosive();

    protected DamageIncandescence() {
        super(MODID+".incandescence");
    }
}
