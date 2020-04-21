package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.PlantBase;
import azzy.fabric.azzyfruits.block.WildPlantBase;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.RandomPatchFeature;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.ItemRegistry.*;

public class CropRegistry {

    public static final Block CLOUDBERRY_CROP = register("cloudberry_crop", new PlantBase("cloudberry", 6, Material.PLANT, BlockSoundGroup.CROP, CLOUDBERRY_SEEDS, 8, 16, null, 0, 0));
    public static final Block CLOUDBERRY_WILD = register("cloudberry_wild", new WildPlantBase("cloudberry", Material.PLANT, BlockSoundGroup.CROP, null, 0, 0));

    private static Block register(String name, Block item){
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), item);
        return item;
    }

    public static void init(){

    }
}
