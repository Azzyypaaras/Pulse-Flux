package azzy.fabric.forgottenfruits.mixin;

import azzy.fabric.forgottenfruits.util.effect.FFStatusEffect;
import azzy.fabric.forgottenfruits.util.mixin.EffectGetter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MilkBucketItem.class)
public class BadMilkiesMixin {

    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    public boolean finishUsing(LivingEntity livingEntity) {
        if (!livingEntity.world.isClient) {
            boolean bl = false;
            for (StatusEffectInstance instance : livingEntity.getActiveStatusEffects().values()) {
                bl = true;
                if (!(instance.getEffectType() instanceof FFStatusEffect)) {
                    ((EffectGetter) livingEntity).removeStatusEffect(instance);
                }
            }
            return bl;
        }
        return false;
    }
}
