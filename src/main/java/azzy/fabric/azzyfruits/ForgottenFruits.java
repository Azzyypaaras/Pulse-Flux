package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.config.ConfigGen;
import azzy.fabric.azzyfruits.registry.*;
import azzy.fabric.azzyfruits.util.recipe.JanksonRecipeParser;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import blue.endless.jankson.Jankson;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

public class ForgottenFruits implements ModInitializer {
	public static final String MODID = "azzyfruits";
	public static final ItemGroup PLANTSTUFF = FabricItemGroupBuilder.build(new Identifier(MODID, "plantstuff"), () -> new ItemStack(ItemRegistry.AMALGAM_REGISTRY.get(0).getJelly()));
	public static final ItemGroup BLOCKENTITIES = FabricItemGroupBuilder.build(new Identifier(MODID, "blockentities"), () -> new ItemStack(BlockRegistry.PRESS_BLOCK));
	public static final ItemGroup PLANTMATERIALS = FabricItemGroupBuilder.build(new Identifier(MODID, "materials"), () -> new ItemStack(ItemRegistry.APPLE_ALLOY));

	public static HashMap<RecipeRegistryKey, TrueRecipeRegistry.RecipeType> REGISTEREDRECIPES = new HashMap<>();

	public static final Logger FFLog = LogManager.getLogger(MODID);
	public static volatile ConfigGen config;

	@Override
	public void onInitialize() {

		AutoConfig.register(ConfigGen.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ConfigGen.class).getConfig();

		FFLog.info("Come one come all, get your cloudberries here!");
		JanksonRecipeParser.init();

		//Registries
		ItemRegistry.init();
		BlockRegistry.init();
		PotionRegistry.init();
		CropRegistry.init();
		GeneratedRegistry.init();
		BlockEntityRegistry.init();
		ContainerRegistry.init();
		FluidRegistry.init();
		RecipeRegistry.init();
	}

	public void onInitializing(){
	}
}
