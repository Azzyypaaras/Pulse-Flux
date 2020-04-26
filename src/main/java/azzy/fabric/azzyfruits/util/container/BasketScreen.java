package azzy.fabric.azzyfruits.util.container;

import azzy.fabric.azzyfruits.util.controller.BaseController;
import azzy.fabric.azzyfruits.util.controller.BasketController;
import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BasketScreen extends CottonInventoryScreen<BasketController> {
    public BasketScreen(BasketController container, PlayerEntity player) {
        super(container, player);
    }
}
