package mods.tesseract.bopt.biomes;

import biomesoplenty.api.content.BOPCBlocks;
import mods.tesseract.bopt.world.WorldGenPromisedFlora;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BOPTPromisedBiomeDecorator extends BiomeDecorator {

    public World world;
    public Random rand;
    public BiomeGenBase biome;

    public WorldGenerator flora = new WorldGenPromisedFlora(BOPCBlocks.flowers, 6);
    public int pinkFlowersPerChunk;

    public BOPTPromisedBiomeDecorator() {
        super();
    }

    @Override
    public void decorateChunk(World worldIn, Random random, BiomeGenBase biome, int x, int z) {
        if (this.world != null) {
            System.out.println("Already decorating");
        } else {
            this.world = worldIn;
            this.rand = random;
            this.chunk_X = x;
            this.chunk_Z = z;
            this.biome = biome;
            this.genDecorations(biome);
            this.world = null;
            this.rand = null;
        }
    }

    @Override
    protected void genDecorations(BiomeGenBase biome) {
        int i = this.treesPerChunk;

        if (this.rand.nextInt(10) == 0) {
            ++i;
        }

        int k, l;
        int i1;

        for (int j = 0; j < i; ++j) {
            k = this.chunk_X + this.rand.nextInt(16) + 8;
            l = this.chunk_Z + this.rand.nextInt(16) + 8;
            i1 = this.world.getHeightValue(k, l);
            WorldGenAbstractTree t = biome.func_150567_a(this.rand);
            t.setScale(1.0D, 1.0D, 1.0D);

            if (t.generate(this.world, this.rand, k, i1, l)) {
                t.func_150524_b(this.world, this.rand, k, i1, l);
            }
        }

        for (i = 0; i < pinkFlowersPerChunk; i++) {
            int randX = chunk_X + rand.nextInt(16) + 8;
            int randZ = chunk_Z + rand.nextInt(16) + 8;
            int randY = rand.nextInt(world.getHeightValue(randX, randZ) + 32);
            flora.generate(world, rand, randX, randY, randZ);
        }
    }
}
