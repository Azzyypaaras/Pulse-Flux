package azzy.fabric.circumstable.registry;

import azzy.fabric.circumstable.util.effect.CStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class PotionRegistry {

    public static CStatusEffect DRUNK;

    public static void init() {
    }

    public static CStatusEffect registerEffect(String id, StatusEffect entry) {
        return (CStatusEffect) Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, id), entry);
    }

}
