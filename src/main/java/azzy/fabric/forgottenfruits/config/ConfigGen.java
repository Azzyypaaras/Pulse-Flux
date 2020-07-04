package azzy.fabric.forgottenfruits.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "forgottenfruits")
@Config.Gui.Background(value = "minecraft:textures/block/dark_oak_planks.png")
public class ConfigGen implements ConfigData {

    @ConfigEntry.Gui.Tooltip(count = 3)
    private boolean regen = false;

    @ConfigEntry.Gui.Tooltip
    private boolean debug = false;

    public boolean isRegen() {
        return regen;
    }

    public boolean isDebug() {
        return debug;
    }
}
