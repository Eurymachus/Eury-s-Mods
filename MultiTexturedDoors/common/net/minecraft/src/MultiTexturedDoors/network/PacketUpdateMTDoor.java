package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketUpdateMTDoor extends PacketMTD {
	public PacketUpdateMTDoor() {
		super(PacketIds.MTDOOR_UPDATE);
	}

	public PacketUpdateMTDoor(TileEntityMTDoor tileentitymtdoor) {
		this();
		this.xPosition = tileentitymtdoor.xCoord;
		this.yPosition = tileentitymtdoor.yCoord;
		this.zPosition = tileentitymtdoor.zCoord;
		this.payload = tileentitymtdoor.getPacketPayload();
		this.isChunkDataPacket = true;
	}

	public PacketUpdateMTDoor(int x, int y, int z, int metaValue, int doorPiece) {
		this();
		this.payload = new PacketPayload(2, 0, 0, 0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.setItemDamage(metaValue);
		this.setDoorPiece(doorPiece);
		this.isChunkDataPacket = true;
	}

	public void setItemDamage(int itemDamage) {
		this.payload.setIntPayload(0, itemDamage);
	}

	public void setDoorPiece(int doorPiece) {
		this.payload.setIntPayload(1, doorPiece);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}

	public int getDoorPiece() {
		return this.payload.getIntPayload(1);
	}
}
