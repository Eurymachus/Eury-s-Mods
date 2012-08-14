package net.minecraft.src.MultiTexturedDoors;

import java.util.Random;

import net.minecraft.src.BlockDoor;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.IContainer;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTDoor extends BlockDoor implements ITextureProvider,
		IContainer {
	Class mtDoorEntityClass;

	public BlockMTDoor(int par1, Class doorClass, float hardness,
			StepSound sound, boolean disableStats, boolean requiresSelfNotify,
			String blockName) {
		super(par1, Material.rock);
		this.setBlockName("mtDoor");
		this.isBlockContainer = true;
		this.blockIndexInTexture = 1;
		mtDoorEntityClass = doorClass;
		setHardness(hardness);
		setStepSound(sound);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
		float var3 = 0.5F;
		float var4 = 1.0F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4,
				0.5F + var3);
	}

	@Override
	public TileEntity getTileEntity(int meta) {
		return getBlockEntity();
	}

	/**
	 * Metadata-sensitive version, to fix 1.8.1 regression.
	 * 
	 * @param meta
	 *            The current Metadata
	 * @return And instance of the TileEntity class for this block
	 */
	@Override
	public TileEntity getBlockEntity(int meta) {
		return getBlockEntity();
	}

	@Override
	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtDoorEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		par1World.setBlockTileEntity(par2, par3, par4, this
				.getBlockEntity(par1World.getBlockMetadata(par2, par3, par4)));
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args:
	 * iBlockAccess, x, y, z, side
	 */
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y,
			int z, int side) {
		int index = this.blockIndexInTexture;
		int staticIndex = index;
		switch (MultiTexturedDoors.getDamageValue(par1IBlockAccess, x, y, z)) {
		case 0:
			index = 17;
			staticIndex = 17;
			break;
		case 1:
			index = 18;
			staticIndex = 18;
			break;
		case 2:
			index = 19;
			staticIndex = 19;
			break;
		}
		if (side != 0 && side != 1) {
			int fullMeta = this.getFullMetadata(par1IBlockAccess, x, y, z);
			if ((fullMeta & 8) != 0) {
				index -= 16;
			}

			int var8 = fullMeta & 3;
			boolean var9 = (fullMeta & 4) != 0;

			if (!var9) {
				if (var8 == 0 && side == 5) {
					index = -index;
				} else if (var8 == 1 && side == 3) {
					index = -index;
				} else if (var8 == 2 && side == 4) {
					index = -index;
				} else if (var8 == 3 && side == 2) {
					index = -index;
				}

				if ((fullMeta & 16) != 0) {
					index = -index;
				}
			} else if (var8 == 0 && side == 2) {
				index = -index;
			} else if (var8 == 1 && side == 5) {
				index = -index;
			} else if (var8 == 2 && side == 3) {
				index = -index;
			} else if (var8 == 3 && side == 4) {
				index = -index;
			}

			return index;
		} else {
			return staticIndex;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		int texture = -1;
		EntityPlayer entityplayer = MultiTexturedDoors.getPlayer();
		if (entityplayer.onGround) {
			texture = MultiTexturedDoors.getMouseOver();
		}
		if (texture == -1 && entityplayer.isAirBorne) {
			texture = MultiTexturedDoors.getMouseOver();
		}
		if (texture == -1 && entityplayer.isAirBorne) {
			texture = MultiTexturedDoors.getBelowPlayer(entityplayer);
		}
		if (texture == -1 && entityplayer.isAirBorne) {
			texture = MultiTexturedDoors.getAtPlayer(entityplayer);
		}
		return texture + 1;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return MultiTexturedDoors.mtDoorBlockRenderID;
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if (tileentity != null && tileentity instanceof TileEntityMTDoor) {
			int doorPiece = ((TileEntityMTDoor) tileentity).getDoorPiece();
			if (doorPiece == 0) {
				int door = ((TileEntityMTDoor) tileentity).getTextureValue();
				ItemStack itemstack = new ItemStack(MTDCore.mtDoorItem, 1, door);
				EntityItem entityitem = new EntityItem(world, i, j, k,
						new ItemStack(itemstack.itemID, 1,
								itemstack.getItemDamage()));
				world.spawnEntityInWorld(entityitem);
			}
			super.onBlockRemoval(world, i, j, k);
			world.removeBlockTileEntity(i, j, k);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public String getTextureFile() {
		return MultiTexturedDoors.Core.getBlockSheet();
	}
}
