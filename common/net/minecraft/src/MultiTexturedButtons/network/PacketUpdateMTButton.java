package net.minecraft.src.MultiTexturedButtons.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketUpdateMTButton extends PacketMTB
{
    public PacketUpdateMTButton()
    {
    	super(PacketIds.MTBUTTON_UPDATE);
    }
    
    public PacketUpdateMTButton(TileEntityMTButton tileentitymtbutton)
    {
		super(PacketIds.MTBUTTON_UPDATE);
	
		this.payload = tileentitymtbutton.getPacketPayload();
		TileEntity entity = (TileEntity)tileentitymtbutton;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}
    
    public PacketUpdateMTButton(int x, int y, int z, int metaValue)
    {
       	super(PacketIds.MTBUTTON_UPDATE);

       	this.payload = new PacketPayload(1,0,0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.payload.intPayload[0] = metaValue;
		this.isChunkDataPacket = true;
    }
    
	public int getItemDamage()
	{
		return this.payload.intPayload[0];
	}
}
