package MultiTexturedSigns;

import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockMTSign extends BlockContainer
{
    private Class mtSignEntityClass;
    private boolean isFreestanding;

    public BlockMTSign(int var1, Class var2, boolean var3, float var4, float var5, boolean var6, boolean var7)
    {
        super(var1, Material.ORE);
        this.isFreestanding = var3;
        this.mtSignEntityClass = var2;
        this.c(var4);
        this.b(var5);

        if (var6)
        {
            this.s();
        }

        if (var7)
        {
            this.j();
        }

        float var8 = 0.25F;
        float var9 = 1.0F;
        this.a(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, var9, 0.5F + var8);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess var1, int var2, int var3, int var4)
    {
        if (!this.isFreestanding)
        {
            int var5 = var1.getData(var2, var3, var4);
            float var6 = 0.28125F;
            float var7 = 0.78125F;
            float var8 = 0.0F;
            float var9 = 1.0F;
            float var10 = 0.125F;
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            if (var5 == 2)
            {
                this.a(var8, var6, 1.0F - var10, var9, var7, 1.0F);
            }

            if (var5 == 3)
            {
                this.a(var8, var6, 0.0F, var9, var7, var10);
            }

            if (var5 == 4)
            {
                this.a(1.0F - var10, var6, var8, 1.0F, var7, var9);
            }

            if (var5 == 5)
            {
                this.a(0.0F, var6, var8, var10, var7, var9);
            }
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int c()
    {
        return -1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean a()
    {
        return false;
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        try
        {
            return (TileEntity)this.mtSignEntityClass.newInstance();
        }
        catch (Exception var2)
        {
            throw new RuntimeException(var2);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random var1)
    {
        return 0;
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getTileEntity(var2, var3, var4);

        if (var5 != null && var5 instanceof TileEntityMTSign)
        {
            TileEntityMTSign var6 = (TileEntityMTSign)var5;
            byte var7 = -1;

            switch (var6.getMetaValue())
            {
                case 0:
                    var7 = 0;
                    break;

                case 1:
                    var7 = 2;
                    break;

                case 2:
                    var7 = 4;
            }

            if (var7 > -1)
            {
                ItemStack var8 = new ItemStack(MTSCore.mtsItemSignParts, 1, var7);
                EntityItem var9 = new EntityItem(var1, (double)((float)var2), (double)((float)var3), (double)((float)var4), new ItemStack(var8.id, 1, var8.getData()));
                var1.addEntity(var9);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = false;

        if (this.isFreestanding)
        {
            if (!var1.getMaterial(var2, var3 - 1, var4).isBuildable())
            {
                var6 = true;
            }
        }
        else
        {
            int var7 = var1.getData(var2, var3, var4);
            var6 = true;

            if (var7 == 2 && var1.getMaterial(var2, var3, var4 + 1).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 3 && var1.getMaterial(var2, var3, var4 - 1).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 4 && var1.getMaterial(var2 + 1, var3, var4).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 5 && var1.getMaterial(var2 - 1, var3, var4).isBuildable())
            {
                var6 = false;
            }
        }

        if (var6)
        {
            this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
            var1.setTypeId(var2, var3, var4, 0);
        }

        super.doPhysics(var1, var2, var3, var4, var5);
    }
}
