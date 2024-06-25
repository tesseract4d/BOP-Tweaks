package mods.tesseract.bopt;

import mods.tesseract.bopt.biomes.BiomeGenPromisedLandForest;
import net.minecraft.world.biome.BiomeGenBase;

public class BOPTBiomes {
    public static BiomeGenBase promisedLandForest;
    public static BiomeGenBase promisedLandPlains;
    public static BiomeGenBase promisedLandShrub;
    public static BiomeGenBase promisedLandSwamp;

    public static void init() {
        promisedLandForest = new BiomeGenPromisedLandForest(237).setColor(7925125).setBiomeName("Wonderous Woods").setTemperatureRainfall(2.0F, 2.0F).setHeight(new BiomeGenBase.Height(0.1F, 2.0F));
    }
}
