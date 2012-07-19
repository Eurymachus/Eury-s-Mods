package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.Block;
import net.minecraft.src.Direction;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class RenderBlockMTBed {
	public static boolean renderWorldBlock(RenderBlocks renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		return renderBlockMTBed(block, i,j, k, renderblocks, iblockaccess);
	}
	
	/**
     * render a bed at the given coordinates
     */
    public static boolean renderBlockMTBed(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
        Tessellator var5 = Tessellator.instance;
        int var7 = par1Block.getBedDirection(iblockaccess, par2, par3, par4);
        boolean var8 = par1Block.isBedFoot(iblockaccess, par2, par3, par4);
        float var9 = 0.5F;
        float var10 = 1.0F;
        float var11 = 0.8F;
        float var12 = 0.6F;
        int var25 = par1Block.getMixedBrightnessForBlock(iblockaccess, par2, par3, par4);
        var5.setBrightness(var25);
        var5.setColorOpaque_F(var9, var9, var9);
        int var27 = par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 0);
        int var28 = (var27 & 15) << 4;
        int var29 = var27 & 240;
        double var30 = (double)((float)var28 / 256.0F);
        double var32 = ((double)(var28 + 16) - 0.01D) / 256.0D;
        double var34 = (double)((float)var29 / 256.0F);
        double var36 = ((double)(var29 + 16) - 0.01D) / 256.0D;
        double var38 = (double)par2 + par1Block.minX;
        double var40 = (double)par2 + par1Block.maxX;
        double var42 = (double)par3 + par1Block.minY + 0.1875D;
        double var44 = (double)par4 + par1Block.minZ;
        double var46 = (double)par4 + par1Block.maxZ;
        var5.addVertexWithUV(var38, var42, var46, var30, var36);
        var5.addVertexWithUV(var38, var42, var44, var30, var34);
        var5.addVertexWithUV(var40, var42, var44, var32, var34);
        var5.addVertexWithUV(var40, var42, var46, var32, var36);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(iblockaccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var10, var10, var10);
        var27 = par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 1);
        var28 = (var27 & 15) << 4;
        var29 = var27 & 240;
        var30 = (double)((float)var28 / 256.0F);
        var32 = ((double)(var28 + 16) - 0.01D) / 256.0D;
        var34 = (double)((float)var29 / 256.0F);
        var36 = ((double)(var29 + 16) - 0.01D) / 256.0D;
        var38 = var30;
        var40 = var32;
        var42 = var34;
        var44 = var34;
        var46 = var30;
        double var48 = var32;
        double var50 = var36;
        double var52 = var36;

        if (var7 == 0)
        {
            var40 = var30;
            var42 = var36;
            var46 = var32;
            var52 = var34;
        }
        else if (var7 == 2)
        {
            var38 = var32;
            var44 = var36;
            var48 = var30;
            var50 = var34;
        }
        else if (var7 == 3)
        {
            var38 = var32;
            var44 = var36;
            var48 = var30;
            var50 = var34;
            var40 = var30;
            var42 = var36;
            var46 = var32;
            var52 = var34;
        }

        double var54 = (double)par2 + par1Block.minX;
        double var56 = (double)par2 + par1Block.maxX;
        double var58 = (double)par3 + par1Block.maxY;
        double var60 = (double)par4 + par1Block.minZ;
        double var62 = (double)par4 + par1Block.maxZ;
        var5.addVertexWithUV(var56, var58, var62, var46, var50);
        var5.addVertexWithUV(var56, var58, var60, var38, var42);
        var5.addVertexWithUV(var54, var58, var60, var40, var44);
        var5.addVertexWithUV(var54, var58, var62, var48, var52);
        var27 = Direction.headInvisibleFace[var7];

        if (var8)
        {
            var27 = Direction.headInvisibleFace[Direction.footInvisibleFaceRemap[var7]];
        }

        byte var64 = 4;

        switch (var7)
        {
            case 0:
                var64 = 5;
                break;
            case 1:
                var64 = 3;
            case 2:
            default:
                break;
            case 3:
                var64 = 2;
        }

        if (var27 != 2 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, par2, par3, par4 - 1, 2)))
        {
            var5.setBrightness(par1Block.minZ > 0.0D ? var25 : par1Block.getMixedBrightnessForBlock(iblockaccess, par2, par3, par4 - 1));
            var5.setColorOpaque_F(var11, var11, var11);
            renderblocks.flipTexture = var64 == 2;
            renderblocks.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 2));
        }

        if (var27 != 3 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, par2, par3, par4 + 1, 3)))
        {
            var5.setBrightness(par1Block.maxZ < 1.0D ? var25 : par1Block.getMixedBrightnessForBlock(iblockaccess, par2, par3, par4 + 1));
            var5.setColorOpaque_F(var11, var11, var11);
            renderblocks.flipTexture = var64 == 3;
            renderblocks.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 3));
        }

        if (var27 != 4 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, par2 - 1, par3, par4, 4)))
        {
            var5.setBrightness(par1Block.minZ > 0.0D ? var25 : par1Block.getMixedBrightnessForBlock(iblockaccess, par2 - 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            renderblocks.flipTexture = var64 == 4;
            renderblocks.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 4));
        }

        if (var27 != 5 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, par2 + 1, par3, par4, 5)))
        {
            var5.setBrightness(par1Block.maxZ < 1.0D ? var25 : par1Block.getMixedBrightnessForBlock(iblockaccess, par2 + 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            renderblocks.flipTexture = var64 == 5;
            renderblocks.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(iblockaccess, par2, par3, par4, 5));
        }

        renderblocks.flipTexture = false;
        return true;
    }
}
