package mods.tesseract.bopt.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPromisedFlora extends WorldGenerator {
    public Block flora;
    public int floraMeta;
    private int groupCount = 64;

    public WorldGenPromisedFlora(Block flora, int floraMeta) {
        this.flora = flora;
        this.floraMeta = floraMeta;
    }

    public WorldGenPromisedFlora(Block flora, int floraMeta, int groupCount) {
        this.flora = flora;
        this.floraMeta = floraMeta;
        this.groupCount = groupCount;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int l = 0; l < this.groupCount; l++) {
            int i1 = x + random.nextInt(8) - random.nextInt(8);
            int j1 = y + random.nextInt(4) - random.nextInt(4);
            int k1 = z + random.nextInt(8) - random.nextInt(8);
            if (j1 < 255 &&world.isAirBlock(i1, j1, k1) &&  flora.canBlockStay(world, i1, j1, k1))
                setBlockAndNotifyAdequately(world, i1, j1, k1, this.flora, this.floraMeta);
        }
        return true;
    }
}
