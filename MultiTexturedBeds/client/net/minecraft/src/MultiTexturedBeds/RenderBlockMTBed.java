package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.Block;
import net.minecraft.src.Direction;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class RenderBlockMTBed {
	public static boolean renderWorldBlock(RenderBlocks renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		if (block.blockID == MTBedsCore.mtBlockBedID) {
			return renderBlockMTBed(block, i, j, k, renderblocks, iblockaccess);
		}
		return false;
	}
	
	public static boolean renderBlockMTBed(Block par1Block, int x, int y,
			int z, RenderBlocks renderblocks, IBlockAccess iblockaccess) {
		Tessellator tessellator = Tessellator.instance;
		BlockMTBed mtBed = (BlockMTBed)par1Block;
		
		int bedDirection = par1Block.getBedDirection(iblockaccess, x, y, z);
		
		boolean isBedFoot = par1Block.isBedFoot(iblockaccess, x, y, z);
		
		float var9 = 0.5F;
		float var10 = 1.0F;
		float var11 = 0.8F;
		float var12 = 0.6F;
		
		int mixedBrightness = par1Block.getMixedBrightnessForBlock(iblockaccess, x, y, z);
		
		tessellator.setBrightness(mixedBrightness);
		tessellator.setColorOpaque_F(var9, var9, var9);
		
		int blockTexture = par1Block.getBlockTexture(iblockaccess, x, y, z, 0);
		
		int var28 = (blockTexture & 15) << 4;		
		int var29 = blockTexture & 240;
		
		double var30 = (var28 / 256.0F);
		double var32 = ((var28 + 16) - 0.01D) / 256.0D;
		double var34 = (var29 / 256.0F);
		double var36 = ((var29 + 16) - 0.01D) / 256.0D;
		double var38 = x + mtBed.minX;
		double var40 = x + mtBed.maxX;
		double var42 = y + mtBed.minY + 0.1875D;
		double var44 = z + mtBed.minZ;
		double var46 = z + mtBed.maxZ;
		
		tessellator.addVertexWithUV(var38, var42, var46, var30, var36);
		tessellator.addVertexWithUV(var38, var42, var44, var30, var34);
		tessellator.addVertexWithUV(var40, var42, var44, var32, var34);
		tessellator.addVertexWithUV(var40, var42, var46, var32, var36);
		tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(iblockaccess, x, y + 1, z));
		tessellator.setColorOpaque_F(var10, var10, var10);
		int rotation = renderblocks.uvRotateTop;
		if (bedDirection == 0) {
			renderblocks.uvRotateTop = 1;
		} else if (bedDirection == 1) {
			renderblocks.uvRotateTop = 3;
		} else if (bedDirection == 2) {
			renderblocks.uvRotateTop = 2;
		} else if (bedDirection == 3) {
			renderblocks.uvRotateTop = 4;
		}
		renderblocks.renderTopFace(par1Block, x, y, z, par1Block
				.getBlockTexture(renderblocks.blockAccess, x, y,
						z, 1));
		renderblocks.uvRotateTop = rotation;
		blockTexture = Direction.headInvisibleFace[bedDirection];

		if (isBedFoot) {
			blockTexture = Direction.headInvisibleFace[Direction.footInvisibleFaceRemap[bedDirection]];
		}

		byte var64 = 4;

		switch (bedDirection) {
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

		if (blockTexture != 2 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, x, y,	z - 1, 2))) {
			tessellator.setBrightness(par1Block.minZ > 0.0D ? mixedBrightness : par1Block.getMixedBrightnessForBlock(iblockaccess, x, y, z - 1));
			tessellator.setColorOpaque_F(var11, var11, var11);
			renderblocks.flipTexture = var64 == 2;
			renderblocks.renderEastFace(par1Block, x, y, z, par1Block.getBlockTexture(iblockaccess, x, y, z, 2));
		}

		if (blockTexture != 3 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, x, y,	z + 1, 3))) {
			tessellator.setBrightness(par1Block.maxZ < 1.0D ? mixedBrightness : par1Block.getMixedBrightnessForBlock(iblockaccess, x, y, z + 1));
			tessellator.setColorOpaque_F(var11, var11, var11);
			renderblocks.flipTexture = var64 == 3;
			renderblocks.renderWestFace(par1Block, x, y, z, par1Block.getBlockTexture(iblockaccess, x, y, z, 3));
		}

		if (blockTexture != 4 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, x - 1, y, z, 4))) {
			tessellator.setBrightness(par1Block.minZ > 0.0D ? mixedBrightness : par1Block.getMixedBrightnessForBlock(iblockaccess, x - 1, y, z));
			tessellator.setColorOpaque_F(var12, var12, var12);
			renderblocks.flipTexture = var64 == 4;
			renderblocks.renderNorthFace(par1Block, x, y, z, par1Block.getBlockTexture(iblockaccess, x, y, z, 4));
		}

		if (blockTexture != 5 && (renderblocks.renderAllFaces || par1Block.shouldSideBeRendered(iblockaccess, x + 1, y, z, 5))) {
			tessellator.setBrightness(par1Block.maxZ < 1.0D ? mixedBrightness : par1Block.getMixedBrightnessForBlock(iblockaccess, x + 1, y, z));
			tessellator.setColorOpaque_F(var12, var12, var12);
			renderblocks.flipTexture = var64 == 5;
			renderblocks.renderSouthFace(par1Block, x, y, z, par1Block.getBlockTexture(iblockaccess, x, y, z, 5));
		}

		renderblocks.flipTexture = false;
		return true;
	}
}
