package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketUpdateMTButton extends PacketMTB {
	public PacketUpdateMTButton() {
		super(PacketIds.MTBUTTON_UPDATE);
	}

	public PacketUpdateMTButton(TileEntityMTButton tileentitymtbutton) {
		this();
		this.payload = tileentitymtbutton.getPacketPayload();
		TileEntity entity = tileentitymtbutton;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}

	public PacketUpdateMTButton(int x, int y, int z, int metaValue) {
		this();
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		setItemDamage(metaValue);
		this.isChunkDataPacket = true;
	}

	public void setItemDamage(int itemdamage) {
		this.payload.setIntPayload(0, itemdamage);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}
}
