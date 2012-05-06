package MultiTexturedButtons;

import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockMTButton extends BlockContainer
{
    Class mtButtonEntityClass;

    public BlockMTButton(int var1, Class var2, float var3, StepSound var4, boolean var5, boolean var6)
    {
        super(var1, Material.ORIENTABLE);
        this.mtButtonEntityClass = var2;
        this.c(var3);
        this.a(var4);

        if (var5)
        {
            this.s();
        }

        if (var6)
        {
            this.j();
        }

        this.a(true);
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
     * How many world ticks before ticking
     */
    public int d()
    {
        return 20;
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World var1, int var2, int var3, int var4, int var5)
    {
        return var5 == 2 && var1.isBlockSolidOnSide(var2, var3, var4 + 1, 2) || var5 == 3 && var1.isBlockSolidOnSide(var2, var3, var4 - 1, 3) || var5 == 4 && var1.isBlockSolidOnSide(var2 + 1, var3, var4, 4) || var5 == 5 && var1.isBlockSolidOnSide(var2 - 1, var3, var4, 5);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, 5) || var1.isBlockSolidOnSide(var2 + 1, var3, var4, 4) || var1.isBlockSolidOnSide(var2, var3, var4 - 1, 3) || var1.isBlockSolidOnSide(var2, var3, var4 + 1, 2);
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void postPlace(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getData(var2, var3, var4);
        int var7 = var6 & 8;
        var6 &= 7;

        if (var5 == 2 && var1.isBlockSolidOnSide(var2, var3, var4 + 1, 2))
        {
            var6 = 4;
        }
        else if (var5 == 3 && var1.isBlockSolidOnSide(var2, var3, var4 - 1, 3))
        {
            var6 = 3;
        }
        else if (var5 == 4 && var1.isBlockSolidOnSide(var2 + 1, var3, var4, 4))
        {
            var6 = 2;
        }
        else if (var5 == 5 && var1.isBlockSolidOnSide(var2 - 1, var3, var4, 5))
        {
            var6 = 1;
        }
        else
        {
            var6 = this.getOrientation(var1, var2, var3, var4);
        }

        var1.setData(var2, var3, var4, var6 + var7);
    }

    private int getOrientation(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2 - 1, var3, var4, 5) ? 1 : (var1.isBlockSolidOnSide(var2 + 1, var3, var4, 4) ? 2 : (var1.isBlockSolidOnSide(var2, var3, var4 - 1, 3) ? 3 : (var1.isBlockSolidOnSide(var2, var3, var4 + 1, 2) ? 4 : 1)));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (this.redundantCanPlaceBlockAt(var1, var2, var3, var4))
        {
            int var6 = var1.getData(var2, var3, var4) & 7;
            boolean var7 = false;

            if (!var1.isBlockSolidOnSide(var2 - 1, var3, var4, 5) && var6 == 1)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2 + 1, var3, var4, 4) && var6 == 2)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 - 1, 3) && var6 == 3)
            {
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3, var4 + 1, 2) && var6 == 4)
            {
                var7 = true;
            }

            if (var7)
            {
                this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
                var1.setTypeId(var2, var3, var4, 0);
            }
        }
    }

    private boolean redundantCanPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        if (!this.canPlace(var1, var2, var3, var4))
        {
            this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
            var1.setTypeId(var2, var3, var4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getData(var2, var3, var4);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) > 0;
        float var8 = 0.375F;
        float var9 = 0.625F;
        float var10 = 0.1875F;
        float var11 = 0.125F;

        if (var7)
        {
            var11 = 0.0625F;
        }

        if (var6 == 1)
        {
            this.a(0.0F, var8, 0.5F - var10, var11, var9, 0.5F + var10);
        }
        else if (var6 == 2)
        {
            this.a(1.0F - var11, var8, 0.5F - var10, 1.0F, var9, 0.5F + var10);
        }
        else if (var6 == 3)
        {
            this.a(0.5F - var10, var8, 0.0F, 0.5F + var10, var9, var11);
        }
        else if (var6 == 4)
        {
            this.a(0.5F - var10, var8, 1.0F - var11, 0.5F + var10, var9, 1.0F);
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        this.interact(var1, var2, var3, var4, var5);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        int var6 = var1.getData(var2, var3, var4);
        int var7 = var6 & 7;
        int var8 = 8 - (var6 & 8);

        if (var8 == 0)
        {
            return true;
        }
        else
        {
            var1.setData(var2, var3, var4, var7 + var8);
            var1.b(var2, var3, var4, var2, var3, var4);
            var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.6F);
            var1.applyPhysics(var2, var3, var4, this.id);

            if (var7 == 1)
            {
                var1.applyPhysics(var2 - 1, var3, var4, this.id);
            }
            else if (var7 == 2)
            {
                var1.applyPhysics(var2 + 1, var3, var4, this.id);
            }
            else if (var7 == 3)
            {
                var1.applyPhysics(var2, var3, var4 - 1, this.id);
            }
            else if (var7 == 4)
            {
                var1.applyPhysics(var2, var3, var4 + 1, this.id);
            }
            else
            {
                var1.applyPhysics(var2, var3 - 1, var4, this.id);
            }

            var1.c(var2, var3, var4, this.id, this.d());
            return true;
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getData(var2, var3, var4);

        if ((var5 & 8) > 0)
        {
            var1.applyPhysics(var2, var3, var4, this.id);
            int var6 = var5 & 7;

            if (var6 == 1)
            {
                var1.applyPhysics(var2 - 1, var3, var4, this.id);
            }
            else if (var6 == 2)
            {
                var1.applyPhysics(var2 + 1, var3, var4, this.id);
            }
            else if (var6 == 3)
            {
                var1.applyPhysics(var2, var3, var4 - 1, this.id);
            }
            else if (var6 == 4)
            {
                var1.applyPhysics(var2, var3, var4 + 1, this.id);
            }
            else
            {
                var1.applyPhysics(var2, var3 - 1, var4, this.id);
            }
        }

        TileEntityMTButton var10 = (TileEntityMTButton)var1.getTileEntity(var2, var3, var4);

        if (var10 != null)
        {
            byte var7 = -1;

            switch (var10.getMetaValue())
            {
                case 0:
                    var7 = 0;
                    break;

                case 1:
                    var7 = 1;
                    break;

                case 2:
                    var7 = 2;
            }

            if (var7 > -1)
            {
                ItemStack var8 = new ItemStack(MTBCore.BlockMTButton, 1, var7);
                EntityItem var9 = new EntityItem(var1, (double)((float)var2), (double)((float)var3), (double)((float)var4), new ItemStack(var8.id, 1, var8.getData()));
                var1.addEntity(var9);
            }
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
     * Is this block powering the block on the specified side
     */
    public boolean a(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return (var1.getData(var2, var3, var4) & 8) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean d(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getData(var2, var3, var4);

        if ((var6 & 8) == 0)
        {
            return false;
        }
        else
        {
            int var7 = var6 & 7;
            return var7 == 5 && var5 == 1 ? true : (var7 == 4 && var5 == 2 ? true : (var7 == 3 && var5 == 3 ? true : (var7 == 2 && var5 == 4 ? true : var7 == 1 && var5 == 5)));
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void a(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.isStatic)
        {
            int var6 = var1.getData(var2, var3, var4);

            if ((var6 & 8) != 0)
            {
                var1.setData(var2, var3, var4, var6 & 7);
                var1.applyPhysics(var2, var3, var4, this.id);
                int var7 = var6 & 7;

                if (var7 == 1)
                {
                    var1.applyPhysics(var2 - 1, var3, var4, this.id);
                }
                else if (var7 == 2)
                {
                    var1.applyPhysics(var2 + 1, var3, var4, this.id);
                }
                else if (var7 == 3)
                {
                    var1.applyPhysics(var2, var3, var4 - 1, this.id);
                }
                else if (var7 == 4)
                {
                    var1.applyPhysics(var2, var3, var4 + 1, this.id);
                }
                else
                {
                    var1.applyPhysics(var2, var3 - 1, var4, this.id);
                }

                var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.5F);
                var1.b(var2, var3, var4, var2, var3, var4);
            }
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.1875F;
        float var2 = 0.125F;
        float var3 = 0.125F;
        this.a(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        try
        {
            return (TileEntity)this.mtButtonEntityClass.newInstance();
        }
        catch (Exception var2)
        {
            throw new RuntimeException(var2);
        }
    }
}
