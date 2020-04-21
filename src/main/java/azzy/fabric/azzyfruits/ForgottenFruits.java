package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.registry.BlockRegistry;
import azzy.fabric.azzyfruits.registry.CropRegistry;
import azzy.fabric.azzyfruits.registry.GeneratedRegistry;
import azzy.fabric.azzyfruits.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ForgottenFruits implements ModInitializer {
	public static final String MODID = "azzyfruits";
	public static final ItemGroup PLANTSTUFF = FabricItemGroupBuilder.build(new Identifier(MODID, "plantstuff"), () -> new ItemStack(ItemRegistry.AMALGAM_REGISTRY.get(0).getJelly()));
	@Override
	public void onInitialize() {
		System.out.println("Come one come all, get your cloudberries here!");
		//Registries
		ItemRegistry.init();
		BlockRegistry.init();
		CropRegistry.init();
		GeneratedRegistry.init();
	}
	public void onInitializing(){
	}
}
