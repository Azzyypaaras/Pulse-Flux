package azzy.fabric.azzyfruits.util.tracker;

import azzy.fabric.azzyfruits.staticentities.blockentity.WitchCauldronEntity;
import net.minecraft.nbt.CompoundTag;

public class BrewingTracker {

    private WitchCauldronEntity cauldron;
    private double heat;
    private String cachedBrew;
    private BrewMetadata metadata;

    public BrewingTracker(WitchCauldronEntity cauldron){
        this.cauldron = cauldron;
        this.heat = cauldron.getHeat();
        this.cachedBrew = cauldron.getCachedBrew();
        this.metadata = cauldron.getMetadata();
    }

}
