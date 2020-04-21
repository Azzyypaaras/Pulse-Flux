package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.generated.PlantGen;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.CropRegistry.*;

public class GeneratedRegistry {

    public static final Feature<DefaultFeatureConfig> WILD_CLOUDBERRIES = register("wild_cloudberries", new PlantGen(DefaultFeatureConfig::deserialize, 7, 2, CLOUDBERRY_WILD));

    public static Feature<DefaultFeatureConfig> register(String name, Feature<DefaultFeatureConfig> item){
        Registry.register(Registry.FEATURE, new Identifier(MODID, name), item);
        return item;
    }

    public static void init(){
        Biomes.TAIGA.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, WILD_CLOUDBERRIES.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP.configure(new CountDecoratorConfig(5))));
    }
}
