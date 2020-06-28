package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.item.AmalgamItems;
import azzy.fabric.azzyfruits.item.FoodItems;
import azzy.fabric.azzyfruits.item.LiquorBottle;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static azzy.fabric.azzyfruits.ForgottenFruits.*;

public class ItemRegistry extends Item{

    public static Item CLOUDBERRY_FRUIT, CLOUDBERRY_SEEDS, DRINKCLOUDBERRY;
    public static Item IGNOBLE_SILK;
    public static Item BASKET_ITEM;
    public static Item CINDERMOTE_FRUIT, CINDERMOTE_SEEDS, DRINKCINDERMOTE;
    public static Item APPLE_ALLOY;
    public static Item MULCH;
    public static Item SAMMICH;
    public static ArrayList<AmalgamItems.ConstructAmalgam> AMALGAM_REGISTRY = new ArrayList<AmalgamItems.ConstructAmalgam>();

    private ItemRegistry(Item.Settings settings){
        super(settings);
    }

    public static void init(){

        //What, why
        SAMMICH = register(new Identifier(MODID, "sammich"), new Item(defaultSettings().food(FoodItems.FoodBackend(8, 1f, false))));

        //Misc
        MULCH = register(new Identifier(MODID, "mulch"), new Item(new Item.Settings().group(PLANTMATERIALS)));

        //Drinks
        DRINKCLOUDBERRY = register(new Identifier(MODID, "drinkcloudberry"), new LiquorBottle(drinkSettings()));
        DRINKCINDERMOTE = register(new Identifier(MODID, "drinkcindermote"), new LiquorBottle(drinkSettings()));

        //Threads
        IGNOBLE_SILK = register(new Identifier(MODID, "thread_basic"),  new Item(new Item.Settings().group(PLANTMATERIALS)));

        //Storage
        BASKET_ITEM = register(new Identifier(MODID, "basket_block"), new AliasedBlockItem(BlockRegistry.BASKET_BLOCK, new Item.Settings().group(BLOCKENTITIES)));

        //Alloys
        APPLE_ALLOY = register(new Identifier(MODID, "apple_alloy"), new Item(new Item.Settings().group(PLANTMATERIALS)));

        //Berries
        CLOUDBERRY_FRUIT = register(new Identifier(MODID, "cloudberry_fruit"), new Item(defaultSettings().food(FoodItems.FoodBackendSpecial(3, 0.5f, true, false, StatusEffects.LEVITATION, 0.1f, 200))));
        CINDERMOTE_FRUIT = register(new Identifier(MODID, "cindermote_fruit"), new Item(defaultSettings().food(FoodItems.FoodBackend(6, 0.3f, false)).fireproof()));

        //Seeds
        CLOUDBERRY_SEEDS = register(new Identifier(MODID, "cloudberry_seeds"), new AliasedBlockItem(CropRegistry.CLOUDBERRY_CROP, defaultSettings().food(FoodItems.FoodBackendSpecial(-3, -2f, false, false, StatusEffects.LEVITATION, 1f, 600))));
        CINDERMOTE_SEEDS = register(new Identifier(MODID, "cindermote_seeds"), new AliasedBlockItem(CropRegistry.CINDERMOTE_CROP, defaultSettings().fireproof()));

        //Jellies
        for (int i = 0; i < 2; i++) {
            AMALGAM_REGISTRY.add(i, new AmalgamItems.ConstructAmalgam(Rarity.RARE, i));
        }

        for(int j = 0; j < 2; j++){
            register(AMALGAM_REGISTRY.get(j).getKey(), AMALGAM_REGISTRY.get(j).getJelly());
        }
    }

    private static Item.Settings defaultSettings(){
        return new Item.Settings().group(PLANTSTUFF);
    }
    private static Item.Settings drinkSettings() {return new Item.Settings().group(PLANTSTUFF).food(FoodItems.FoodBackend(0, 0, false)).maxCount(16);}

    private static Item register(Identifier name, Item item){
        Registry.register(Registry.ITEM, name, item);
        return item;
    }
}


