package azzy.fabric.circumstable.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

public class FoodItems {

    public static FoodComponent FoodBackend(int hungerEffect, float saturationEffect, boolean fast) {
        if (fast)
            return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).snack().build();
        return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).build();
    }

    public static FoodComponent FoodBackendSpecial(int hungerEffect, float saturationEffect, boolean fast, boolean meat, StatusEffect bonusEffect, float chance, int duration) {
        StatusEffectInstance effect = new StatusEffectInstance(bonusEffect, duration);
        if (fast) {
            if (meat)
                return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).snack().meat().statusEffect(effect, chance).build();
            return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).snack().statusEffect(effect, chance).build();
        }
        if (meat)
            return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).meat().statusEffect(effect, chance).build();
        return new FoodComponent.Builder().hunger(hungerEffect).saturationModifier(saturationEffect).statusEffect(effect, chance).build();
    }
}
