package azzy.fabric.forgottenfruits.util.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class FFStatusEffect extends StatusEffect {

    private final boolean instant;
    private final boolean removable;

    public FFStatusEffect(StatusEffectType type, int color, boolean instant, boolean removable) {
        super(type, color);
        this.instant = instant;
        this.removable = removable;
    }

    public boolean isRemovable() {
        return removable;
    }

    @Override
    public boolean isInstant() {
        return instant;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return !instant;
    }
}
