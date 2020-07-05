package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.render.blockentity.PressEntityRenderer;
import azzy.fabric.forgottenfruits.render.blockentity.WitchCauldronEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.PRESS;
import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.WITCH_CAULDRON;

public class RenderRegistry {

    public static void init() {
        BlockEntityRendererRegistry.INSTANCE.register(PRESS, PressEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(WITCH_CAULDRON, WitchCauldronEntityRenderer::new);
    }
}
