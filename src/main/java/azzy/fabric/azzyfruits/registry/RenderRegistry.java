package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.render.blockentity.PressEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.*;

public class RenderRegistry {

    public static void init(){

        BlockEntityRendererRegistry.INSTANCE.register(PRESS_ENTITY, PressEntityRenderer::new);

    }

}
