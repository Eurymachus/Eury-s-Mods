package net.minecraft.src.EurysMods.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.packets.ForgePacket;

public class PacketUpdate extends EurysPacket
{
	private int packetId;
	
    public PacketPayload payload;

    public PacketUpdate() {}
    
    public PacketUpdate(int packetId, PacketPayload payload)
    {
    	this(packetId);
    	this.payload = payload;
    }

    public PacketUpdate(int packetId)
    {
    	this.packetId = packetId;
		this.isChunkDataPacket = true;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {

        data.writeInt(this.xPosition);
        data.writeInt(this.yPosition);
        data.writeInt(this.zPosition);

        // No payload means no data
        if(this.payload == null) {
        	data.writeInt(0);
        	data.writeInt(0);
        	data.writeInt(0);
        	return;
        }

        data.writeInt(this.payload.intPayload.length);
        data.writeInt(this.payload.floatPayload.length);
        data.writeInt(this.payload.stringPayload.length);

        for(int intData : this.payload.intPayload)
        	data.writeInt(intData);
        for(float floatData : this.payload.floatPayload)
        	data.writeFloat(floatData);
        for(String stringData : this.payload.stringPayload)
        	data.writeUTF(stringData);

	}

	@Override
	public void readData(DataInputStream data) throws IOException {

		this.xPosition = data.readInt();
		this.yPosition = data.readInt();
		this.zPosition = data.readInt();

		this.payload = new PacketPayload();

		this.payload.intPayload = new int[data.readInt()];
		this.payload.floatPayload = new float[data.readInt()];
		this.payload.stringPayload = new String[data.readInt()];

        for(int i = 0; i < this.payload.intPayload.length; i++)
        	this.payload.intPayload[i] = data.readInt();
        for(int i = 0; i < this.payload.floatPayload.length; i++)
        	this.payload.floatPayload[i] = data.readFloat();
        for(int i = 0; i < this.payload.stringPayload.length; i++)
        	this.payload.stringPayload[i] = data.readUTF();

	}
	
	public boolean targetExists(World world)
	{
		return world.blockExists(this.xPosition, this.yPosition, this.zPosition);
	}
	
	public TileEntity getTarget(World world)
	{
		return world.getBlockTileEntity(this.xPosition, this.yPosition, this.zPosition);
	}
	
	@Override
	public int getID() 
	{
		return this.packetId;
	}
}
