package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTSignParts extends Item implements ITextureProvider
{
    public ItemMTSignParts(int i)
    {
        super(i);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }
    
    private String[] signParts = new String[]{"IronPlating", "IronPole", "GoldPlating", "GoldenPole", "DiamondPlating", "DiamondPole"};
    
    @Override
    public String getItemNameIS(ItemStack itemstack)
    {
    	return (new StringBuilder())
        .append(super.getItemName())
        .append(".")
        .append(signParts[itemstack.getItemDamage()])
        .toString();
    }
    
    public int filterData(int i)
    {
    	return i;
    }
    
    public int getIconFromDamage(int i)
    {
        if(i==0 || i==2 || i==4)
        {
            setMaxStackSize(16);
        }
        if(i==1 || i==3 || i==5)
        {
            setMaxStackSize(8);
        }
        return i;
    }
    
    public String getTextureFile()
    {
            return MultiTexturedSigns.MTS.getItemSheet();
    }
}
