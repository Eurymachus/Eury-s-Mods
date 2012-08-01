package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.EntityPainting;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;

public class PacketUpdatePainting extends PacketPainting {

	public PacketUpdatePainting() {
		super(PacketIds.PAINTING_UPDATE);
	}

	public PacketUpdatePainting(EntityPainting entitypaintings) {
		this();

		this.payload = new PacketPayload(2, 0, 2, 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		this.setDirection(entitypaintings.direction);
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

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(0, entityId);
	}

	public void setDirection(int direction) {
		this.payload.setIntPayload(1, direction);
	}
	
	public int getEntityId() {
		return this.payload.getIntPayload(0);
	}
	
	public int getDirection() {
		return this.payload.getIntPayload(1);
	}
	
	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	public String getArtTitle() {
		return this.payload.getStringPayload(1);
	}
}
