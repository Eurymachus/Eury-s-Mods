package net.minecraft.src.MultiTexturedButtons;

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
import net.minecraft.src.mod_MultiTexturedButtons;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTButtons extends ItemBlock// implements ITextureProvider
{
    private String[] buttonNames = new String[]{"Iron", "Gold", "Diamond"};
    private final Block blockRef;
    
    public ItemMTButtons(int i)
    {
        super(i);
        this.blockRef = MTBCore.BlockMTButton;
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
        .append(buttonNames[itemstack.getItemDamage()])
        .toString();
    }
    
    public int filterData(int i)
    {
    	return i;
    }
    
    /**
     * sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemMTButtons setBlockNames(String[] par1ArrayOfStr)
    {
        this.buttonNames = par1ArrayOfStr;
        return this;
    }
    
    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return this.blockRef.getBlockTextureFromSideAndMetadata(0, par1);
    }
    
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        Block button = MTBCore.BlockMTButton;
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
        else if (j == 255 && button.blockMaterial.isSolid())
        {
            return false;
        }
        else if (world.canBlockBePlacedAt(button.blockID, i, j, k, false, l))
        {
            if (world.setBlockAndMetadataWithNotify(i, j, k, button.blockID, this.getMetadata(itemstack.getItemDamage())))
            {
                if (world.getBlockId(i, j, k) == button.blockID)
                {
                    button.onBlockPlaced(world, i, j, k, l);
                    button.onBlockPlacedBy(world, i, j, k, entityplayer);
                    TileEntity tileentity = world.getBlockTileEntity(i, j, k);
                    if(tileentity != null && tileentity instanceof TileEntityMTButton)
                    {
                        TileEntityMTButton tileentitymtbutton = (TileEntityMTButton)tileentity;
                    	tileentitymtbutton.setMetaValue(itemstack.getItemDamage());
                    	tileentitymtbutton.onInventoryChanged();
                    }
                }
                world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), button.stepSound.getStepSound(), (button.stepSound.getVolume() + 1.0F) / 2.0F, button.stepSound.getPitch() * 0.8F);
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