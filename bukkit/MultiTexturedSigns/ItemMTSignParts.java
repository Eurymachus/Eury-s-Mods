package MultiTexturedSigns;

import forge.ITextureProvider;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class ItemMTSignParts extends Item implements ITextureProvider
{
    private String[] signParts = new String[] {"IronPlating", "IronPole", "GoldPlating", "GoldenPole", "DiamondPlating", "DiamondPole"};

    public ItemMTSignParts(int var1)
    {
        super(var1);
        this.a(true);
        this.setMaxDurability(0);
        this.setNoRepair();
    }

    public String a(ItemStack var1)
    {
        return super.getName() + "." + this.signParts[var1.getData()];
    }

    public int filterData(int var1)
    {
        return var1;
    }

    public int getIconFromDamage(int var1)
    {
        if (var1 == 0 || var1 == 2 || var1 == 4)
        {
            this.e(16);
        }

        if (var1 == 1 || var1 == 3 || var1 == 5)
        {
            this.e(8);
        }

        return var1;
    }

    public String getTextureFile()
    {
        return MultiTexturedSigns.MTS.getItemSheet();
    }
}
