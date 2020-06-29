package azzy.fabric.azzyfruits.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "azzyfruits")
@Config.Gui.Background(value = "minecraft:dark_oak_planks")
public class ConfigGen implements ConfigData {

    @ConfigEntry.Gui.Tooltip(count = 3)
    private boolean regen = false;

    @ConfigEntry.Gui.Tooltip
    private boolean debug = false;

    public boolean isRegenOn(){return regen;}
    public boolean isDebugOn() {
        return debug;
    }
}
