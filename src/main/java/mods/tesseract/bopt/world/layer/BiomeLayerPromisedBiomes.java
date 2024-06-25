package mods.tesseract.bopt.world.layer;

import biomesoplenty.common.world.layer.hell.BiomeLayerHell;
import mods.tesseract.bopt.BOPTBiomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;

public class BiomeLayerPromisedBiomes extends BiomeLayerPromised {

    public ArrayList<BiomeManager.BiomeEntry> promisedBiomes = new ArrayList<>();

    public BiomeLayerPromisedBiomes(long par1, BiomeLayerHell par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
        this.promisedBiomes.add(new BiomeManager.BiomeEntry(BOPTBiomes.promisedLandForest,10));
    }

    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);
        for (int var7 = 0; var7 < par4; var7++) {
            for (int var8 = 0; var8 < par3; var8++) {
                initChunkSeed((var8 + par1), (var7 + par2));
                int var9 = var5[var8 + var7 * par3];
                var6[var8 + var7 * par3] = getWeightedBiomeFromList(this.promisedBiomes);
            }
        }
        return var6;
    }

    private int getWeightedBiomeFromList(List<BiomeManager.BiomeEntry> biomeList) {
        return ((BiomeManager.BiomeEntry) WeightedRandom.getItem(biomeList, (int)nextLong((WeightedRandom.getTotalWeight(biomeList) / 10)) * 10)).biome.biomeID;
    }
}
