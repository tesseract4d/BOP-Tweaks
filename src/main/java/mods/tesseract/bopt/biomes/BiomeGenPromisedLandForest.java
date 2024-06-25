package mods.tesseract.bopt.biomes;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.world.features.trees.WorldGenBOPTaiga1;
import biomesoplenty.common.world.features.trees.WorldGenBOPTaiga2;
import biomesoplenty.common.world.features.trees.WorldGenOriginalTree;
import mods.tesseract.bopt.BOPTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenPromisedLandForest extends BiomeGenBase {
    public WorldGenAbstractTree worldGeneratorTreeMagic = new WorldGenOriginalTree(BOPCBlocks.logs2, BOPCBlocks.leaves1, 1, 2, false, 5, 3, false);
    public WorldGenAbstractTree worldGeneratorTreeHoly = new WorldGenBOPTaiga2(BOPCBlocks.logs1, BOPCBlocks.leaves2, 3, 1, false, 10, 10, 5, 4);;

    public BiomeGenPromisedLandForest(int id) {
        super(id);
        this.topBlock = BOPTBlocks.holyGrass;
        this.fillerBlock = BOPTBlocks.holyDirt;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        var d = (BOPTPromisedBiomeDecorator) theBiomeDecorator;
        d.treesPerChunk = 10;
        d.grassPerChunk = 0;
        d.pinkFlowersPerChunk = 2;
    }

    public WorldGenAbstractTree func_150567_a(Random e) {
        return e.nextInt(8) == 0 ? worldGeneratorTreeMagic : worldGeneratorTreeHoly;
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new BOPTPromisedBiomeDecorator();
    }
}
