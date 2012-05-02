package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;


public class MTDCore
{
	public static String version = "v1.0";
	
	public static void initialize()
    {
		new MultiTexturedDoors().initialize();
    }
	
	public static boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
	{
    	if (l == MultiTexturedDoors.mtDoorBlockRenderID)
    	{
    		return renderDoorBlock(block, i, j, k, renderblocks, iblockaccess);
    	}
    	else return false;
	}
    
    public static boolean renderDoorBlock(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
    	Tessellator var5 = Tessellator.instance;
        BlockMTDoor var6 = (BlockMTDoor)par1Block;
        boolean var7 = false;
        float var8 = 0.5F;
        float var9 = 1.0F;
        float var10 = 0.8F;
        float var11 = 0.6F;
        int var12 = par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4);
        var5.setBrightness(par1Block.minY > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
        var5.setColorOpaque_F(var8, var8, var8);
        renderblocks.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 0));
        var7 = true;
        var5.setBrightness(par1Block.maxY < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var9, var9, var9);
        renderblocks.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 1));
        var7 = true;
        var5.setBrightness(par1Block.minZ > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1));
        var5.setColorOpaque_F(var10, var10, var10);
        int var13 = par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 2);

        if (var13 < 0)
        {
            renderblocks.flipTexture = true;
            var13 = -var13;
        }

        renderblocks.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var13);
        var7 = true;
        renderblocks.flipTexture = false;
        var5.setBrightness(par1Block.maxZ < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1));
        var5.setColorOpaque_F(var10, var10, var10);
        var13 = par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 3);

        if (var13 < 0)
        {
            renderblocks.flipTexture = true;
            var13 = -var13;
        }

        renderblocks.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var13);
        var7 = true;
        renderblocks.flipTexture = false;
        var5.setBrightness(par1Block.minX > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 4);

        if (var13 < 0)
        {
            renderblocks.flipTexture = true;
            var13 = -var13;
        }

        renderblocks.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var13);
        var7 = true;
        renderblocks.flipTexture = false;
        var5.setBrightness(par1Block.maxX < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = par1Block.getBlockTexture(renderblocks.blockAccess, par2, par3, par4, 5);

        if (var13 < 0)
        {
            renderblocks.flipTexture = true;
            var13 = -var13;
        }

        renderblocks.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var13);
        var7 = true;
        renderblocks.flipTexture = false;
        return var7;
	}
}
