package azzy.fabric.forgottenfruits.item;

import azzy.fabric.forgottenfruits.registry.damagetypes.DamageIncandescence;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MODID;
import static azzy.fabric.forgottenfruits.ForgottenFruits.PLANTSTUFF;

public class AmalgamItems extends Item {

    public static final String AMALGAMS[] = new String[]{
            "cloudberry",
            "incandescent",
            "vompollolowm",
            "ambrosia"
    };

    public String type;

    public AmalgamItems(Item.Settings settings, String type){
        super(settings);
        this.type = type;
    }

    public String getJellyType(){
        return type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(type.equals("incandescent")){
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 2));
            if(!world.isClient)
                if(RANDOM.nextInt(5) == 0) {
                    user.setHealth(0.1f);
                    user.world.createExplosion(null, DamageIncandescence.INCANDESCENCE, null, user.getX(), user.getY(), user.getZ(), 6f, false, Explosion.DestructionType.BREAK);
                }
                else
                    user.setOnFireFor(5);
        }
        return this.isFood() ? user.eatFood(world, stack) : stack;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if(getJellyType().equals(AMALGAMS[0]))
            tooltip.add(new TranslatableText("item.azzyfruits." + AMALGAMS[0] + "_amalgam.tooltip"));
        else if(getJellyType().equals(AMALGAMS[1]))
            tooltip.add(new TranslatableText("item.azzyfruits." + AMALGAMS[1] + "_amalgam.tooltip"));
    }



    public static class ConstructAmalgam{

        private Item jelly;
        private Identifier type;

        public ConstructAmalgam(Rarity rarity, int loop){
            jelly = new AmalgamItems(new Item.Settings().group(PLANTSTUFF).maxCount(32).rarity(rarity).food(FoodItems.FoodBackend(6, 1.0f, true)), AMALGAMS[loop]);
            type = new Identifier(MODID, AMALGAMS[loop] + "_amalgam");
        }

        public Identifier getKey(){
            return type;
        }

        public Item getJelly(){
            return jelly;
        }

    }
}
