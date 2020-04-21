package azzy.fabric.azzyfruits.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.ForgottenFruits.PLANTSTUFF;
import static azzy.fabric.azzyfruits.registry.CropRegistry.*;

public class BlockRegistry {
    public static final Block CLOUDBERRY_BLOCK = register("cloudberry_block", new Block(FabricBlockSettings.of(Material.UNDERWATER_PLANT).materialColor(MaterialColor.ORANGE).nonOpaque().breakInstantly().sounds(BlockSoundGroup.SLIME).slipperiness(0.8f).build()));;

    public static Item.Settings defaultSettings(){
        return new Item.Settings().group(PLANTSTUFF);
    }

    private static Block register(String name, Block item){
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), item);
        Registry.register(Registry.ITEM, new Identifier(MODID, name), new BlockItem(item, defaultSettings()));
        return item;
    }

    @Environment(EnvType.CLIENT)
    public static ArrayList<Block> registryTrans = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static ArrayList<Block> registryPartial = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void initTransparency(){
        registryTrans.add(CLOUDBERRY_BLOCK);
    }

    @Environment(EnvType.CLIENT)
    public static void initPartialblocks(){
        registryPartial.add(CLOUDBERRY_CROP);
        registryPartial.add(CLOUDBERRY_WILD);
    }

    public static void init(){
    }

}
