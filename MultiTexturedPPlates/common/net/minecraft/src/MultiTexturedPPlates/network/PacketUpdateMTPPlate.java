package net.minecraft.src.MultiTexturedPPlates.network;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedPPlates.TileEntityMTPPlate;

public class PacketUpdateMTPPlate extends PacketMTP {
	public PacketUpdateMTPPlate() {
		super(PacketIds.MTPPLATE_UPDATE);
	}

	public PacketUpdateMTPPlate(TileEntityMTPPlate tileentitymtpplate) {
		this();
		this.payload = new PacketPayload(2, 0, 0, 0);
		TileEntity entity = tileentitymtpplate;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.setItemDamage(tileentitymtpplate.getMetaValue());
		this.setTriggerType(tileentitymtpplate.getTriggerType());
		this.isChunkDataPacket = true;
	}

	public void setItemDamage(int itemDamage) {
		this.payload.setIntPayload(0, itemDamage);
	}

	public void setTriggerType(int triggerType) {
		this.payload.setIntPayload(1, triggerType);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}

	public int getTriggerType() {
		return this.payload.getIntPayload(1);
	}
}
