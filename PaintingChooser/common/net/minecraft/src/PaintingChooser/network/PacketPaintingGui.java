package net.minecraft.src.PaintingChooser.network;

import java.util.ArrayList;

import net.minecraft.src.EntityPainting;
import net.minecraft.src.EnumArt;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.PaintingChooser.PaintingChooser;

public class PacketPaintingGui extends PacketUpdate {

	public PacketPaintingGui() {
		super(PacketIds.GUI);
		this.channel = PaintingChooser.PChooser.getModChannel();
	}

	public void setEntityId(int entityId) {
		this.payload.setIntPayload(1, entityId);
	}

	public int getEntityId() {
		return this.payload.getIntPayload(1);
	}

	public PacketPaintingGui(int command, EntityPainting entitypaintings,
			ArrayList artList) {
		this();
		this.payload = new PacketPayload(2, 0, artList != null ? artList.size()
				: 0, 0);
		this.setPosition(entitypaintings.xPosition, entitypaintings.yPosition,
				entitypaintings.zPosition);
		this.setCommand(command);
		this.setEntityId(entitypaintings.entityId);
		this.setArtList(artList);
		this.isChunkDataPacket = true;
	}

	private void setCommand(int i) {
		this.payload.setIntPayload(0, i);
	}

	public int getCommand() {
		return this.payload.getIntPayload(0);
	}

	public void setArtList(ArrayList artList) {
		if (artList != null) {
			for (int i = 0; i < artList.size(); i++) {
				EnumArt art = (EnumArt) artList.get(i);
				this.payload.setStringPayload(i, art.title);
			}
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

	@Override
	public boolean targetExists(World world) {
		return false;
	}
}