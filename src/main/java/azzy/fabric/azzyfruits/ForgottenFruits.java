package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.RunArgs;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import net.minecraft.util.registry.Registry;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.logging.log4j.Logger;

public class ForgottenFruits implements ModInitializer {
	public static final String MODID = "azzyfruits";
	public static final ItemGroup PLANTSTUFF = FabricItemGroupBuilder.build(new Identifier(MODID, "plantstuff"), () -> new ItemStack(ItemRegistry.AMALGAM_REGISTRY.get(0).getJelly()));
	public static final ItemGroup BLOCKENTITIES = FabricItemGroupBuilder.build(new Identifier(MODID, "blockentities"), () -> new ItemStack(BlockRegistry.PRESS_BLOCK));
	public static final ItemGroup PLANTMATERIALS = FabricItemGroupBuilder.build(new Identifier(MODID, "materials"), () -> new ItemStack(BlockRegistry.PRESS_BLOCK));

	public static final Logger FFLog = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		FFLog.info("Come one come all, get your cloudberries here!");
		//Registries
		ItemRegistry.init();
		BlockRegistry.init();
		CropRegistry.init();
		GeneratedRegistry.init();
		BlockEntityRegistry.init();
		ContainerRegistry.init();
		FluidRegistry.init();
	}
	public void onInitializing(){
	}
}
