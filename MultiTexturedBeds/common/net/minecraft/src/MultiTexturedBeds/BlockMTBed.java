package net.minecraft.src.MultiTexturedBeds;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.Direction;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.IContainer;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTBed extends BlockBed implements ITextureProvider,
		IContainer {
	Class mtBedEntityClass;

	public BlockMTBed(int par1, Class bedClass, float hardness,
			boolean disableStats, boolean requiresSelfNotify) {
		super(par1);
		this.blockIndexInTexture = 1;
		this.isBlockContainer = true;
		this.mtBedEntityClass = bedClass;
		setHardness(hardness);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
	}

	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z,
			int l) {
		int index = 0;
		switch (MultiTexturedBeds.getDamageValue(blockAccess, x, y, z)) {
		case 0:
			index = 1;
			break;
		case 1:
			index = 5;
			break;
		case 2:
			index = 7;
			break;
		default:
			index = 1;
			break;
		}
		return this.getBlockTextureFromSideAndMetadata(l,
				blockAccess.getBlockMetadata(x, y, z), index);
	}

	/**
	 * Called upon block activation (left or right click on the block.). The
	 * three integers represent x,y,z of the block.
	 */
	public boolean blockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		} else {
			int var6 = par1World.getBlockMetadata(par2, par3, par4);

			if (!isBlockFootOfBed(var6)) {
				int var7 = getDirection(var6);
				par2 += headBlockToFootBlockMap[var7][0];
				par4 += headBlockToFootBlockMap[var7][1];

				if (par1World.getBlockId(par2, par3, par4) != this.blockID) {
					return true;
				}

				var6 = par1World.getBlockMetadata(par2, par3, par4);
			}

			if (!par1World.worldProvider.canRespawnHere()) {
				double var16 = par2 + 0.5D;
				double var17 = par3 + 0.5D;
				double var11 = par4 + 0.5D;
				par1World.setBlockWithNotify(par2, par3, par4, 0);
				int var13 = getDirection(var6);
				par2 += headBlockToFootBlockMap[var13][0];
				par4 += headBlockToFootBlockMap[var13][1];

				if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
					par1World.setBlockWithNotify(par2, par3, par4, 0);
					var16 = (var16 + par2 + 0.5D) / 2.0D;
					var17 = (var17 + par3 + 0.5D) / 2.0D;
					var11 = (var11 + par4 + 0.5D) / 2.0D;
				}

				par1World.newExplosion((Entity) null, (par2 + 0.5F),
						(par3 + 0.5F), (par4 + 0.5F), 5.0F, true);
				return true;
			} else {
				if (isBedOccupied(var6)) {
					EntityPlayer var14 = null;
					Iterator var8 = par1World.playerEntities.iterator();

					while (var8.hasNext()) {
						EntityPlayer var9 = (EntityPlayer) var8.next();

						if (var9.isPlayerSleeping()) {
							ChunkCoordinates var10 = var9.playerLocation;

							if (var10.posX == par2 && var10.posY == par3
									&& var10.posZ == par4) {
								var14 = var9;
							}
						}
					}

					if (var14 != null) {
						par5EntityPlayer.addChatMessage("tile.bed.occupied");
						return true;
					}

					setBedOccupied(par1World, par2, par3, par4, false);
				}

				EnumStatus var15 = par5EntityPlayer.sleepInBedAt(par2, par3,
						par4);

				if (var15 == EnumStatus.OK) {
					setBedOccupied(par1World, par2, par3, par4, true);
					return true;
				} else {
					if (var15 == EnumStatus.NOT_POSSIBLE_NOW) {
						par5EntityPlayer.addChatMessage("tile.bed.noSleep");
					} else if (var15 == EnumStatus.NOT_SAFE) {
						par5EntityPlayer.addChatMessage("tile.bed.notSafe");
					}

					return true;
				}
			}
		}
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2, int par3) {
		int index = par3;
		if (par1 == 0) {
			return Block.planks.blockIndexInTexture;
		} else {
			int var3 = getDirection(par2);
			int var4 = Direction.bedDirection[var3][par1];
			return isBlockFootOfBed(par2) ? (var4 == 2 ? index + 2 + 16
					: (var4 != 5 && var4 != 4 ? index + 1 : index + 1 + 16))
					: (var4 == 3 ? index - 1 + 16
							: (var4 != 5 && var4 != 4 ? index : index + 16));
		}
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType() {
		return MultiTexturedBeds.mtBedRenderID;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3,
			int par4, int par5, float par6, int par7) {
		if (!isBlockFootOfBed(par5)) {
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5,
					par6, 0);
		}
	}

	@Override
	public String getTextureFile() {
		return MultiTexturedBeds.MTBed.getBlockSheet();
	}

	@Override
	public TileEntity getTileEntity(int meta) {
		return getBlockEntity(meta);
	}

	@Override
	public TileEntity getBlockEntity(int meta) {
		return getBlockEntity();
	}

	@Override
	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtBedEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}
