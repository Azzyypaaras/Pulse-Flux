package azzy.fabric.azzyfruits.util.tracker;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.staticentities.blockentity.BarrelEntity;

public class FermentationTracker {

    double quality, purity, content, temp, percentOffset;
    int height, light, time, age;
    FluidVolume liquid;
    BarrelEntity fermenter;
    boolean active;

    private FermentationTracker(BarrelEntity entity){
        fermenter = entity;
        active = false;
        height = fermenter.getPos().getY();
    }

    public static FermentationTracker initiate(BarrelEntity entity){
        return new FermentationTracker(entity);
    }

    public void start(double purity, double content, double percentOffset, double quality){
        this.purity = purity;
        this.content = content;
        this.quality = quality;

        liquid = fermenter.fluidInv.getInvFluid(0);
        light = fermenter.getWorld().getLightLevel(fermenter.getPos());

        if(percentOffset != 0.0d)
            this.percentOffset = percentOffset;
        else
            this.percentOffset = 0.02;

        time = 0;
    }

    public void tick(){
        if(!active){
            age++;
            return;
        }
        if(time%20==0){
            light = fermenter.getWorld().getLightLevel(fermenter.getPos());
        }
    }

    public void cancel(){
        active = false;
        percentOffset = 0.0d;
    }
}
