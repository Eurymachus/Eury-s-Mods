package net.minecraft.src.PaintingChooser.network;

import java.util.ArrayList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
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
		int entityId = ((PacketPaintingGui)packet).getEntityId();
		int direction = ((PacketPaintingGui)packet).getDirection();
		Entity entity = PaintingChooser.getEntityByID(entityId);
		if (entity != null) {
			EntityPaintings entitypaintings = (EntityPaintings)entity;
			ArrayList artList = new ArrayList();
	        EnumArt[] enumart = EnumArt.values();
	        int enumartlength = enumart.length;

	        for (int i = 0; i < enumartlength; ++i)
	        {
	            EnumArt currentArt = enumart[i];
	            entitypaintings.art = currentArt;
	            entitypaintings.setDirection(direction);

	            if (entitypaintings.onValidSurface())
	            {
	                artList.add(currentArt);
	            }
	        }

	        if (artList.size() > 0)
	        {
	        	entitypaintings.art = (EnumArt)artList.get(0);
	        	ModLoader.openGUI(entityplayer, new GuiPainting(artList, entitypaintings));
	        }
		}
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}
}
