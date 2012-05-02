package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemColored;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTDoor extends Item implements ITextureProvider
{
    private String[] doorNames = new String[]{"Iron", "Gold", "Diamond"};

    public ItemMTDoor(int i)
    {
        super(i);
        this.maxStackSize = 1;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }
    
    @Override
    public String getItemNameIS(ItemStack itemstack)
    {
    	return (new StringBuilder())
        .append(super.getItemName())
        .append(".")
        .append(doorNames[itemstack.getItemDamage()])
        .toString();
    }
    
    /**
     * sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemMTDoor setBlockNames(String[] par1ArrayOfStr)
    {
        this.doorNames = par1ArrayOfStr;
        return this;
    }
    
    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return par1;
    }
    
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	if (world.isRemote) return false;
        Block mtDoor = MultiTexturedDoors.mtDoor;
        if (l != 1)
        {
            return false;
        }
        if (itemstack.stackSize == 0)
        {
            return false;
        }
        else
        {
        	++j;
        	if (entityplayer.canPlayerEdit(i, j, k) && entityplayer.canPlayerEdit(i, j + 1, k))
        	{
        		if (!mtDoor.canPlaceBlockAt(world, i, j, k))
        		{
        			return false;
        		}
        		else
		        {
                    int var10 = MathHelper.floor_double((double)((entityplayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(world, i, j, k, var10, mtDoor, itemstack.getItemDamage());
	                --itemstack.stackSize;
		            return true;
		        }
        	}
        	else
        	{
        		return false;
        	}
        }
    }
    
    public static void placeDoorBlock(World par0World, int par1, int par2, int par3, int par4, Block par5Block, int damage)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (par4 == 0)
        {
            var7 = 1;
        }

        if (par4 == 1)
        {
            var6 = -1;
        }

        if (par4 == 2)
        {
            var7 = -1;
        }

        if (par4 == 3)
        {
            var6 = 1;
        }

        int var8 = (par0World.isBlockNormalCube(par1 - var6, par2, par3 - var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 - var6, par2 + 1, par3 - var7) ? 1 : 0);
        int var9 = (par0World.isBlockNormalCube(par1 + var6, par2, par3 + var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 + var6, par2 + 1, par3 + var7) ? 1 : 0);
        boolean var10 = par0World.getBlockId(par1 - var6, par2, par3 - var7) == par5Block.blockID || par0World.getBlockId(par1 - var6, par2 + 1, par3 - var7) == par5Block.blockID;
        boolean var11 = par0World.getBlockId(par1 + var6, par2, par3 + var7) == par5Block.blockID || par0World.getBlockId(par1 + var6, par2 + 1, par3 + var7) == par5Block.blockID;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        par0World.editingBlocks = true;
        par0World.setBlockAndMetadataWithNotify(par1, par2, par3, par5Block.blockID, par4);
        TileEntityMTDoor tileentitymtdoor = (TileEntityMTDoor)par0World.getBlockTileEntity(par1, par2, par3);
        if(tileentitymtdoor != null)
        {
        	tileentitymtdoor.setMetaValue(damage);
        	tileentitymtdoor.setDoorPiece(0);
        }
        par0World.setBlockAndMetadataWithNotify(par1, par2 + 1, par3, par5Block.blockID, 8 | (var12 ? 1 : 0));
        TileEntityMTDoor tileentitymtdoor1 = (TileEntityMTDoor)par0World.getBlockTileEntity(par1, par2+1, par3);
        if(tileentitymtdoor1 != null)
        {
        	tileentitymtdoor1.setMetaValue(damage);
        	tileentitymtdoor1.setDoorPiece(1);
        }
        par0World.editingBlocks = false;
        par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block.blockID);
        par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block.blockID);
    }

	@Override
	public String getTextureFile()
	{
		return MultiTexturedDoors.MTDCore.getBlockSheet();
	}
}