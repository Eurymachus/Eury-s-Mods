package net.minecraft.src.MultiTexturedDoors.network;

import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedDoors.MultiTexturedDoors;
import net.minecraft.src.MultiTexturedDoors.TileEntityMTDoor;

public class PacketUpdateMTDoor extends PacketTileEntityMT {
	public PacketUpdateMTDoor() {
		super(MultiTexturedDoors.Core.getModChannel());
	}

	public PacketUpdateMTDoor(TileEntityMTDoor tileentitymtdoor) {
		super(MultiTexturedDoors.Core.getModChannel(), tileentitymtdoor);
		this.payload = tileentitymtdoor.getPacketPayload();
	}

	public void setDoorPiece(int doorPiece) {
		this.payload.setIntPayload(0, doorPiece);
	}

	public int getDoorPiece() {
		return this.payload.getIntPayload(0);
	}
}
