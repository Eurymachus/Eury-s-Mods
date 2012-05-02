package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTLever extends Item implements ITextureProvider
{
    private String[] leverNames = new String[]{"Iron", "Gold", "Diamond"};

    public ItemMTLever(int i)
    {
        super(i);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }
    
    @Override
    public int getIconFromDamage(int i)
    {
    	return i;
    }
    
    @Override
    public String getItemNameIS(ItemStack itemstack)
    {
    	return (new StringBuilder())
        .append(super.getItemName())
        .append(".")
        .append(leverNames[itemstack.getItemDamage()])
        .toString();
    }
    
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	if (world.isRemote) return false;
        Block lever = MultiTexturedLevers.mtLever;
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
        else if (j == 255 && lever.blockMaterial.isSolid())
        {
            return false;
        }
        else if (world.canBlockBePlacedAt(lever.blockID, i, j, k, false, l))
        {
            if (world.setBlockAndMetadataWithNotify(i, j, k, lever.blockID, this.getMetadata(itemstack.getItemDamage())))
            {
                if (world.getBlockId(i, j, k) == lever.blockID)
                {
                    lever.onBlockPlaced(world, i, j, k, l);
                    lever.onBlockPlacedBy(world, i, j, k, entityplayer);
                    TileEntity tileentity = world.getBlockTileEntity(i, j, k);
                    if(tileentity != null && tileentity instanceof TileEntityMTLever)
                    {
                        TileEntityMTLever tileentitymtbutton = (TileEntityMTLever)tileentity;
                    	tileentitymtbutton.setMetaValue(itemstack.getItemDamage());
                    	tileentitymtbutton.onInventoryChanged();
                    }
                }
                world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), lever.stepSound.getStepSound(), (lever.stepSound.getVolume() + 1.0F) / 2.0F, lever.stepSound.getPitch() * 0.8F);
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

	@Override
	public String getTextureFile()
	{
		return MultiTexturedLevers.MTLCore.getItemSheet();
	}
}