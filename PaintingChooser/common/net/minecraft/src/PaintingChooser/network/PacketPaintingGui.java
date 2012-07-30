package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.Entity;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;
import net.minecraft.src.PaintingChooser.EntityPaintings;

public class PacketPaintingGui extends PacketPainting {

	public PacketPaintingGui() {
		super(PacketIds.PAINTING_GUI);
	}

	public PacketPaintingGui(EntityPaintings entitypaintings) {
		this();
		this.payload = new PacketPayload(2, 0, 0, 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		ModLoader.getLogger().warning("EntityID: " + this.getEntityId());
		this.setDirection(entitypaintings.direction);
		this.isChunkDataPacket = true;
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
}