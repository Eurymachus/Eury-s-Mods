package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.EntityPainting;
import net.minecraft.src.EurysMods.network.PacketEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.PaintingChooser.PaintingChooser;

public class PacketUpdatePainting extends PacketEntity {

	public PacketUpdatePainting() {
		super();
		this.channel = PaintingChooser.PChooser.getModChannel();
	}

	public PacketUpdatePainting(EntityPainting entitypaintings) {
		this();
		this.payload = new PacketPayload(1, 0, 2, 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setDirection(entitypaintings.direction);
		this.setEntityId(entitypaintings.entityId);
		this.isChunkDataPacket = true;
	}

	public PacketUpdatePainting(EntityPainting entitypaintings, String command) {
		this(entitypaintings);
		this.setCommand(command);
	}

	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	public void setArtTitle(String title) {
		this.payload.setStringPayload(1, title);
	}

	public void setDirection(int direction) {
		this.payload.setIntPayload(0, direction);
	}

	public int getDirection() {
		return this.payload.getIntPayload(0);
	}

	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	public String getArtTitle() {
		return this.payload.getStringPayload(1);
	}
}
