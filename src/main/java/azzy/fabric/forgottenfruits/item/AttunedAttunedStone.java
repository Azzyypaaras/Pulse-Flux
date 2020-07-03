package azzy.fabric.forgottenfruits.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AttunedAttunedStone extends Item {

    public AttunedAttunedStone(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
