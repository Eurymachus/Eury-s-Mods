package net.minecraft.src.MultiTexturedPPlates;

import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockPressurePlate;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.IContainer;

public class BlockMTPPlate extends BlockPressurePlate implements IContainer {
	Class mtPPlateEntityClass;

	public BlockMTPPlate(int par1, Class pPlateClass, float hardness,
			StepSound sound, boolean disableStats, boolean requiresSelfNotify) {
		super(par1, 0, null, Material.circuits);
		this.isBlockContainer = true;
		mtPPlateEntityClass = pPlateClass;
		setHardness(hardness);
		setStepSound(sound);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
	}

	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4, int par5) {
		switch (MultiTexturedPPlates.getDamageValue(par1IBlockAccess, par2,
				par3, par4)) {
		case 0:
			return 22;
		case 1:
			return 23;
		case 2:
			return 24;
		default:
			return 22;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par0, int par1) {
		return MultiTexturedPPlates.getTextureFromMetaData(par1);
	}

	private void setStateIfMobInteractsWithMTPlate(World world, int i, int j,
			int k) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate)) {
			TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
			boolean flag = world.getBlockMetadata(i, j, k) == 1;
			boolean flag1 = false;
			float f = 0.125F;
			List list = null;
			if (tileentitymtpplate.getTriggerType() == 0) {
				list = world.getEntitiesWithinAABBExcludingEntity(null,
						AxisAlignedBB.getBoundingBoxFromPool(i + f, j, k + f,
								(i + 1) - f, j + 0.25D, (k + 1) - f));
			}
			if (tileentitymtpplate.getTriggerType() == 1) {
				list = world.getEntitiesWithinAABB(
						net.minecraft.src.EntityLiving.class, AxisAlignedBB
								.getBoundingBoxFromPool(i + f, j, k + f,
										(i + 1) - f, j + 0.25D, (k + 1) - f));
			}
			if (tileentitymtpplate.getTriggerType() == 2) {
				list = world.getEntitiesWithinAABB(
						net.minecraft.src.EntityPlayer.class, AxisAlignedBB
								.getBoundingBoxFromPool(i + f, j, k + f,
										(i + 1) - f, j + 0.25D, (k + 1) - f));
			}
			if (list.size() > 0) {
				flag1 = true;
			}
			if (flag1 && !flag) {
				world.setBlockMetadataWithNotify(i, j, k, 1);
				world.notifyBlocksOfNeighborChange(i, j, k, blockID);
				world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
				world.markBlocksDirty(i, j, k, i, j, k);
				world.playSoundEffect(i + 0.5D, j + 0.10000000000000001D,
						k + 0.5D, "random.click", 0.3F, 0.6F);
			}
			if (!flag1 && flag) {
				world.setBlockMetadataWithNotify(i, j, k, 0);
				world.notifyBlocksOfNeighborChange(i, j, k, blockID);
				world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
				world.markBlocksDirty(i, j, k, i, j, k);
				world.playSoundEffect(i + 0.5D, j + 0.10000000000000001D,
						k + 0.5D, "random.click", 0.3F, 0.5F);
			}
			if (flag1) {
				world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
			}
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		if (!par1World.isRemote) {
			if (par1World.getBlockMetadata(par2, par3, par4) != 0) {
				this.setStateIfMobInteractsWithMTPlate(par1World, par2, par3,
						par4);
			}
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		if (!par1World.isRemote) {
			if (par1World.getBlockMetadata(par2, par3, par4) != 1) {
				this.setStateIfMobInteractsWithMTPlate(par1World, par2, par3,
						par4);
			}
		}
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		ItemStack itemstack = new ItemStack(MTPCore.mtPPlate, 1,
				MultiTexturedPPlates.getDamageValue(world, i, j, k));
		EntityItem entityitem = new EntityItem(world, i, j, k, new ItemStack(
				itemstack.itemID, 1, itemstack.getItemDamage()));
		world.spawnEntityInWorld(entityitem);
		super.onBlockRemoval(world, i, j, k);
		world.removeBlockTileEntity(i, j, k);
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public TileEntity getTileEntity(int meta) {
		return getBlockEntity(meta);
	}

	@Override
	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtPPlateEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public TileEntity getBlockEntity(int meta) {
		return getBlockEntity();
	}
}
