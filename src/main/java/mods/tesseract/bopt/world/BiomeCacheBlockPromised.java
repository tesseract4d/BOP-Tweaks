package mods.tesseract.bopt.world;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeCacheBlockPromised {
    public float[] temperatureValues;

    public float[] rainfallValues;

    public BiomeGenBase[] biomes;

    public int xPosition;

    public int zPosition;

    public long lastAccessTime;

    final BiomeCachePromised theBiomeCache;

    public BiomeCacheBlockPromised(BiomeCachePromised par1BiomeCache, int par2, int par3) {
        this.theBiomeCache = par1BiomeCache;
        this.temperatureValues = new float[256];
        this.rainfallValues = new float[256];
        this.biomes = new BiomeGenBase[256];
        this.xPosition = par2;
        this.zPosition = par3;
        BiomeCachePromised.getChunkManager(par1BiomeCache).getTemperatures(this.temperatureValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCachePromised.getChunkManager(par1BiomeCache).getRainfall(this.rainfallValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCachePromised.getChunkManager(par1BiomeCache).getBiomeGenAt(this.biomes, par2 << 4, par3 << 4, 16, 16, false);
    }

    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.biomes[par1 & 0xF | (par2 & 0xF) << 4];
    }
}
