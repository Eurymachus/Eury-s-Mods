package net.minecraft.src.MultiTexturedPPlates.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedPPlates.TileEntityMTPPlate;

public class PacketUpdateMTPPlate extends PacketMTP
{
    public PacketUpdateMTPPlate()
    {
    	super(PacketIds.MTPPLATE_UPDATE);
    }
    
    public PacketUpdateMTPPlate(TileEntityMTPPlate tileentitymtpplate)
    {
		super(PacketIds.MTPPLATE_UPDATE);
	
		this.payload = tileentitymtpplate.getPacketPayload();
		TileEntity entity = (TileEntity)tileentitymtpplate;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}
    
    public PacketUpdateMTPPlate(int x, int y, int z, int metaValue, int triggerType)
    {
       	super(PacketIds.MTPPLATE_UPDATE);

       	this.payload = new PacketPayload(2,0,0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.payload.intPayload[0] = metaValue;
		this.payload.intPayload[1] = triggerType;
		this.isChunkDataPacket = true;
    }
    
	public int getItemDamage()
	{
		return this.payload.intPayload[0];
	}

	public int getTriggerType()
	{
		return this.payload.intPayload[1];
	}
}
