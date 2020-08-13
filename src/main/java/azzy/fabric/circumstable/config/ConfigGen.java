package azzy.fabric.circumstable.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "circumstable")
@Config.Gui.Background(value = "minecraft:textures/block/dark_oak_planks.png")
public class ConfigGen implements ConfigData {

    @ConfigEntry.Gui.Tooltip(count = 3)
    private final boolean regen = false;

    @ConfigEntry.Gui.Tooltip
    private final boolean debug = false;

    public boolean isRegen() {
        return regen;
    }

    public boolean isDebug() {
        return debug;
    }
}
