package azzy.fabric.forgottenfruits.item;

import azzy.fabric.forgottenfruits.registry.PotionRegistry;
import azzy.fabric.forgottenfruits.util.tracker.BrewMetadata;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class LiquorBottle extends Item {
    public LiquorBottle(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(Items.GLASS_BOTTLE)));
        if (!world.isClient()) {
            CompoundTag tag = stack.getSubTag("brewmetadata");
            if (tag != null) {
                double content = tag.getDouble("content");
                int quality = (int) (tag.getDouble("quality") * 10.0);
                StatusEffect drunk = PotionRegistry.DRUNK;
                Random random = world.getRandom();
                if (content >= 0.9) {
                    if (random.nextInt(1) == 0) {
                        user.addStatusEffect(new StatusEffectInstance(drunk, 12000, 3));
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 1));
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 1200, 1));
                    }
                } else if (content >= 0.75) {
                    if (random.nextInt(2 + (quality / 2)) == 0) {
                        user.addStatusEffect(new StatusEffectInstance(drunk, (int) (6000.0 * (1.0 + (content - 0.5))), 2));
                    }
                } else if (content >= 0.5) {
                    if (random.nextInt((int) (4 + (quality / 1.5))) == 0) {
                        user.addStatusEffect(new StatusEffectInstance(drunk, (int) (6000.0 * (1.0 + (content - 0.5))), 2));
                    }
                } else if (content >= 0.2) {
                    if (random.nextInt(4 + quality) == 0) {
                        user.addStatusEffect(new StatusEffectInstance(drunk, (int) (2400.0 * (1.0 + (content - 0.2))), 1));
                    }
                } else {
                    if (random.nextInt(10 + quality) == 0) {
                        user.addStatusEffect(new StatusEffectInstance(drunk, (int) (1200.0 * (1.0 + content)), 0));
                    }
                }

                double quality2 = tag.getDouble("quality");

                if (quality2 >= 0.98) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 2));
                }
                if (quality2 >= 0.9) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 2));
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, 4));
                } else if (quality2 <= 0.2) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 0));
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
                }
            } else
                user.addStatusEffect(new StatusEffectInstance(PotionRegistry.DRUNK, 1200));
        }
        return super.finishUsing(stack, world, user);
    }


    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag tag = stack.getSubTag("brewmetadata");
        if (tag != null) {
            BrewMetadata metadata = BrewMetadata.fromTag(tag);
            DecimalFormat format = new DecimalFormat("###.#");
            double quality = Double.parseDouble(format.format(metadata.getQuality() * 100.0));
            double purity = Double.parseDouble(format.format(metadata.getPurity() * 100.0));
            int content = (int) (metadata.getContent() * 200.0);
            tooltip.add(new LiteralText(I18n.translate("drink.forgottenfruits.quality", quality)));
            tooltip.add(new LiteralText(I18n.translate("drink.forgottenfruits.purity", purity)));
            tooltip.add(new LiteralText(I18n.translate("drink.forgottenfruits.content", content)));
        } else
            tooltip.add(new TranslatableText("drink.forgottenfruits.invalid"));
    }
}
