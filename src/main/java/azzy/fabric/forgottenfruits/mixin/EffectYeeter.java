package azzy.fabric.forgottenfruits.mixin;

import azzy.fabric.forgottenfruits.util.mixin.EffectGetter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public class EffectYeeter implements EffectGetter {

    @Shadow
    protected void onStatusEffectRemoved(StatusEffectInstance effect) { }

    @Override
    public void removeStatusEffect(StatusEffectInstance effect) {
        onStatusEffectRemoved(effect);
    }
}
