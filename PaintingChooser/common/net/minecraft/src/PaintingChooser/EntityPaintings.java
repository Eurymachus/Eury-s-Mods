package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;
import net.minecraft.src.EurysMods.network.PacketPayload;

public class EntityPaintings extends EntityPainting
{
    private int tickCounter1;
    protected EntityPlayer owner;

    public EntityPaintings(World world)
    {
        super(world);
    }

    public EntityPaintings(World world, EntityPlayer entityplayer, int x, int y, int z, int facing)
    {
        this(world);
        this.xPosition = x;
        this.yPosition = y;
        this.zPosition = z;
        this.owner = entityplayer;
        ArrayList artList = new ArrayList();
        EnumArt[] enumart = EnumArt.values();
        int enumartlength = enumart.length;

        for (int i = 0; i < enumartlength; ++i)
        {
            EnumArt currentArt = enumart[i];
            this.art = currentArt;
            this.setDirection(facing);

            if (this.onValidSurface())
            {
                artList.add(currentArt);
            }
        }

        if (artList.size() > 0)
        {
            this.art = (EnumArt)artList.get(1);
            PaintingChooser.openGui(world, entityplayer, artList, this);
        }

        this.setDirection(facing);
    }

    public void setPainting(EnumArt var1)
    {
        this.art = var1;
        this.setDirection(this.direction);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote)
        {
            this.tickCounter1 = 0;

            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (!this.isDead && !this.worldObj.isRemote)
        {
            this.setDead();
            this.setBeenAttacked();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }

        return true;
    }
    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }
    }
}
