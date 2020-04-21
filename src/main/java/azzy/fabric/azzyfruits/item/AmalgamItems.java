package azzy.fabric.azzyfruits.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.ForgottenFruits.PLANTSTUFF;

public class AmalgamItems extends Item {

    public static final String AMALGAMS[] = new String[]{
            "cloudberry",
            "incandescent",
            "vompollolowm",
            "ambrosia"
    };

    public AmalgamItems(Item.Settings settings){
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        for (int i = 0; i < 1; i++) {
            tooltip.add(new TranslatableText("item.azzyfruits." + AMALGAMS[i] + "_amalgam.tooltip"));
        }
    }



    public static class ConstructAmalgam{

        private Item jelly;
        private Identifier type;

        public ConstructAmalgam(Rarity rarity, int loop){
            jelly = new AmalgamItems(new Item.Settings().group(PLANTSTUFF).maxCount(32).rarity(rarity).food(FoodItems.FoodBackend(6, 1.0f, true)));
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
