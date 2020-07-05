package azzy.fabric.forgottenfruits.util.tracker;

import net.minecraft.nbt.CompoundTag;

public class BrewMetadata {

    private final boolean isFermented;
    private final boolean isDistilled;
    private final double quality;
    private final double purity;
    private final double content;

    public BrewMetadata(boolean isFermented, boolean isDistilled, double quality, double purity, double content) {
        this.isFermented = isFermented;
        this.isDistilled = isDistilled;
        this.quality = quality;
        this.purity = purity;
        this.content = content;
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

    public static BrewMetadata fromTag(CompoundTag tag) {
        return new BrewMetadata(
                tag.getBoolean("fermented"),
                tag.getBoolean("distilled"),
                tag.getDouble("quality"),
                tag.getDouble("purity"),
                tag.getDouble("content")
        );
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("fermented", isFermented);
        tag.putBoolean("distilled", isDistilled);
        tag.putDouble("quality", quality);
        tag.putDouble("purity", purity);
        tag.putDouble("content", content);
        return tag;
    }
}
