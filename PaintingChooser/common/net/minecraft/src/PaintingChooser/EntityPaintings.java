package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.PaintingChooser.network.PacketUpdatePainting;

public class EntityPaintings extends EntityPainting
{
    private int tickCounter1;
    private boolean firstTick = false;
    protected String owner;
    
    public EntityPaintings(World world) {
    	super(world);
    }

    public EntityPaintings(World world, EntityPlayer entityplayer, int x, int y, int z, int facing)
    {
        super(world, x, y, z, facing);
        this.owner = entityplayer.username;
    }
    
    public EntityPaintings(World world, EntityPlayer entityplayer, int x, int y, int z, int facing, String title) {
        this(world);
        this.xPosition = x;
        this.yPosition = y;
        this.zPosition = z;
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            EnumArt var10 = var7[var9];

            if (var10.title.equals(title))
            {
                this.art = var10;
                break;
            }
        }

        this.setDirection(facing);
    }

    public void setPainting(EnumArt enumart)
    {
        this.art = enumart;
        this.setDirection(this.direction);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
    	if (firstTick == false) {
    		firstTick = true;
    		this.updatePainting();
    	}
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote)
        {
            this.tickCounter1 = 0;
            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
            } else {
        		this.updatePainting();
            }
        }
    }

    public void updatePainting() {
		if (!PaintingChooser.PChooser.getProxy().isClient) {
			if (this != null && this.art != null) {
				ModLoader.getLogger().warning("Art: " + this.art.title + " Direction: " + this.direction);
				PacketUpdatePainting paintingPacket = new PacketUpdatePainting(this, "UPDATEPAINTING");
				paintingPacket.setArtTitle(this.art.title);
				PaintingChooser.PChooser.getProxy().sendPacketToAll(paintingPacket.getPacket(), this.xPosition, this.yPosition, this.zPosition, 16, mod_PaintingChooser.instance);
			}
		}
	}

	/**
     * Called when the entity is attacked.
     */
    @Override
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
    @Override
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
    @Override
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
/*    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
    	if (!this.worldObj.isRemote) {
    		EnumArt currentArt = this.art;
    		int direction = this.direction;
            ArrayList artList = new ArrayList();
            EnumArt[] var7 = EnumArt.values();
            int var8 = var7.length;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                EnumArt var10 = var7[var9];
                this.art = var10;
                this.setDirection(direction);

                if (this.onValidSurface())
                {
                    artList.add(var10);
                }
            }
            this.art = currentArt;
            this.setDirection(direction);
            
            if (artList.size() > 0) {
            	PaintingChooser.openGui(this.worldObj, entityplayer, this, artList);
            }
    		return true;
    	}
    	return false;
    }*/
    
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeEntityToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setString("owner", this.owner);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readEntityFromNBT(par1NBTTagCompound);
    	this.owner = par1NBTTagCompound.getString("owner");
    }
}
