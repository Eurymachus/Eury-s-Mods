package net.minecraft.src.EurysMods.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

/**
 * Packet Information for Reading/Writing packet data
 * 
 * packetId The ID of the packet used to identify which packet handler to use
 * payload The payload to be delivered with the packet
 * 
 * @author Eurymachus
 * 
 */
public class PacketUpdate extends EurysPacket {
	private int packetId;

	public PacketPayload payload;

	public PacketUpdate() {
	}

	public PacketUpdate(int packetId, PacketPayload payload) {
		this(packetId);
		this.payload = payload;
	}

	public PacketUpdate(int packetId) {
		this.packetId = packetId;
		this.isChunkDataPacket = true;
	}

	/**
	 * Writes a String to the DataOutputStream
	 */
	public static void writeString(String par0Str,
			DataOutputStream par1DataOutputStream) throws IOException {
		if (par0Str.length() > 32767) {
			throw new IOException("String too big");
		} else {
			par1DataOutputStream.writeShort(par0Str.length());
			par1DataOutputStream.writeChars(par0Str);
		}
	}

	/**
	 * Reads a string from a packet
	 */
	public static String readString(DataInputStream par0DataInputStream,
			int par1) throws IOException {
		short var2 = par0DataInputStream.readShort();

		if (var2 > par1) {
			throw new IOException(
					"Received string length longer than maximum allowed ("
							+ var2 + " > " + par1 + ")");
		} else if (var2 < 0) {
			throw new IOException(
					"Received string length is less than zero! Weird string!");
		} else {
			StringBuilder var3 = new StringBuilder();

			for (int var4 = 0; var4 < var2; ++var4) {
				var3.append(par0DataInputStream.readChar());
			}

			return var3.toString();
		}
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {

		data.writeInt(this.xPosition);
		data.writeInt(this.yPosition);
		data.writeInt(this.zPosition);

		// No payload means no data
		if (this.payload == null) {
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			return;
		}

		data.writeInt(this.payload.getIntSize());
		data.writeInt(this.payload.getFloatSize());
		data.writeInt(this.payload.getStringSize());
		data.writeInt(this.payload.getBoolSize());
		data.writeInt(this.payload.getDoubleSize());

		for (int i = 0; i < this.payload.getIntSize(); i++)
			data.writeInt(this.payload.getIntPayload(i));
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			data.writeFloat(this.payload.getFloatPayload(i));
		for (int i = 0; i < this.payload.getStringSize(); i++)
			data.writeUTF(this.payload.getStringPayload(i));
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			data.writeBoolean(this.payload.getBoolPayload(i));
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			data.writeDouble(this.payload.getDoublePayload(i));
	}

	@Override
	public void readData(DataInputStream data) throws IOException {

		/*
		 * this.xPosition = data.readInt(); this.yPosition = data.readInt();
		 * this.zPosition = data.readInt();
		 */
		this.setPosition(data.readInt(), data.readInt(), data.readInt());

		int intSize = data.readInt();
		int floatSize = data.readInt();
		int stringSize = data.readInt();
		int boolSize = data.readInt();
		int doubleSize = data.readInt();

		this.payload = new PacketPayload(intSize, floatSize, stringSize,
				boolSize, doubleSize);

		for (int i = 0; i < this.payload.getIntSize(); i++)
			this.payload.setIntPayload(i, data.readInt());
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			this.payload.setFloatPayload(i, data.readFloat());
		for (int i = 0; i < this.payload.getStringSize(); i++)
			this.payload.setStringPayload(i, data.readUTF());
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			this.payload.setBoolPayload(i, data.readBoolean());
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			this.payload.setDoublePayload(i, data.readDouble());
	}

	public boolean targetExists(World world) {
		return world
				.blockExists(this.xPosition, this.yPosition, this.zPosition);
	}

	public TileEntity getTarget(World world) {
		return world.getBlockTileEntity(this.xPosition, this.yPosition,
				this.zPosition);
	}

	@Override
	public int getID() {
		return this.packetId;
	}
}