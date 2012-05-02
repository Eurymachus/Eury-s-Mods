package net.minecraft.src.MultiTexturedDoors.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketUpdateMTDoor extends PacketMTD
{
    public PacketUpdateMTDoor()
    {
    	super(PacketIds.MTDOOR_UPDATE);
    }
    
    public PacketUpdateMTDoor(TileEntityMTDoor tileentitymtdoor)
    {
		super(PacketIds.MTDOOR_UPDATE);
	
		this.payload = tileentitymtdoor.getPacketPayload();
		this.xPosition = tileentitymtdoor.xCoord;
		this.yPosition = tileentitymtdoor.yCoord;
		this.zPosition = tileentitymtdoor.zCoord;
		this.isChunkDataPacket = true;
	}
    
    public PacketUpdateMTDoor(int x, int y, int z, int metaValue, int doorPiece)
    {
       	super(PacketIds.MTDOOR_UPDATE);

       	this.payload = new PacketPayload(2,0,0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.payload.intPayload[0] = metaValue;
		this.payload.intPayload[1] = doorPiece;
		this.isChunkDataPacket = true;
    }
    
	public int getItemDamage()
	{
		return this.payload.intPayload[0];
	}
    
	public int getDoorPiece()
	{
		return this.payload.intPayload[1];
	}
}
