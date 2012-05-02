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

        data.writeInt(xPosition);
        data.writeInt(yPosition);
        data.writeInt(zPosition);

        // No payload means no data
        if(payload == null) {
        	data.writeInt(0);
        	data.writeInt(0);
        	data.writeInt(0);
        	return;
        }

        data.writeInt(payload.intPayload.length);
        data.writeInt(payload.floatPayload.length);
        data.writeInt(payload.stringPayload.length);

        for(int intData : payload.intPayload)
        	data.writeInt(intData);
        for(float floatData : payload.floatPayload)
        	data.writeFloat(floatData);
        for(String stringData : payload.stringPayload)
        	data.writeUTF(stringData);

	}

	@Override
	public void readData(DataInputStream data) throws IOException {

		xPosition = data.readInt();
		yPosition = data.readInt();
		zPosition = data.readInt();

        payload = new PacketPayload();

        payload.intPayload = new int[data.readInt()];
        payload.floatPayload = new float[data.readInt()];
        payload.stringPayload = new String[data.readInt()];

        for(int i = 0; i < payload.intPayload.length; i++)
        	payload.intPayload[i] = data.readInt();
        for(int i = 0; i < payload.floatPayload.length; i++)
        	payload.floatPayload[i] = data.readFloat();
        for(int i = 0; i < payload.stringPayload.length; i++)
        	payload.stringPayload[i] = data.readUTF();

	}
	
	public boolean targetExists(World world) {
		return world.blockExists(xPosition, yPosition, zPosition);
	}
	
	public TileEntity getTarget(World world) {
		return world.getBlockTileEntity(xPosition, yPosition, zPosition);
	}
	
	@Override
	public int getID() 
	{
		return packetId;
	}
}
