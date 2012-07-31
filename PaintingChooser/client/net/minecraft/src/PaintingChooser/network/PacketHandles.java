package net.minecraft.src.PaintingChooser.network;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;
import net.minecraft.src.PaintingChooser.EntityPaintings;
import net.minecraft.src.PaintingChooser.GuiPainting;
import net.minecraft.src.PaintingChooser.PaintingChooser;

public class PacketHandles implements IPaintingPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketPaintingGui) {
			PacketPaintingGui guiPacket = (PacketPaintingGui)packet;
			int entityId = guiPacket.getEntityId();
			Entity entity = PaintingChooser.getEntityByID(world, entityId);
			ModLoader.getLogger().warning("Entity: " + entity);
			if (entity != null && entity instanceof EntityPainting) {
				EntityPainting entitypaintings = (EntityPainting)entity;
				ArrayList artList = guiPacket.getArtList();
	        	ModLoader.openGUI(entityplayer, new GuiPainting(entitypaintings, artList));
			}
		}
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting)packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			String artTitle = paintingPacket.getArtTitle();
			EntityPaintings painting = new EntityPaintings(world, entityplayer, packet.xPosition, packet.yPosition, packet.zPosition, direction, artTitle);
			((WorldClient)world).addEntityToWorld(entityId, painting);
		}
	}
}
