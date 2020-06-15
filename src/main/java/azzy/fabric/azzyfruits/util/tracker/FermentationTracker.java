package azzy.fabric.azzyfruits.util.tracker;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.staticentities.blockentity.BarrelEntity;

public class FermentationTracker {

    public double quality, purity, content, temp, percentOffset;
    public int height, light, time, age, minTime;
    private FluidVolume liquid;
    private final BarrelEntity fermenter;
    private boolean active;

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
        minTime = 72000;
        minTime = minTime * (fermenter.fluidInv.getMaxAmount_F(0).asInt(1) / liquid.getAmount_F().asInt(1));
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
