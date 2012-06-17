package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;

public class RenderMTLever {
	public static boolean renderWorldBlock(RenderBlocks renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		if (l == MultiTexturedLevers.mtLeverBlockRenderID) {
			return renderLeverBlock(block, i, j, k, renderblocks, iblockaccess);
		} else
			return false;
	}

	public static boolean renderLeverBlock(Block par1Block, int par2, int par3,
			int par4, RenderBlocks renderblocks, IBlockAccess iblockaccess) {
		int var5 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) > 0;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = renderblocks.overrideBlockTexture >= 0;

		if (!var9) {
			renderblocks.overrideBlockTexture = par1Block.getBlockTexture(
					iblockaccess, par2, par3, par4, 0);
		}

		float var10 = 0.25F;
		float var11 = 0.1875F;
		float var12 = 0.1875F;

		if (var6 == 5) {
			par1Block.setBlockBounds(0.5F - var11, 0.0F, 0.5F - var10,
					0.5F + var11, var12, 0.5F + var10);
		} else if (var6 == 6) {
			par1Block.setBlockBounds(0.5F - var10, 0.0F, 0.5F - var11,
					0.5F + var10, var12, 0.5F + var11);
		} else if (var6 == 4) {
			par1Block.setBlockBounds(0.5F - var11, 0.5F - var10, 1.0F - var12,
					0.5F + var11, 0.5F + var10, 1.0F);
		} else if (var6 == 3) {
			par1Block.setBlockBounds(0.5F - var11, 0.5F - var10, 0.0F,
					0.5F + var11, 0.5F + var10, var12);
		} else if (var6 == 2) {
			par1Block.setBlockBounds(1.0F - var12, 0.5F - var10, 0.5F - var11,
					1.0F, 0.5F + var10, 0.5F + var11);
		} else if (var6 == 1) {
			par1Block.setBlockBounds(0.0F, 0.5F - var10, 0.5F - var11, var12,
					0.5F + var10, 0.5F + var11);
		}

		renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

		if (!var9) {
			renderblocks.overrideBlockTexture = -1;
		}

		var8.setBrightness(par1Block.getMixedBrightnessForBlock(
				renderblocks.blockAccess, par2, par3, par4));
		float var13 = 1.0F;

		if (Block.lightValue[par1Block.blockID] > 0) {
			var13 = 1.0F;
		}

		var8.setColorOpaque_F(var13, var13, var13);
		int var14 = par1Block.getBlockTextureFromSide(0);

		if (renderblocks.overrideBlockTexture >= 0) {
			var14 = renderblocks.overrideBlockTexture;
		}

		int var15 = (var14 & 15) << 4;
		int var16 = var14 & 240;
		float var17 = var15 / 256.0F;
		float var18 = (var15 + 15.99F) / 256.0F;
		float var19 = var16 / 256.0F;
		float var20 = (var16 + 15.99F) / 256.0F;
		Vec3D[] var21 = new Vec3D[8];
		float var22 = 0.0625F;
		float var23 = 0.0625F;
		float var24 = 0.625F;
		var21[0] = Vec3D.createVector((-var22), 0.0D,
				(-var23));
		var21[1] = Vec3D.createVector(var22, 0.0D, (-var23));
		var21[2] = Vec3D.createVector(var22, 0.0D, var23);
		var21[3] = Vec3D.createVector((-var22), 0.0D, var23);
		var21[4] = Vec3D.createVector((-var22), var24,
				(-var23));
		var21[5] = Vec3D.createVector(var22, var24,
				(-var23));
		var21[6] = Vec3D.createVector(var22, var24,
				var23);
		var21[7] = Vec3D.createVector((-var22), var24,
				var23);

		for (int var25 = 0; var25 < 8; ++var25) {
			if (var7) {
				var21[var25].zCoord -= 0.0625D;
				var21[var25].rotateAroundX(((float) Math.PI * 2F / 9F));
			} else {
				var21[var25].zCoord += 0.0625D;
				var21[var25].rotateAroundX(-((float) Math.PI * 2F / 9F));
			}

			if (var6 == 6) {
				var21[var25].rotateAroundY(((float) Math.PI / 2F));
			}

			if (var6 < 5) {
				var21[var25].yCoord -= 0.375D;
				var21[var25].rotateAroundX(((float) Math.PI / 2F));

				if (var6 == 4) {
					var21[var25].rotateAroundY(0.0F);
				}

				if (var6 == 3) {
					var21[var25].rotateAroundY((float) Math.PI);
				}

				if (var6 == 2) {
					var21[var25].rotateAroundY(((float) Math.PI / 2F));
				}

				if (var6 == 1) {
					var21[var25].rotateAroundY(-((float) Math.PI / 2F));
				}

				var21[var25].xCoord += par2 + 0.5D;
				var21[var25].yCoord += (par3 + 0.5F);
				var21[var25].zCoord += par4 + 0.5D;
			} else {
				var21[var25].xCoord += par2 + 0.5D;
				var21[var25].yCoord += (par3 + 0.125F);
				var21[var25].zCoord += par4 + 0.5D;
			}
		}

		Vec3D var30 = null;
		Vec3D var26 = null;
		Vec3D var27 = null;
		Vec3D var28 = null;

		for (int var29 = 0; var29 < 6; ++var29) {
			if (var29 == 0) {
				var17 = (var15 + 7) / 256.0F;
				var18 = ((var15 + 9) - 0.01F) / 256.0F;
				var19 = (var16 + 6) / 256.0F;
				var20 = ((var16 + 8) - 0.01F) / 256.0F;
			} else if (var29 == 2) {
				var17 = (var15 + 7) / 256.0F;
				var18 = ((var15 + 9) - 0.01F) / 256.0F;
				var19 = (var16 + 6) / 256.0F;
				var20 = ((var16 + 16) - 0.01F) / 256.0F;
			}

			if (var29 == 0) {
				var30 = var21[0];
				var26 = var21[1];
				var27 = var21[2];
				var28 = var21[3];
			} else if (var29 == 1) {
				var30 = var21[7];
				var26 = var21[6];
				var27 = var21[5];
				var28 = var21[4];
			} else if (var29 == 2) {
				var30 = var21[1];
				var26 = var21[0];
				var27 = var21[4];
				var28 = var21[5];
			} else if (var29 == 3) {
				var30 = var21[2];
				var26 = var21[1];
				var27 = var21[5];
				var28 = var21[6];
			} else if (var29 == 4) {
				var30 = var21[3];
				var26 = var21[2];
				var27 = var21[6];
				var28 = var21[7];
			} else if (var29 == 5) {
				var30 = var21[0];
				var26 = var21[3];
				var27 = var21[7];
				var28 = var21[4];
			}

			var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord,
					var17, var20);
			var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord,
					var18, var20);
			var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord,
					var18, var19);
			var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord,
					var17, var19);
		}

		return true;
	}
}
