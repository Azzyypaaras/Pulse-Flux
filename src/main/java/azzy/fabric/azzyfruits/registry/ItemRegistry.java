package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.item.AmalgamItems;
import azzy.fabric.azzyfruits.item.FoodItems;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.ForgottenFruits.PLANTSTUFF;

public class ItemRegistry extends Item{

    public static Item CLOUDBERRY_FRUIT;
    public static Item CLOUDBERRY_SEEDS;
    public static ArrayList<AmalgamItems.ConstructAmalgam> AMALGAM_REGISTRY = new ArrayList<AmalgamItems.ConstructAmalgam>();

    private ItemRegistry(Item.Settings settings){
        super(settings);
    }

    public static void init(){

        //Berries
        CLOUDBERRY_FRUIT = register(new Identifier(MODID, "cloudberry_fruit"), new Item(defaultSettings().food(FoodItems.FoodBackendSpecial(3, 0.5f, true, false, StatusEffects.LEVITATION, 0.1f, 200))));
        CLOUDBERRY_SEEDS = register(new Identifier(MODID, "cloudberry_seeds"), new AliasedBlockItem(CropRegistry.CLOUDBERRY_CROP, defaultSettings().food(FoodItems.FoodBackendSpecial(-3, -2f, false, false, StatusEffects.LEVITATION, 1f, 600))));

        //Seeds


        //Jellies
        for (int i = 0; i < 1; i++) {
            AMALGAM_REGISTRY.add(i, new AmalgamItems.ConstructAmalgam(Rarity.RARE, i));
        }

        for(int j = 0; j < 1; j++){
            register(AMALGAM_REGISTRY.get(j).getKey(), AMALGAM_REGISTRY.get(j).getJelly());
        }
    }

    public static Item.Settings defaultSettings(){
        return new Item.Settings().group(PLANTSTUFF);
    }

    private static Item register(Identifier name, Item item){
        Registry.register(Registry.ITEM, name, item);
        return item;
    }
}


