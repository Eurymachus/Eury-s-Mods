package MultiTexturedSigns;

import forge.ITextureProvider;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class ItemMTSigns extends Item implements ITextureProvider
{
    private String[] signNames = new String[] {"IronSign", "GoldSign", "DiamondSign"};

    public ItemMTSigns(int var1)
    {
        super(var1);
        this.a(true);
        this.e(1);
        this.setMaxDurability(0);
        this.setNoRepair();
    }

    public String a(ItemStack var1)
    {
        return super.getName() + "." + this.signNames[var1.getData()];
    }

    public int filterData(int var1)
    {
        return var1;
    }

    public int getIconFromDamage(int var1)
    {
        switch (var1)
        {
            case 0:
                return 16;

            case 1:
                return 17;

            case 2:
                return 18;

            default:
                return 19;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
    {
        Block var8 = MTSCore.mtSignPost;
        Block var9 = MTSCore.mtSignWall;

        if (var7 == 0)
        {
            return false;
        }
        else if (!var3.getMaterial(var4, var5, var6).isBuildable())
        {
            return false;
        }
        else
        {
            if (var7 == 1)
            {
                ++var5;
            }

            if (var7 == 2)
            {
                --var6;
            }

            if (var7 == 3)
            {
                ++var6;
            }

            if (var7 == 4)
            {
                --var4;
            }

            if (var7 == 5)
            {
                ++var4;
            }

            if (!var2.d(var4, var5, var6))
            {
                return false;
            }
            else if (!var8.canPlace(var3, var4, var5, var6))
            {
                return false;
            }
            else
            {
                if (var7 == 1)
                {
                    int var10 = MathHelper.floor((double)((var2.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    var3.setTypeIdAndData(var4, var5, var6, var8.id, var10);
                }
                else
                {
                    var3.setTypeIdAndData(var4, var5, var6, var9.id, var7);
                }

                --var1.count;
                TileEntity var12 = var3.getTileEntity(var4, var5, var6);

                if (var12 != null && var12 instanceof TileEntityMTSign)
                {
                    TileEntityMTSign var11 = (TileEntityMTSign)var12;
                    var11.setMetaValue(var1.getData());
                    var11.update();
                    MultiTexturedSigns.displaymtsGuiEditSign(var2, var11);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    public String getTextureFile()
    {
        return MultiTexturedSigns.MTS.getItemSheet();
    }
}
