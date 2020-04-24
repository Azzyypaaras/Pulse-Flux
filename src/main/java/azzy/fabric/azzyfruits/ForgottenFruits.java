package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.RunArgs;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ForgottenFruits implements ModInitializer {
	public static final String MODID = "azzyfruits";
	public static final ItemGroup PLANTSTUFF = FabricItemGroupBuilder.build(new Identifier(MODID, "plantstuff"), () -> new ItemStack(ItemRegistry.AMALGAM_REGISTRY.get(0).getJelly()));
	public static final ItemGroup BLOCKENTITIES = FabricItemGroupBuilder.build(new Identifier(MODID, "blockentities"), () -> new ItemStack(BlockRegistry.PRESS_BLOCK));
	@Override
	public void onInitialize() {
		System.out.println("Come one come all, get your cloudberries here!");
		//Registries
		ItemRegistry.init();
		BlockRegistry.init();
		CropRegistry.init();
		GeneratedRegistry.init();
		BlockEntityRegistry.init();
		ContainerRegistry Cr = new ContainerRegistry();
		Cr.init();
	}
	public void onInitializing(){
	}
}
