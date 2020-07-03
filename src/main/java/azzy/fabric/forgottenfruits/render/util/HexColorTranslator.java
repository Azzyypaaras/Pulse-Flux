package azzy.fabric.forgottenfruits.render.util;

public class HexColorTranslator {

    public static int[] translate(int hex){
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        return new int[]{r, g, b};
    }
}
