package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemColored;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedPPlates;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTPPlate extends ItemBlock
{
    private String[] pPlateNames = new String[]{"Iron", "Gold", "Diamond"};

    public ItemMTPPlate(int i)
    {
        super(i);
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
    
    public int filterData(int i)
    {
    	return i;
    }
    
    /**
     * sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemMTPPlate setBlockNames(String[] par1ArrayOfStr)
    {
        this.pPlateNames = par1ArrayOfStr;
        return this;
    }
    
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        int var8 = par3World.getBlockId(par4, par5, par6);
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }
        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }
        else if (par5 == 255 && MultiTexturedPPlates.mtPPlate.blockMaterial.isSolid())
        {
            return false;
        }
        else if (par3World.canBlockBePlacedAt(MultiTexturedPPlates.mtPPlate.blockID, par4, par5, par6, false, par7))
        {
            Block var9 = MultiTexturedPPlates.mtPPlate;

            if (par3World.setBlockAndMetadataWithNotify(par4, par5, par6, MultiTexturedPPlates.mtPPlate.blockID, 0))
            {
                if (par3World.getBlockId(par4, par5, par6) == MultiTexturedPPlates.mtPPlate.blockID)
                {
                    MultiTexturedPPlates.mtPPlate.onBlockPlaced(par3World, par4, par5, par6, par7);
                    MultiTexturedPPlates.mtPPlate.onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer);
                    TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate)par3World.getBlockTileEntity(par4, par5, par6);
                    if(tileentitymtpplate != null)
                    {
                    	tileentitymtpplate.setMetaValue(par1ItemStack.getItemDamage());
                    	switch(par1ItemStack.getItemDamage())
                    	{
	                    	case 0: tileentitymtpplate.setTriggerType(1); //mobs >
	                    	case 1: tileentitymtpplate.setTriggerType(2); //players only
	                    	case 2: tileentitymtpplate.setTriggerType(2); //players only
                    	}
                    }
                }
                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var9.stepSound.getStepSound(), (var9.stepSound.getVolume() + 1.0F) / 2.0F, var9.stepSound.getPitch() * 0.8F);
                
                --par1ItemStack.stackSize;
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}