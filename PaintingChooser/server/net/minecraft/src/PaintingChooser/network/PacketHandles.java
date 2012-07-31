package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;
import net.minecraft.src.PaintingChooser.EntityPaintings;
import net.minecraft.src.PaintingChooser.PaintingChooser;

public class PacketHandles implements IPaintingPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting)packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			Entity entity = PaintingChooser.getEntityByID(world, entityId);
			if (entity != null && entity instanceof EntityPainting) {
				EntityPainting entitypaintings = (EntityPainting)entity;
				EnumArt[] enumart = EnumArt.values();
				for (int i = 0; i < enumart.length; i++) {
					if (enumart[i].title.equals(paintingPacket.getArtTitle())) {
						((EntityPaintings)entitypaintings).setPainting(enumart[i]);
						((EntityPaintings)entitypaintings).updatePainting();
					}
				}
			}
		}
	}
}
