package mods.tesseract.bopt.world.layer;

import biomesoplenty.common.world.layer.hell.*;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;

public abstract class BiomeLayerPromised extends BiomeLayerHell {
    public BiomeLayerPromised(long seed) {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType) {
        int biomesize = 3;
        BiomeLayerHell obj = new BiomeLayerHellCreate(1L, false);
        obj = new BiomeLayerHellFuzzyZoom(2000L, obj);
        for (int i = 1; i < 3; ) {
            obj = new BiomeLayerHellZoom(2000L + i, obj);
            i++;
        }
        obj = BiomeLayerHellZoom.magnify(1000L, obj, 0);
        obj = new BiomeLayerPromisedBiomes(200L, obj);
        obj = BiomeLayerHellZoom.magnify(1000L, obj, 2);
        for (int j = 0; j < biomesize; ) {
            obj = new BiomeLayerHellZoom(1000L + j, obj);
            j++;
        }
        BiomeLayerHellVoronoiZoom g = new BiomeLayerHellVoronoiZoom(10L, obj);
        obj.initWorldGenSeed(seed);
        g.initWorldGenSeed(seed);
        return new GenLayer[] { obj, g };
    }
}
