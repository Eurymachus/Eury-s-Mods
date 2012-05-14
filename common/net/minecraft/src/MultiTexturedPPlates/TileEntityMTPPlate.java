package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.EnumMobType;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedPPlates.network.PacketUpdateMTPPlate;

public class TileEntityMTPPlate extends TileEntity
{
	public int metaValue;
    public int triggerType;
	public int getMetaValue() { return this.metaValue; }
	public void setMetaValue(int meta) { this.metaValue = meta; }
	public int getTriggerType() { return this.triggerType; }
	public void setTriggerType(int trigger) { this.triggerType = trigger; }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("metaValue", this.getMetaValue());
        nbttagcompound.setInteger("triggerType", this.getTriggerType());
    }
	
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        this.setMetaValue(nbttagcompound.getInteger("metaValue"));
        this.setTriggerType(nbttagcompound.getInteger("triggerType"));
    }
    
	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(2,0,0,0);
		p.setIntPayload(0, this.metaValue);
		p.setIntPayload(1, this.triggerType);
		return p;
	}
	
	public Packet getDescriptionPacket()
	{
		return getUpdatePacket();
	}
	
	public Packet getUpdatePacket()
	{
		return new PacketUpdateMTPPlate(this).getPacket();
	}
	
	public void handleUpdatePacket(PacketUpdateMTPPlate packet, World world)
	{
		this.setMetaValue(packet.getItemDamage());
		this.setTriggerType(packet.getTriggerType());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
	}
}
