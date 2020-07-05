package azzy.fabric.forgottenfruits.util.container;

import azzy.fabric.forgottenfruits.util.controller.BarrelController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BarrelScreen extends CottonInventoryScreen<BarrelController> {
    public BarrelScreen(BarrelController container, PlayerEntity player) {
        super(container, player);
    }
}
