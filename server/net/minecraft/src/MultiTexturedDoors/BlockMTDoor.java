package net.minecraft.src.MultiTexturedDoors;

import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMobType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTDoor extends BlockContainer
{
	Class mtDoorEntityClass;
	
    public BlockMTDoor(int par1, Class doorClass, float hardness, StepSound sound, boolean disableStats, boolean requiresSelfNotify)
    {
        super(par1, Material.rock);
        mtDoorEntityClass = doorClass;
        setHardness(hardness);
        setStepSound(sound);
        if (disableStats) { disableStats(); }
        if (requiresSelfNotify) { setRequiresSelfNotify(); }
        float var3 = 0.5F;
        float var4 = 1.0F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		int indexInTexture = -1;
        TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
    	if (tileentitymtdoor != null)
    	{
    		switch(tileentitymtdoor.getMetaValue())
    		{
    		case 0:
    			indexInTexture = 16;
    			break;
    		case 1:
    			indexInTexture = 17;
    			break;
    		case 2:
    			indexInTexture = 18;
    			break;
    		}
    	}
    	if (indexInTexture != -1)
    	{
    		if (par5 != 0 && par5 != 1)
	        {
	            int var6 = this.func_48212_i(par1IBlockAccess, par2, par3, par4);
	            int var7 = indexInTexture;
	
	            if ((var6 & 8) != 0)
	            {
	                var7 -= 16;
	            }
	
	            int var8 = var6 & 3;
	            boolean var9 = (var6 & 4) != 0;
	
	            if (!var9)
	            {
	                if (var8 == 0 && par5 == 5)
	                {
	                    var7 = -var7;
	                }
	                else if (var8 == 1 && par5 == 3)
	                {
	                    var7 = -var7;
	                }
	                else if (var8 == 2 && par5 == 4)
	                {
	                    var7 = -var7;
	                }
	                else if (var8 == 3 && par5 == 2)
	                {
	                    var7 = -var7;
	                }
	
	                if ((var6 & 16) != 0)
	                {
	                    var7 = -var7;
	                }
	            }
	            else if (var8 == 0 && par5 == 2)
	            {
	                var7 = -var7;
	            }
	            else if (var8 == 1 && par5 == 5)
	            {
	                var7 = -var7;
	            }
	            else if (var8 == 2 && par5 == 3)
	            {
	                var7 = -var7;
	            }
	            else if (var8 == 3 && par5 == 4)
	            {
	                var7 = -var7;
	            }
	
	            return var7;
	        }
	        else
	        {
	            return indexInTexture;
	        }
    	}
    	else
    	{
    		return 4;
    	}
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = this.func_48212_i(par1IBlockAccess, par2, par3, par4);
        return (var5 & 4) != 0;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setDoorRotation(this.func_48212_i(par1IBlockAccess, par2, par3, par4));
    }

    /**
     * Returns 0, 1, 2 or 3 depending on where the hinge is.
     */
    public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.func_48212_i(par1IBlockAccess, par2, par3, par4) & 3;
    }

    public boolean func_48213_h(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return (this.func_48212_i(par1IBlockAccess, par2, par3, par4) & 4) != 0;
    }

    private void setDoorRotation(int par1)
    {
        float var2 = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int var3 = par1 & 3;
        boolean var4 = (par1 & 4) != 0;
        boolean var5 = (par1 & 16) != 0;

        if (var3 == 0)
        {
            if (!var4)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (var3 == 1)
        {
            if (!var4)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
            else if (!var5)
            {
                this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
        else if (var3 == 2)
        {
            if (!var4)
            {
                this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
        }
        else if (var3 == 3)
        {
            if (!var4)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
            else
            {
                this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (this.blockMaterial == Material.iron)
        {
            return false;
        }
        else
        {
            int var6 = this.func_48212_i(par1World, par2, par3, par4);
            int var7 = var6 & 7;
            var7 ^= 4;

            if ((var6 & 8) != 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var7);
                par1World.markBlocksDirty(par2, par3 - 1, par4, par2, par3, par4);
            }
            else
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var7);
                par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            }

            par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
            return true;
        }
    }

    /**
     * A function to open a door.
     */
    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int var6 = this.func_48212_i(par1World, par2, par3, par4);
        boolean var7 = (var6 & 4) != 0;

        if (var7 != par5)
        {
            int var8 = var6 & 7;
            var8 ^= 4;

            if ((var6 & 8) != 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var8);
                par1World.markBlocksDirty(par2, par3 - 1, par4, par2, par3, par4);
            }
            else
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var8);
                par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            }

            par1World.playAuxSFXAtEntity((EntityPlayer)null, 1003, par2, par3, par4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);

        if ((var6 & 8) != 0)
        {
            if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }

            if (par5 > 0 && par5 != this.blockID)
            {
                this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
            }
        }
        else
        {
            boolean var7 = false;

            if (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1))
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                var7 = true;

                if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID)
                {
                    par1World.setBlockWithNotify(par2, par3 + 1, par4, 0);
                }
            }

            if (var7)
            {
                if (!par1World.isRemote)
                {
                    this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
                }
            }
            else
            {
                boolean var8 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

                if ((var8 || par5 > 0 && Block.blocksList[par5].canProvidePower() || par5 == 0) && par5 != this.blockID)
                {
                    this.onPoweredBlockChange(par1World, par2, par3, par4, var8);
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
/*    public int idDropped(int par1, Random par2Random, int par3)
    {
        return (par1 & 8) != 0 ? 0 : (this.blockMaterial == Material.iron ? Item.doorSteel.shiftedIndex : Item.doorWood.shiftedIndex);
    }*/

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor)world.getBlockTileEntity(i, j, k);
    	if (tileentitymtdoor != null)
    	{
    		int itemDamage = -1;
    		switch(tileentitymtdoor.getMetaValue())
    		{
    		case 0:
    			itemDamage = 0;
    			break;
    		case 1:
    			itemDamage = 1;
    			break;
    		case 2:
    			itemDamage = 2;
    			break;
    		}
    		if (itemDamage > -1)
    		{
    			if (tileentitymtdoor.getDoorPiece() == 0)
    			{
		    		ItemStack itemstack = new ItemStack(MultiTexturedDoors.mtDoorItem, 1, itemDamage);
		    		EntityItem entityitem = new EntityItem(world, (float)i, (float)j, (float)k, new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
		            world.spawnEntityInWorld(entityitem);
    			}
    		}
    	}
        //super.onBlockRemoval(world, i, j, k);
    }
    
    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3D par5Vec3D, Vec3D par6Vec3D)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3D, par6Vec3D);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 255 ? false : par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }

    public int func_48212_i(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean var6 = (var5 & 8) != 0;
        int var7;
        int var8;

        if (var6)
        {
            var7 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
            var8 = var5;
        }
        else
        {
            var7 = var5;
            var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
        }

        boolean var9 = (var8 & 1) != 0;
        int var10 = var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
        return var10;
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
            return (TileEntity)mtDoorEntityClass.newInstance();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
	}
}
