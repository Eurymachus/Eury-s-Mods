package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemColored;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedPPlates;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTPPlate extends ItemBlock
{
    private String[] pPlateNames = new String[]{"Iron", "Gold", "Diamond"};
    private final Block blockRef;

    public ItemMTPPlate(int i)
    {
        super(i);
        this.blockRef = MultiTexturedPPlates.mtPPlate;
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
        .append(pPlateNames[itemstack.getItemDamage()])
        .toString();
    }
    
    /**
     * sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemMTPPlate setBlockNames(String[] par1ArrayOfStr)
    {
        this.pPlateNames = par1ArrayOfStr;
        return this;
    }
    
    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return this.blockRef.getBlockTextureFromSideAndMetadata(1000, par1);
    }
    
    
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	if (world.isRemote) return false;
        Block mtPPlate = MultiTexturedPPlates.mtPPlate;
        if (l == 0)
        {
            --j;
        }

        if (l == 1)
        {
            ++j;
        }

        if (l == 2)
        {
            --k;
        }

        if (l == 3)
        {
            ++k;
        }

        if (l == 4)
        {
            --i;
        }

        if (l == 5)
        {
            ++i;
        }
        if (itemstack.stackSize == 0)
        {
            return false;
        }
        else if (!entityplayer.canPlayerEdit(i, j, k))
        {
            return false;
        }
        else if (j == 255 && mtPPlate.blockMaterial.isSolid())
        {
            return false;
        }
        else if (world.canBlockBePlacedAt(mtPPlate.blockID, i, j, k, false, l))
        {
            if (world.setBlockAndMetadataWithNotify(i, j, k, mtPPlate.blockID, 0))
            {
                if (world.getBlockId(i, j, k) == mtPPlate.blockID)
                {
                	mtPPlate.onBlockPlaced(world, i, j, k, l);
                	mtPPlate.onBlockPlacedBy(world, i, j, k, entityplayer);
                	TileEntity tileentity = world.getBlockTileEntity(i, j, k);
                    if(tileentity != null && tileentity instanceof TileEntityMTPPlate)
                    {
                        TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)tileentity;
                    	tileentitymtpplate.setMetaValue(itemstack.getItemDamage());
                    	switch(itemstack.getItemDamage())
                    	{
	                    	case 0: tileentitymtpplate.setTriggerType(1); //mobs >
	                    	case 1: tileentitymtpplate.setTriggerType(2); //players only
	                    	case 2: tileentitymtpplate.setTriggerType(2); //players only
                    	}
                    	tileentitymtpplate.onInventoryChanged();
                    }
                }
                world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), mtPPlate.stepSound.getStepSound(), (mtPPlate.stepSound.getVolume() + 1.0F) / 2.0F, mtPPlate.stepSound.getPitch() * 0.8F);
                --itemstack.stackSize;
                return true;
            }
            else
            {
            	return false;
            }
        }
        else
        {
            return false;
        }
    }
}