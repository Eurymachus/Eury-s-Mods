package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.IPacketHandling;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.PaintingChooser.EntityPaintings;
import net.minecraft.src.PaintingChooser.PChooserCore;
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
			if (guiPacket.getKillCode() == 999) {
				Entity entity = PaintingChooser.getEntityByID(world, guiPacket.getEntityId());
				if (entity instanceof EntityPainting) {
					EntityPainting entitypainting = (EntityPainting)entity;
					entitypainting.setDead();
					entitypainting.worldObj.spawnEntityInWorld(new EntityItem(entitypainting.worldObj, entitypainting.posX, entitypainting.posY, entitypainting.posZ, new ItemStack(PChooserCore.itemPaintings)));
				}
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
			Entity entity = PaintingChooser.getEntityByID(world, entityId);
			if (entity != null && entity instanceof EntityPainting) {
				EntityPainting entitypainting = (EntityPainting)entity;
				EnumArt[] enumart = EnumArt.values();
				for (int i = 0; i < enumart.length; i++) {
					if (enumart[i].title.equals(paintingPacket.getArtTitle())) {
						entitypainting.art = enumart[i];
						entitypainting.setDirection(((EntityPainting)entity).direction);
						PaintingChooser.notifyPlayers(entitypainting);
					}
				}
			}
		}
	}
}
