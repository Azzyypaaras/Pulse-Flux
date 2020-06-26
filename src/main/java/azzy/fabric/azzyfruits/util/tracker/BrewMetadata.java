package azzy.fabric.azzyfruits.util.tracker;

import net.minecraft.nbt.CompoundTag;

public class BrewMetadata {

    private boolean isFermented, isDistilled;
    private double quality, purity, content;
    private int color;

    public BrewMetadata(boolean isFermented, boolean isDistilled, double quality, double purity, double content, int color){
        this.isFermented = isFermented;
        this.isDistilled = isDistilled;
        this.quality = quality;
        this.purity = purity;
        this.content = content;
        this.color = color;
    }

    public boolean isFermented() {
        return isFermented;
    }

    public boolean isDistilled() {
        return isDistilled;
    }

    public double getQuality() {
        return quality;
    }

    public double getPurity() {
        return purity;
    }

    public double getContent() {
        return content;
    }

    public int getColor() {
        return color;
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("fermented", isFermented);
        tag.putBoolean("distilled", isDistilled);
        tag.putDouble("quality", quality);
        tag.putDouble("purity", purity);
        tag.putDouble("content", content);
        tag.putInt("color", color);
        return tag;
    }

    public static BrewMetadata fromTag(CompoundTag tag){
        return new BrewMetadata(
                tag.getBoolean("fermented"),
                tag.getBoolean("distilled"),
                tag.getDouble("quality"),
                tag.getDouble("purity"),
                tag.getDouble("content"),
                tag.getInt("color")
        );
    }
}
