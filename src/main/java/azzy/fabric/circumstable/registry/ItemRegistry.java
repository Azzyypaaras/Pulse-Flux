package azzy.fabric.circumstable.registry;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class ItemRegistry extends Item {

    private ItemRegistry(Item.Settings settings) {
        super(settings);
    }


    public static void init() {
    }

    private static Item register(Identifier name, Item item) {
        Registry.register(Registry.ITEM, name, item);
        return item;
    }

    public static Item registerBucket(String name, FlowableFluid item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BucketItem(item, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroup.MISC)));
    }
}


