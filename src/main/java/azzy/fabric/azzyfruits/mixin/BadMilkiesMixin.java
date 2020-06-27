package azzy.fabric.azzyfruits.mixin;

import azzy.fabric.azzyfruits.util.effect.FFStatusEffect;
import azzy.fabric.azzyfruits.util.mixin.EffectGetter;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(MilkBucketItem.class)
public class BadMilkiesMixin {

    /**
     * @author Azazelthedemonlord
     * :CRAB:MILK BAD:CRAB:
     */
    @Overwrite
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        }

        if (user instanceof PlayerEntity && !((PlayerEntity)user).abilities.creativeMode) {
            stack.decrement(1);
        }

        if (!world.isClient) {
            BadMilkiesMixin.clearSomeStatusEffects(user);
        }
        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }

    private static void clearSomeStatusEffects(LivingEntity entity){
        Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();


        StatusEffectInstance instance;
        ArrayList<StatusEffectInstance> preservedEffects = new ArrayList<>();
        while(iterator.hasNext()) {
            instance = iterator.next();
            if(instance.getEffectType() instanceof FFStatusEffect){
                preservedEffects.add(instance);
            }
            ((EffectGetter) entity).removeStatusEffect(instance);
            iterator.remove();
        }
        for (StatusEffectInstance preservedEffect : preservedEffects) {
            entity.addStatusEffect(preservedEffect);
        }
    }
}
