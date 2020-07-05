package azzy.fabric.forgottenfruits;

import azzy.fabric.forgottenfruits.config.ConfigGen;
import azzy.fabric.forgottenfruits.registry.*;
import azzy.fabric.forgottenfruits.util.interaction.HeatTransferHelper;
import azzy.fabric.forgottenfruits.util.recipe.JanksonRecipeParser;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ForgottenFruits implements ModInitializer {
    public static final String MOD_ID = "forgottenfruits";
    public static final ItemGroup PLANT_STUFF = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "plantstuff"), () -> new ItemStack(ItemRegistry.AMALGAM_REGISTRY.get(0).getJelly()));
    public static final ItemGroup BLOCK_ENTITIES = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "blockentities"), () -> new ItemStack(BlockRegistry.PRESS));
    public static final ItemGroup PLANT_MATERIALS = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "materials"), () -> new ItemStack(ItemRegistry.APPLE_ALLOY));

    public static final Map<RecipeRegistryKey, TrueRecipeRegistry.RecipeType<?>> REGISTERED_RECIPES = new HashMap<>();

    public static final Logger FFLog = LogManager.getLogger(MOD_ID);
    public static volatile ConfigGen config;

    @Override
    public void onInitialize() {

        AutoConfig.register(ConfigGen.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ConfigGen.class).getConfig();

        FFLog.info("Come one come all, get your cloudberries here!");
        JanksonRecipeParser.init();

        //Registries
        ItemRegistry.init();
        PotionRegistry.init();
        GeneratedRegistry.init();
        BlockEntityRegistry.init();
        ContainerRegistry.init();
        FluidRegistry.init();
        RecipeRegistry.init();
        ParticleRegistry.init();
        HeatTransferHelper.init();
    }
}
