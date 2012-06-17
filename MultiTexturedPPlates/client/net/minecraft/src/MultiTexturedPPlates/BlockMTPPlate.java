package net.minecraft.src.MultiTexturedPPlates;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMobType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedPPlates;

public class BlockMTPPlate extends BlockContainer
{
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	Class mtPPlateEntityClass;
	
    public BlockMTPPlate(int par1, Class pPlateClass, float hardness, StepSound sound, boolean disableStats, boolean requiresSelfNotify)
    {
        super(par1, Material.circuits);
        mtPPlateEntityClass = pPlateClass;
        setHardness(hardness);
        setStepSound(sound);
        if (disableStats) { disableStats(); }
        if (requiresSelfNotify) { setRequiresSelfNotify(); }
        this.setTickRandomly(true);
        float f = 0.0625F;
        setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
    }
    
    public static int getDamageValue(IBlockAccess blockaccess, int x, int y, int z)
    {
    	TileEntity tileentity = blockaccess.getBlockTileEntity(x, y, z);
    	if (tileentity != null && tileentity instanceof TileEntityMTPPlate)
    	{
    		TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)tileentity;
    		return tileentitymtpplate.getMetaValue();
    	}
    	return 0;
    }
    
    public static int getMouseOver()
    {
    	if (mc.objectMouseOver != null)
    	{
        	int xPosition = mc.objectMouseOver.blockX;
        	int yPosition = mc.objectMouseOver.blockY;
        	int zPosition = mc.objectMouseOver.blockZ;
        	return getDamageValue(mc.theWorld, xPosition, yPosition, zPosition);
    	}
    	return 0;
    }
    
    public static int getBelowPlayer(EntityPlayer player)
    {
		int playerX = (int)player.posX;
		int playerY = (int)player.posY;
		int playerZ = (int)player.posZ;
    	return getDamageValue(mc.theWorld, playerX, playerY - 1, playerZ);
    }
    
    public static int getAtPlayer(EntityPlayer player)
    {
		int playerX = (int)player.posX;
		int playerY = (int)player.posY;
		int playerZ = (int)player.posZ;
    	return getDamageValue(mc.theWorld, playerX, playerY, playerZ);
    }
	
	@Override
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		int itemDamage = -1;
        TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
    	if (tileentitymtpplate != null)
    	{
    		switch(tileentitymtpplate.getMetaValue())
    		{
    		case 0:
    			itemDamage = 22;
    			break;
    		case 1:
    			itemDamage = 23;
    			break;
    		case 2:
    			itemDamage = 24;
    			break;
    		}
    	}
    	if (itemDamage != -1)
    	{
    		return itemDamage;
    	}
    	else return 1;
    }
    
	@Override
    public int getBlockTextureFromSideAndMetadata(int par0, int par1)
    {
		int itemDamage = -1;
		if (par0 != 0)
		{
			switch(par1)
			{
			case 0:
				itemDamage = 22;
				break;
			case 1:
				itemDamage = 23;
				break;
			case 2:
				itemDamage = 24;
				break;
			}
		}
		if (itemDamage == -1)
		{
			int texture = -1;
	    	EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
	    	if (player.onGround)
	    	{
	    		texture = getMouseOver();
	    	}
	    	if (texture == -1 && player.isAirBorne)
	    	{
	    		texture = getMouseOver();
	    	}
	    	if (texture == -1 && player.isAirBorne)
	    	{
	    		texture = getBelowPlayer(player);
	    	}
	    	if (texture == -1 && player.isAirBorne)
	    	{
	    		texture = getAtPlayer(player);
	    	}
	    	switch(texture)
	    	{
			case 0:
				itemDamage = 22;
				break;
			case 1:
				itemDamage = 23;
				break;
			case 2:
				itemDamage = 24;
				break;
	    	}
    	}
		if (itemDamage == -1) itemDamage = 22;
		return itemDamage;
    }

    public int tickRate()
    {
        return 20;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockSolidOnSide(i, j - 1, k, 1) || world.getBlockId(i, j - 1, k) == Block.fence.blockID;
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean flag = false;
        if (!world.isBlockSolidOnSide(i, j - 1, k, 1) && world.getBlockId(i, j - 1, k) != Block.fence.blockID)
        {
            flag = true;
        }
        if (flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if (world.isRemote)
        {
            return;
        }
        if (world.getBlockMetadata(i, j, k) == 0)
        {
            return;
        }
        else
        {
            setStateIfMobInteractsWithPlate(world, i, j, k);
            return;
        }
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if (world.isRemote)
        {
            return;
        }
        if (world.getBlockMetadata(i, j, k) == 1)
        {
            return;
        }
        else
        {
            setStateIfMobInteractsWithPlate(world, i, j, k);
            return;
        }
    }

    private void setStateIfMobInteractsWithPlate(World world, int i, int j, int k)
    {
    	TileEntity tileentity = world.getBlockTileEntity(i, j, k);
    	if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate))
		{
    		TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)tileentity;
            boolean flag = world.getBlockMetadata(i, j, k) == 1;
            boolean flag1 = false;
            float f = 0.125F;
            List list = null;
            if (tileentitymtpplate.getTriggerType() == 0)
            {
                list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
            }
            if (tileentitymtpplate.getTriggerType() == 1)
            {
                list = world.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
            }
            if (tileentitymtpplate.getTriggerType() == 2)
            {
                list = world.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
            }
            if (list.size() > 0)
            {
                flag1 = true;
            }
            if (flag1 && !flag)
            {
                world.setBlockMetadataWithNotify(i, j, k, 1);
                world.notifyBlocksOfNeighborChange(i, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
                world.markBlocksDirty(i, j, k, i, j, k);
                world.playSoundEffect((double)i + 0.5D, (double)j + 0.10000000000000001D, (double)k + 0.5D, "random.click", 0.3F, 0.6F);
            }
            if (!flag1 && flag)
            {
                world.setBlockMetadataWithNotify(i, j, k, 0);
                world.notifyBlocksOfNeighborChange(i, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
                world.markBlocksDirty(i, j, k, i, j, k);
                world.playSoundEffect((double)i + 0.5D, (double)j + 0.10000000000000001D, (double)k + 0.5D, "random.click", 0.3F, 0.5F);
            }
            if (flag1)
            {
                world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
            }
		}
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if (l > 0)
        {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
		ItemStack itemstack = new ItemStack(MTPCore.mtPPlate, 1, getDamageValue(world, i, j, k));
		EntityItem entityitem = new EntityItem(world, (float)i, (float)j, (float)k, new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
        world.spawnEntityInWorld(entityitem);
        super.onBlockRemoval(world, i, j, k);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        boolean flag = iblockaccess.getBlockMetadata(i, j, k) == 1;
        float f = 0.0625F;
        if (flag)
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        }
        else
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) > 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        if (world.getBlockMetadata(i, j, k) == 0)
        {
            return false;
        }
        else
        {
            return l == 1;
        }
    }

    public boolean canProvidePower()
    {
        return true;
    }

    public void setBlockBoundsForItemRender()
    {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;
        setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    public int getMobilityFlag()
    {
        return 1;
    }
    
    @Override
    public int quantityDropped(Random random)
    {
    	return 0;
    }
    
	@Override
	public TileEntity getBlockEntity()
	{
        try
        {
            return (TileEntity)mtPPlateEntityClass.newInstance();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
	}
}
