package azzy.fabric.forgottenfruits.util.container;

import azzy.fabric.forgottenfruits.util.controller.PressController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class PressScreen extends CottonInventoryScreen<PressController> {

    public PressScreen(PressController container, PlayerEntity player) {
        super(container, player);

    }
}
