package net.minecraft.src.PaintingChooser.network;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
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
			if (entity != null && entity instanceof EntityPainting) {
	        	ModLoader.openGUI(entityplayer, new GuiPainting((EntityPainting)entity, guiPacket.getArtList()));
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
			EntityPainting painting = null;
			if (paintingPacket.getCommand().equals("FIRSTUPDATE")) {
				painting = new EntityPainting(world, packet.xPosition, packet.yPosition, packet.zPosition, direction, "");
			}
			if (paintingPacket.getCommand().equals("UPDATEPAINTING")) {
				String artTitle = paintingPacket.getArtTitle();
				painting = new EntityPainting(world, packet.xPosition, packet.yPosition, packet.zPosition, direction, artTitle);	
			}
			if (painting != null) {
				((WorldClient)world).addEntityToWorld(entityId, painting);
			}
		}
	}
}
