package mods.tesseract.bopt.world;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.common.eventhandler.Event;
import mods.tesseract.bopt.BOPTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;

public class ChunkProviderPromised implements IChunkProvider {
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    /**
     * The biomes that are used to generate the chunk
     */
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    public ChunkProviderPromised(World world, long seed) {
        this.endWorld = world;
        this.endRNG = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);

        NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5};
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.endRNG, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorOctaves) noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves) noiseGens[4];
    }

    public void generateTerrain(int x, int z, Block[] blocks, BiomeGenBase[] biomes) {
        byte b0 = 2;
        int k = b0 + 1;
        byte b1 = 33;
        int l = b0 + 1;
        this.densities = this.initializeNoiseField(this.densities, x * b0, 0, z * b0, k, b1, l);

        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < 32; ++k1) {
                    double d0 = 0.25D;
                    double d1 = this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
                    double d2 = this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
                    double d3 = this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
                    double d4 = this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
                    double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
                    double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
                    double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
                    double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 4; ++l1) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 8; ++i2) {
                            int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
                            short short1 = 128;
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 8; ++k2) {
                                Block block = null;

                                if (d15 > 0.0D) {
                                    block = BOPTBlocks.holyStone;
                                }

                                blocks[j2] = block;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int par1, int par2, Block[] par3, BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        byte var98 = 63;
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, par3, par4ArrayOfBiomeGenBase);
        MinecraftForge.EVENT_BUS.post((Event) event);
        if (event.getResult() == Event.Result.DENY)
            return;
        for (int var5 = 0; var5 < 16; var5++) {
            for (int var6 = 0; var6 < 16; var6++) {
                BiomeGenBase var99 = par4ArrayOfBiomeGenBase[var6 + var5 * 16];
                byte var7 = 1;
                int var8 = -1;
                Block var9 = var99.topBlock;
                Block var10 = var99.fillerBlock;
                for (int var11 = 127; var11 >= 0; var11--) {
                    int var12 = (var6 * 16 + var5) * 128 + var11;
                    Block var13 = par3[var12];
                    if (var13 == Blocks.air) {
                        var8 = -1;
                    } else if (var13 == BOPTBlocks.holyStone) {
                        if (var8 == -1) {
                            if (var7 <= 0) {
                                var9 = Blocks.air;
                                var10 = BOPTBlocks.holyStone;
                            } else if (var11 >= var98 - 4 && var11 <= var98 + 1) {
                                var9 = var99.topBlock;
                                var10 = var99.fillerBlock;
                            }
                            if (var11 < var98 && var9 == Blocks.air)
                                var9 = Blocks.water;
                            var8 = var7;
                            if (var11 >= 0) {
                                par3[var12] = var9;
                            } else {
                                par3[var12] = var10;
                            }
                        } else if (var8 > 0) {
                            var8--;
                            par3[var12] = var10;
                        }
                    }
                }
            }
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return provideChunk(par1, par2);
    }

    public Chunk provideChunk(int par1, int par2) {
        this.endRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        Block[] var3 = new Block[32768];
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        generateTerrain(par1, par2, var3, this.biomesForGeneration);
        replaceBlocksForBiome(par1, par2, var3, this.biomesForGeneration);
        Chunk var4 = new Chunk(this.endWorld, var3, par1, par2);
        byte[] var5 = var4.getBiomeArray();
        for (int var6 = 0; var6 < var5.length; var6++)
            var5[var6] = (byte) (this.biomesForGeneration[var6]).biomeID;
        var4.generateSkylightMap();
        return var4;
    }

    private double[] initializeNoiseField(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, ad, i, j, k, l, i1, j1);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) return event.noisefield;
        if (ad == null)
            ad = new double[l * i1 * j1];
        double d0 = 684.412D;
        double d1 = 684.412D;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, i, k, l, j1, 200.0D, 200.0D, 0.5D);
        d0 *= 2.0D;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, i, j, k, l, i1, j1, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, i, j, k, l, i1, j1, d0, d1, d0);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, i, j, k, l, i1, j1, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;

        for (int i2 = 0; i2 < l; ++i2) {
            for (int j2 = 0; j2 < j1; ++j2) {
                double d2 = (this.noiseData4[l1] + 256.0D) / 512.0D;
                if (d2 > 1.0D)
                    d2 = 1.0D;
                double d3 = this.noiseData5[l1] / 8000.0D;
                if (d3 < 0.0D)
                    d3 = -d3 * 0.3D;
                d3 = d3 * 3.0D - 2.0D;
                float f = (float) (i2 + i - 0) / 1.0F;
                float f1 = (float) (j2 + k - 0) / 1.0F;
                float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
                if (f2 > 80.0F)
                    f2 = 80.0F;
                if (f2 < -100.0F)
                    f2 = -100.0F;
                if (d3 > 1.0D)
                    d3 = 1.0D;
                d3 /= 8.0D;
                d3 = 0.0D;
                if (d2 < 0.0D)
                    d2 = 0.0D;
                d2 += 0.5D;
                d3 = d3 * (double) i1 / 16.0D;
                ++l1;
                double d4 = (double) i1 / 2.0D;
                for (int k2 = 0; k2 < i1; ++k2) {
                    double d5 = 0.0D;
                    double d6 = ((double) k2 - d4) * 8.0D / d2;
                    if (d6 < 0.0D)
                        d6 *= -1.0D;
                    double d7 = this.noiseData2[k1] / 512.0D;
                    double d8 = this.noiseData3[k1] / 512.0D;
                    double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;
                    if (d9 < 0.0D)
                        d5 = d7;
                     else if (d9 > 1.0D)
                        d5 = d8;
                     else
                        d5 = d7 + (d8 - d7) * d9;
                    d5 -= 8.0D;
                    d5 += (double) f2;
                    byte b0 = 2;
                    double d10;
                    if (k2 > i1 / 2 - b0) {
                        d10 = (float) (k2 - (i1 / 2 - b0)) / 64.0F;
                        if (d10 < 0.0D)
                            d10 = 0.0D;
                        if (d10 > 1.0D)
                            d10 = 1.0D;
                        d5 = d5 * (1.0D - d10) + -3000.0D * d10;
                    }
                    b0 = 8;
                    if (k2 < b0) {
                        d10 = (double) ((float) (b0 - k2) / ((float) b0 - 1.0F));
                        d5 = d5 * (1.0D - d10) + -30.0D * d10;
                    }
                    ad[k1] = d5;
                    ++k1;
                }
            }
        }
        return ad;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockSand.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post((Event) new PopulateChunkEvent.Pre(par1IChunkProvider, this.endWorld, this.endWorld.rand, par2, par3, false));
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
        for (int a = 0; a < 25; a++) {
            int x = var4 + this.endWorld.rand.nextInt(16);
            int y = this.endWorld.rand.nextInt(30) + 30;
            int z = var5 + this.endWorld.rand.nextInt(16);
            Block b = this.endWorld.getBlock(x, y, z);
            if (b == BOPTBlocks.holyStone)
                this.endWorld.setBlock(x, y, z, BOPCBlocks.gemOre, 0, 2);
        }
        var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);
        MinecraftForge.EVENT_BUS.post((Event) new PopulateChunkEvent.Post(par1IChunkProvider, this.endWorld, this.endWorld.rand, par2, par3, false));
        BlockSand.fallInstantly = false;
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "RandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(par2, par4);
        return (var5 == null) ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    public ChunkPosition func_147416_a(World par1World, String par2Str, int par3, int par4, int par5) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int par1, int par2) {
    }

    public void saveExtraData() {
    }
}
