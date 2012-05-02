package net.minecraft.src.MultiTexturedSigns;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTSign extends BlockContainer
{
    private Class mtSignEntityClass;
    private boolean isFreestanding;
    
    public BlockMTSign(int i, Class class1, boolean flag, float hardness, float resistance, boolean disableStats, boolean requiresSelfNotify)
    {
        super(i, Material.iron);
        isFreestanding = flag;
        mtSignEntityClass = class1;
        
        setHardness(hardness);
        setResistance(resistance);
        if (disableStats) disableStats();
        if (requiresSelfNotify) setRequiresSelfNotify();
        
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        if (isFreestanding)
        {
            return;
        }
        int l = iblockaccess.getBlockMetadata(i, j, k);
        float f = 0.28125F;
        float f1 = 0.78125F;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.125F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if (l == 2)
        {
            setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
        }
        if (l == 3)
        {
            setBlockBounds(f2, f, 0.0F, f3, f1, f4);
        }
        if (l == 4)
        {
            setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
        }
        if (l == 5)
        {
            setBlockBounds(0.0F, f, f2, f4, f1, f3);
        }
    }

    public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public TileEntity getBlockEntity()
    {
        try
        {
            return (TileEntity)mtSignEntityClass.newInstance();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }
    
    @Override
    public int quantityDropped(Random rand)
    {
    	return 0;
    }
    
    public void onBlockRemoval(World world, int i, int j, int k)
    {
    	TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        if ((tileentity != null) && (tileentity instanceof TileEntityMTSign))
        {
            TileEntityMTSign mtstileentitysign = (TileEntityMTSign)tileentity;
    		int itemDamage = -1;
    		switch(mtstileentitysign.getMetaValue())
    		{
    		case 0:
    			itemDamage = 0;
    			break;
    		case 1:
    			itemDamage = 2;
    			break;
    		case 2:
    			itemDamage = 4;
    			break;
    		}
    		if (itemDamage > -1)
    		{
	    		ItemStack itemstack = new ItemStack(MultiTexturedSigns.mtsItemSignParts, 1, itemDamage);
	    		EntityItem entityitem = new EntityItem(world, (float)i, (float)j, (float)k, new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
	            world.spawnEntityInWorld(entityitem);
    		}
    	}
    }
    
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean flag = false;
        if (isFreestanding)
        {
            if (!world.getBlockMaterial(i, j - 1, k).isSolid())
            {
                flag = true;
            }
        }
        else
        {
            int i1 = world.getBlockMetadata(i, j, k);
            flag = true;
            if (i1 == 2 && world.getBlockMaterial(i, j, k + 1).isSolid())
            {
                flag = false;
            }
            if (i1 == 3 && world.getBlockMaterial(i, j, k - 1).isSolid())
            {
                flag = false;
            }
            if (i1 == 4 && world.getBlockMaterial(i + 1, j, k).isSolid())
            {
                flag = false;
            }
            if (i1 == 5 && world.getBlockMaterial(i - 1, j, k).isSolid())
            {
                flag = false;
            }
        }
        if (flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
        super.onNeighborBlockChange(world, i, j, k, l);
    }
}
