package azzy.fabric.pulseflux.util.screen;

import azzy.fabric.pulseflux.util.controller.BlastFurnaceController;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BlastFurnaceMachineScreen extends CottonInventoryScreen<BlastFurnaceController> {

    public BlastFurnaceMachineScreen(BlastFurnaceController description, PlayerEntity player) {
        super(description, player);
    }

}
