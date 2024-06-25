package mods.tesseract.bopt;

import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BOPBlock;
import biomesoplenty.common.blocks.BlockBOPGeneric;
import biomesoplenty.common.blocks.BlockBOPGrass;
import biomesoplenty.common.core.BOPBlocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.tesseract.bopt.blocks.BlockBOPTSkystone;
import mods.tesseract.bopt.itemblocks.ItemBlockSkystone;
import mods.tesseract.bopt.world.WorldProviderPromised;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.DimensionManager;

@Mod(modid = "bopt", dependencies = "required-after:BiomesOPlenty", acceptedMinecraftVersions = "[1.7.10]")
public class BOPTweaks {
    public static String greeting;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        BOPTBlocks.init();
        BOPTBiomes.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        BOPCBiomes.lushDesert.fillerBlock = Blocks.dirt;
        DimensionManager.registerProviderType(20, WorldProviderPromised.class, false);
        DimensionManager.registerDimension(20, 20);
    }

}
