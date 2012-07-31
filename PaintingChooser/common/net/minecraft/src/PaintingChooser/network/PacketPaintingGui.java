package net.minecraft.src.PaintingChooser.network;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EnumArt;
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

	public PacketPaintingGui(EntityPainting entitypaintings, ArrayList artList) {
		this();
		this.payload = new PacketPayload(2, 0, artList.size(), 0);
		this.xPosition = entitypaintings.xPosition;
		this.yPosition = entitypaintings.yPosition;
		this.zPosition = entitypaintings.zPosition;
		this.setEntityId(entitypaintings.entityId);
		this.setArtList(artList);
		this.isChunkDataPacket = true;
	}

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(0, entityId);
	}
	
	public int getEntityId() {
		return this.payload.getIntPayload(0);
	}
	
	public void setArtList(ArrayList artList) {
		for (int i = 0; i < artList.size(); i++) {
			EnumArt art = (EnumArt)artList.get(i);
			this.payload.setStringPayload(i, art.title);
		}
	}
	
	public ArrayList getArtList() {
		ArrayList artList = new ArrayList();
		EnumArt[] art = EnumArt.values();
		for (int i = 0; i < this.payload.getStringSize(); i++) {
			for (int j = 0; j < art.length; j++) {
				if (this.payload.getStringPayload(i).equals(art[j].title)) {
					artList.add(art[j]);
				}
			}
		}
		return artList;
	}
}