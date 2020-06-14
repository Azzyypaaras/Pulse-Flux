package azzy.fabric.azzyfruits.util.container;

import azzy.fabric.azzyfruits.util.controller.BarrelController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BarrelScreen extends CottonInventoryScreen<BarrelController> {
    public BarrelScreen(BarrelController container, PlayerEntity player) {super(container, player);}
}
