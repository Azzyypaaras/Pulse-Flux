package azzy.fabric.forgottenfruits.util.tracker;

import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;

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
