package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.network.PacketUpdateMTButton;

public class TileEntityMTButton extends TileEntity
{
	public int metaValue;
	public int getMetaValue() { return this.metaValue; }
	public void setMetaValue(int meta) { this.metaValue = meta; }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("metaValue", this.metaValue);
    }
	
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        this.setMetaValue(nbttagcompound.getInteger("metaValue"));
    }
    
	public PacketPayload getPacketPayload() {

		int[] dataInt = new int[1];
		float[] dataFloat = new float[1];
		String[] dataString = new String[1];
		dataInt[0] = this.metaValue;
		dataFloat[0] = 0;
		dataString[0] = "";
		PacketPayload p = new PacketPayload();
		p.intPayload = dataInt;
		p.floatPayload = dataFloat;
		p.stringPayload = dataString;
		return p;
	}
	
	public Packet getDescriptionPacket()
	{
		return getUpdatePacket();
	}
	
	public Packet getUpdatePacket()
	{
		return new PacketUpdateMTButton(this).getPacket();
	}
	
	public void handleUpdatePacket(PacketUpdate packet, World world)
	{
		PacketUpdateMTButton bp = (PacketUpdateMTButton)packet;
		this.setMetaValue(bp.getItemDamage());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
	}
}
