package azzy.fabric.forgottenfruits.util.tracker;

import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;

public class BrewingTracker {

    private final int color = 0xFFFFFF;
    private final WitchCauldronEntity cauldron;
    private final double heat;
    private final Item brew;
    private final BrewMetadata metadata;

    public BrewingTracker(WitchCauldronEntity cauldron) {
        this.cauldron = cauldron;
        this.heat = cauldron.getHeat();
        this.brew = cauldron.getBrew();
        this.metadata = cauldron.getMetadata();
    }

    public WitchCauldronEntity getCauldron() {
        return cauldron;
    }

    public double getHeat() {
        return heat;
    }

    public Item getBrew() {
        return brew;
    }

    public BrewMetadata getMetadata() {
        return metadata;
    }

    @Environment(EnvType.CLIENT)
    public int getColor() {
        return color;
    }
}
