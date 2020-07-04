package azzy.fabric.forgottenfruits.registry.damagetypes;

import net.minecraft.entity.damage.DamageSource;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;

public class DamageIncandescence extends DamageSource {

    public static final DamageIncandescence INCANDESCENCE = (DamageIncandescence) new DamageIncandescence().setBypassesArmor().setExplosive();

    protected DamageIncandescence() {
        super(MOD_ID +".incandescence");
    }
}
