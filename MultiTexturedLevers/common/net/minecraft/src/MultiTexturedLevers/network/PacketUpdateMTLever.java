package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketUpdateMTLever extends PacketMTL {
	public PacketUpdateMTLever() {
		super(PacketIds.MTLEVER_UPDATE);
	}

	public PacketUpdateMTLever(TileEntityMTLever tileentitymtlever) {
		this();
		this.payload = tileentitymtlever.getPacketPayload();
		TileEntity entity = tileentitymtlever;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}

	public PacketUpdateMTLever(int x, int y, int z, int metaValue) {
		this();
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.isChunkDataPacket = true;
	}

	public void setItemDamage(int itemDamage) {
		this.payload.setIntPayload(0, itemDamage);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}
}
