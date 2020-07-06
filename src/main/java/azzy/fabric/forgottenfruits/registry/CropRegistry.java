package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;
import static azzy.fabric.forgottenfruits.registry.ItemRegistry.*;

public class CropRegistry {

    private static final Material VOMPOLLOLOWM = (new FabricMaterialBuilder(MaterialColor.FOLIAGE)).lightPassesThrough().destroyedByPiston().burnable().build();

    public static final Block CLOUD_BERRY_CROP = register("cloudberry_crop", new PlantBase(6, Material.PLANT, BlockSoundGroup.CROP, CLOUD_BERRY_SEEDS, 8, 16, null, 0, 0, 0, 0));
    public static final Block CLOUD_BERRY_WILD = register("cloudberry_wild", new WildPlantBase("cloudberry", Material.PLANT, BlockSoundGroup.CROP, null, 0, 0, 0, 0));

    public static final Block CINDERMOTE_CROP = register("cindermote_crop", new CindermoteBlock(7, Material.SOLID_ORGANIC, BlockSoundGroup.NETHER_WART, CINDERMOTE_SEEDS, 0, 16, ParticleTypes.FLAME, 20, 30, 0.05f, 8));
    public static final Block CINDERMOTE_WILD = register("cindermote_wild", new WildPlantBase("cindermote", Material.SOLID_ORGANIC, BlockSoundGroup.NETHER_WART, ParticleTypes.FLAME, 10, 20, 0.05f, 30));

    public static final Block VOMPOLLOLOWM_CROP_BASE = register("vompollolowm_crop", new VompollolowmBase(4, Material.PLANT, BlockSoundGroup.CROP, VOMPOLLOLOWM_SEEDS, 8, 16, null, 0 ,0 ,0, 0));
    public static final Block VOMPOLLOLOWM_CROP_STALK = register("vompollolowm_stalk", new VompollolowmStalk(2, VOMPOLLOLOWM, BlockSoundGroup.CROP, null, 0, 16, null, 0, 0 ,0, 0));
    public static final Block VOMPOLLOLOWM_CROP_FRUIT = register("vompollolowm_fruit", new VompollolowmFruit(1, VOMPOLLOLOWM, BlockSoundGroup.CROP, null, 0, 16, null, 0, 0 ,0, 0));
    public static final Block VOMPOLLOLOWM_WILD_BASE = register("vompollolowm_wild", new VompollolowmWildBase("vompollolowm", Material.PLANT, BlockSoundGroup.CROP, null, 0, 0, 0 ,0));
    public static final Block VOMPOLLOLOWM_WILD_STALK = register("vompollolowm_wild_stalk", new VompollolowmWildBase("vompollolowm", VOMPOLLOLOWM, BlockSoundGroup.CROP, null, 0, 0, 0 ,0));
    public static final Block VOMPOLLOLOWM_WILD_FRUIT = register("vompollolowm_wild_fruit", new VompollolowmWildBase("vompollolowm", VOMPOLLOLOWM, BlockSoundGroup.CROP, null, 0, 0, 0 ,0));

    private static Block register(String name, Block item) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static void init() {
    }
}
