package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTSignParts extends Item
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
}
