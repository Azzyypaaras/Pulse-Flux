package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.util.container.BasketContainer;
import azzy.fabric.azzyfruits.util.container.BasketGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

@Environment(EnvType.CLIENT)
public class GuiRegistry {

    public static void init(){
        //Basket
        ScreenProviderRegistry.INSTANCE.<BasketContainer>registerFactory(new Identifier(MODID, "basket_gui"), (container) -> new BasketGui(container, MinecraftClient.getInstance().player.inventory, new TranslatableText("gui."+MODID+".basket")));
    }
}
