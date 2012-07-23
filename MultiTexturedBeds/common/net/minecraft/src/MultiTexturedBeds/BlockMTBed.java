package net.minecraft.src.MultiTexturedBeds;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.Direction;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.IContainer;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTBed extends BlockBed implements ITextureProvider,
		IContainer {
	Class mtBedEntityClass;
	private final int[] bedTextureIDs = {
			1,5,9,13,
			33,37,41,45,
			65,69,73,77,
			97,101,105,109
			};

	public BlockMTBed(int par1, Class bedClass, float hardness,
			boolean disableStats, boolean requiresSelfNotify) {
		super(par1);
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

	public int getBedTextureID(int index) {
		if (index >= 0 && index <= this.bedTextureIDs.length) { 
			return this.bedTextureIDs[index];
		}
		return 0;
	}

	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z,
			int l) {
		int index = MultiTexturedBeds.getDamageValue(blockAccess, x, y, z);
		return this.getBlockTextureFromSideAndMetadataAndDamage(l,
				blockAccess.getBlockMetadata(x, y, z), bedTextureIDs[index]);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadataAndDamage(int side, int metadata, int damage) {
		int index = damage;
		if (side == 0) {
			return getBlockTextureFromSideAndMetadata(side, metadata);
		} else {
			int direction = getDirection(metadata);
			int theDirection = Direction.bedDirection[direction][side];
			return isBlockFootOfBed(metadata) ? (theDirection == 2 ? index + 2 + 16
					: (theDirection != 5 && theDirection != 4 ? index + 1 : index + 1 + 16))
					: (theDirection == 3 ? index - 1 + 16
							: (theDirection != 5 && theDirection != 4 ? index : index + 16));
		}
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return this.getBedTextureID(MultiTexturedBeds.getBlockTextureFromSideAndMetadata(side, metadata));
	}
	
	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		if (!MultiTexturedBeds.isBlockFootOfBed(world, x, y, z)) {
			ItemStack itemstack = new ItemStack(MTBedsCore.mtItemBed, 1,
			MultiTexturedBeds.getDamageValue(world, x, y, z));
			EntityItem entityitem = new EntityItem(world, x, y, z, itemstack);
			world.spawnEntityInWorld(entityitem);
		}
		world.removeBlockTileEntity(x, y, z);
		super.onBlockRemoval(world, x, y, z);
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
    
    @Override
    public boolean isBed(World world, int x, int y, int z, EntityLiving player)
    {
        return true;
    }
    
    @Override
    public ChunkCoordinates getBedSpawnPosition(World world, int x, int y, int z, EntityPlayer player)
    {
        return super.getNearestEmptyChunkCoordinates(world, x, y, z, 0);
    }

    @Override
    public void setBedOccupied(World world, int x, int y, int z, EntityPlayer player, boolean occupied)
    {
        super.setBedOccupied(world, x, y, z, occupied);        
    }

    @Override
    public int getBedDirection(IBlockAccess world, int x, int y, int z) 
    {
        return super.getDirection(world.getBlockMetadata(x, y, z));
    }
    
    @Override
    public boolean isBedFoot(IBlockAccess world, int x, int y, int z)
    {
        return super.isBlockFootOfBed(world.getBlockMetadata(x, y, z));
    }

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3,
			int par4, int par5, float par6, int par7) {
		if (!isBlockFootOfBed(par5)) {
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5,
					par6, 0);
		}
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

	@Override
	public String getTextureFile() {
		return MultiTexturedBeds.MTBed.getBlockSheet();
	}
}
