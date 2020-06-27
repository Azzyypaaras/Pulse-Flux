package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.potion.effects.DrunkEffect;
import azzy.fabric.azzyfruits.util.effect.FFStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class PotionRegistry {

    public static FFStatusEffect DRUNK;

    public static void init(){
        DRUNK = registerEffect("drunk", new DrunkEffect(StatusEffectType.NEUTRAL, 0xd66f00, false).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "a95f2388-5b6b-4fab-bbb7-a4609cf8b7b0", 0.2d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "bfb5f947-2d18-4d62-a332-340fb0521675", -0.15d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    public static FFStatusEffect registerEffect(String id, StatusEffect entry){
        return (FFStatusEffect) Registry.register(Registry.STATUS_EFFECT, new Identifier(MODID, id), entry);
    }

}
