package mods.tesseract.bopt.blocks;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;
import mods.tesseract.bopt.BOPTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public class BlockBOPTSkygrass extends Block {
    private IIcon[] icon = new IIcon[6];

    public BlockBOPTSkygrass() {
        super(Material.grass);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);
        setStepSound(Block.soundTypeGrass);
        setTickRandomly(true);
        setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icon[0] = iconRegister.registerIcon("bopt:holydirt");
        this.icon[1] = iconRegister.registerIcon("bopt:holygrass_top");
        this.icon[2] = iconRegister.registerIcon("bopt:holygrass_side");
        this.icon[3] = iconRegister.registerIcon("bopt:holygrass_side");
        this.icon[4] = iconRegister.registerIcon("bopt:holygrass_side");
        this.icon[5] = iconRegister.registerIcon("bopt:holygrass_side");
    }

    public IIcon getIcon(int side, int meta) {
        if (side < 0 || side >= this.icon.length)
            side = 1;
        return this.icon[side];
    }

    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        if (world.getBlock(x, y, z) == this && side == ForgeDirection.UP && world.provider.dimensionId == -1)
            return true;
        return super.isFireSource(world, x, y, z, side);
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        boolean hasWater;
        EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
        switch (plantType) {
            case Cave:
                return isSideSolid(world, x, y, z, UP);
            case Plains:
                return true;
            case Beach:
                hasWater = (world.getBlock(x - 1, y, z).getMaterial() == Material.water || world.getBlock(x + 1, y, z).getMaterial() == Material.water || world.getBlock(x, y, z - 1).getMaterial() == Material.water || world.getBlock(x, y, z + 1).getMaterial() == Material.water);
                return hasWater;
        }
        return super.canSustainPlant(world, x, y, z, direction, plantable);
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.provider.isHellWorld) {
            world.setBlock(x, y + 1, z, Blocks.fire);
            world.setBlock(x, y, z, BOPCBlocks.bopGrass, 1, 2);
        }
        if (!world.isRemote)
            if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlock(x, y + 1, z).getLightOpacity() > 2) {
                world.setBlock(x, y, z, BOPTBlocks.holyDirt);
            } else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
                for (int var6 = 0; var6 < 4; var6++) {
                    int var7 = x + random.nextInt(3) - 1;
                    int var8 = y + random.nextInt(5) - 3;
                    int var9 = z + random.nextInt(3) - 1;
                    Block var10 = world.getBlock(var7, var8 + 1, var9);
                    if (world.getBlock(var7, var8, var9) == BOPTBlocks.holyDirt && world.getBlockLightValue(var7, var8 + 1, var9) >= 4 && var10.getLightOpacity() <= 2)
                        world.setBlock(var7, var8, var9, BOPTBlocks.holyGrass, 0, 2);
                }
            }
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        return BOPTBlocks.holyDirt.getItemDropped(0, random, fortune);
    }
}
