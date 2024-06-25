package mods.tesseract.bopt.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.tesseract.bopt.BOPTBlocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderPromised extends WorldProvider {
    public void registerWorldChunkManager() {
        //if (Biomes.promisedLandForest.isPresent() || Biomes.promisedLandPlains.isPresent() || Biomes.promisedLandShrub.isPresent() || Biomes.promisedLandSwamp.isPresent())
        this.worldChunkMgr = new WorldChunkManagerPromised(this.worldObj);
        this.hasNoSky = false;
        this.dimensionId = 20;
    }

    public String getDimensionName() {
        return "Promised Land";
    }

    public boolean canRespawnHere() {
        return false;
    }

    public double getMovementFactor() {
        return 16.0D;
    }

    public float calculateCelestialAngle(long par1, float par3) {
        return 1.0F;
    }

    public float getCloudHeight() {
        return 0.0F;
    }

    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return this.worldObj.getTopBlock(par1, par2) == BOPTBlocks.holyGrass;
    }

    public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(100, 50, 0);
    }

    public int getAverageGroundLevel() {
        return 64;
    }

    public double getHorizon() {
        return 0.0D;
    }

    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return true;
    }

    public double getVoidFogYFactor() {
        return 1.0D;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
        float var3 = MathHelper.cos(par1 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
        if (var3 < 0.0F)
            var3 = 0.0F;
        if (var3 > 1.0F)
            var3 = 1.0F;
        float var4 = 1.0F;
        float var5 = 0.7372549F;
        float var6 = 0.25882354F;
        var4 *= var3 * 3.94F + 0.06F;
        var5 *= var3 * 0.94F + 0.06F;
        var6 *= var3 * 0.91F + 0.09F;
        return Vec3.createVectorHelper(var4, var5, var6);
    }

    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        allowPeaceful = true;
    }

    public String getWelcomeMessage() {
        return "Entering the Promised Land";
    }

    public String getDepartMessage() {
        return "Leaving the Promised Land";
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderPromised(this.worldObj, this.worldObj.getSeed());
    }
}
