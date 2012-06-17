package net.minecraft.src.EurysMods.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.forge.packets.ForgePacket;

/**
 * Packet Information Base
 * 
 * @author Eurymachus
 * 
 */
public abstract class EurysPacket extends ForgePacket {
	/**
	 * Only true for Packet51MapChunk, Packet52MultiBlockChange,
	 * Packet53BlockChange and Packet59ComplexEntity. Used to separate them into
	 * a different send queue.
	 */
	public boolean isChunkDataPacket = false;

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String channel;

	public void setPosition(int x, int y, int z) {
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
	}

	/**
	 * Retrieves the Custom Packet and Payload data as a Forge
	 * Packet250CustomPayload
	 */
	@Override
	public Packet getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getID());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = this.isChunkDataPacket;
		return packet;
	}
}
