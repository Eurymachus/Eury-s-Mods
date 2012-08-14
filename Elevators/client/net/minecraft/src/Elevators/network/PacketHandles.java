package net.minecraft.src.Elevators.network;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.Elevators;
import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.Elevators.EntityElevator;
import net.minecraft.src.Elevators.GuiElevator;
import net.minecraft.src.Elevators.TileEntityElevator;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class PacketHandles implements ElevatorsPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer entityplayer, World world) {
		if (packet != null && packet.targetExists(world)) {
			TileEntity tileentity = packet.getTileEntity(world);
			if ((tileentity != null)
					&& (tileentity instanceof TileEntityElevator)) {
				TileEntityElevator tileentityelevator = (TileEntityElevator) tileentity;
				tileentityelevator.handleUpdatePacket(world, packet);
			}
		}
	}

	@Override
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
	}

	@Override
	public boolean sendResponseGUIPacket(int guiTransferID, int packetCode,
			String[] floors, boolean[] properties) {
		try {
			int[] var4 = new int[] { guiTransferID, packetCode };
			double[] var5 = new double[] { 5.0D, 5.0D, 5.0D };

			try {
				var5[0] = properties[0] ? 5.0D : -5.0D;
				var5[1] = properties[1] ? 5.0D : -5.0D;
				var5[2] = properties[2] ? 5.0D : -5.0D;
			} catch (Exception var7) {
				;
			}

			PacketElevatorGui guiPacket = new PacketElevatorGui("GUI_DATA",
					var4, var5, floors);
			Elevators.Core.getProxy().sendPacket(null, guiPacket.getPacket());
			return true;
		} catch (Exception var8) {
			ElevatorsCore.say(var8.getMessage(), true);
			ElevatorsCore.say("Error while sending response packet.", true);
			return false;
		}
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketElevatorGui) {
			try {
				PacketElevatorGui guiPacket = (PacketElevatorGui) packet;
				if (guiPacket.getCommand().equals("GUI_DATA")) {
					boolean[] dataBool = new boolean[3];//guiPacket.getBoolPayload();

					try {
						dataBool[0] = guiPacket.payload.getDoublePayload(0) > 0.0D;
						dataBool[1] = guiPacket.payload.getDoublePayload(1) > 0.0D;
						dataBool[2] = guiPacket.payload.getDoublePayload(2) > 0.0D;
					} catch (Exception e) {
						dataBool[0] = dataBool[1] = true;
						dataBool[2] = false;
					}
					
					int[] dataInt = new int[3];
					dataInt[0] = guiPacket.payload.getIntPayload(0);
					dataInt[1] = guiPacket.payload.getIntPayload(1);
					dataInt[2] = guiPacket.payload.getIntPayload(2);
					String[] dataString = guiPacket.getDataString();
					Elevators.screen = new GuiElevator(dataInt[0], dataInt[1],
							dataInt[2], dataString, dataBool);
					ModLoader.openGUI(
							ModLoader.getMinecraftInstance().thePlayer,
							Elevators.screen);
				}
			} catch (Exception e) {
				PacketElevatorGui guiPacket = new PacketElevatorGui(
						"GUI_COMMUNICATION_ERROR");
				Elevators.Core.getProxy().sendPacket(null,
						guiPacket.getPacket());
				ElevatorsCore.say(e.getMessage(), true);
				ElevatorsCore.say("Error sending elevator GUI packet.", true);
			}
		}
		if (packet instanceof PacketUpdateElevators) {
			PacketUpdateElevators elevatorPacket = (PacketUpdateElevators) packet;
			for (int i = 0; i < elevatorPacket.payload.getIntSize()
					&& i < elevatorPacket.payload.getFloatSize(); i++) {
				Entity entity = Elevators
						.getEntityByID(elevatorPacket.payload
								.getIntPayload(i));
				ElevatorsCore.say("Received request for entity id "
						+ elevatorPacket.payload.getIntPayload(i)
						+ " to be set to Y: "
						+ elevatorPacket.payload.getFloatPayload(i));

				if (entity != null) {
					ElevatorsCore.say("Entity with id "
							+ entity.entityId + " was set to "
							+ elevatorPacket.payload.getFloatPayload(i));

					if (entity instanceof EntityElevator) {
						EntityElevator entityelevator = (EntityElevator) entity;
						entityelevator.setPosition(entity.posX,
								elevatorPacket.payload
										.getFloatPayload(i),
								entity.posZ, true);
						entityelevator.metadata = (int) elevatorPacket.payload
								.getDoublePayload(i);
						ElevatorsCore.say(entityelevator.entityId + ": "
								+ entityelevator.metadata);
					}
				} else {
					ElevatorsCore.say("Entity with that ID does not exist");
				}
			}
		}
		if (packet instanceof PacketUpdateRiders) {
			PacketUpdateRiders ridersPacket = (PacketUpdateRiders) packet;
			for (int i = 0; i < ridersPacket.payload.getIntSize()
					&& i < ridersPacket.payload.getFloatSize(); i++) {
				Entity entity = Elevators
						.getEntityByID(ridersPacket.payload
								.getIntPayload(i));
				ElevatorsCore.say("Received request for entity id "
						+ ridersPacket.payload.getIntPayload(i)
						+ " to be set to Y: "
						+ ridersPacket.payload.getFloatPayload(i));

				if (entity != null) {
					ElevatorsCore.say("Entity with id "
							+ entity.entityId + " was set to "
							+ ridersPacket.payload.getFloatPayload(i));

					if (entity instanceof EntityPlayer) {
						entity.setPosition(
								entity.posX,
								ridersPacket.payload
										.getFloatPayload(i) + 1.5D,
								entity.posZ);
					} else if (!ridersPacket.getCommand().equals(
							"DISMOUNT_RIDERS")/* 239 */) {
						entity.setPosition(
								entity.posX,
								ridersPacket.payload
										.getFloatPayload(i),
								entity.posZ);
					}

					if (ridersPacket.getCommand().equals("DISMOUNT_RIDERS")/* 239 */) {
						entity.ridingEntity = null;
					}
				} else {
					ElevatorsCore.say("Entity with that ID does not exist");
				}
			}
		}
	}
}
