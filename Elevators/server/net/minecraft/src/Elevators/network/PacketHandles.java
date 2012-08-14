package net.minecraft.src.Elevators.network;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.BlockElevator;
import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.Elevators.TileEntityElevator;
import net.minecraft.src.EurysMods.network.PacketTileEntity;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class PacketHandles implements ElevatorsPacketHandling {
	@Override
	public void handleTileEntityPacket(PacketTileEntity packet,
			EntityPlayer player, World world) {
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
	public void handleGuiPacket(PacketUpdate packet, EntityPlayer player,
			World world) {
	}

	@Override
	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		int entityID;
		EntityPlayerMP entityplayermp = null;
		if (entityplayer != null) {
			entityplayermp = (EntityPlayerMP) entityplayer;
		}

		if (packet instanceof PacketElevatorGui) {
			PacketElevatorGui guiPacket = (PacketElevatorGui) packet;
			if (guiPacket.getCommand().equals("GUI_DATA")) {
				entityID = guiPacket.payload.getIntPayload(0);

				if (entityplayermp == null
						|| entityID != entityplayermp.entityId) {
					return;
				}

				int guiCommandValue = guiPacket.payload.getIntPayload(1);
				ChunkPosition chunkPos = (ChunkPosition) ElevatorsCore.elevatorRequests
						.get(Integer.valueOf(entityID));

				if (chunkPos == null) {
					return;
				}

				Block var6 = Block.blocksList[entityplayermp.worldObj
						.getBlockId(chunkPos.x, chunkPos.y, chunkPos.z)];

				if (var6 == null
						|| var6.blockID != ElevatorsCore.Elevator.blockID) {
					return;
				}

				BlockElevator var7 = (BlockElevator) var6;
				TileEntityElevator var8 = BlockElevator.getTileEntity(
						entityplayermp.worldObj, chunkPos.x, chunkPos.y,
						chunkPos.z);

				if (var8 == null) {
					return;
				}

				ElevatorsCore
						.say("Received elevator response from "
								+ entityplayermp.username + " under ID#"
								+ entityID);
				ElevatorsCore.say("Player requests command "
						+ guiCommandValue);
				boolean[] var9 = new boolean[3];

				try {
					var9[0] = guiPacket.payload.getDoublePayload(0) > 0.0D;
					var9[1] = guiPacket.payload.getDoublePayload(1) > 0.0D;
					var9[2] = guiPacket.payload.getDoublePayload(2) > 0.0D;
				} catch (Exception var11) {
					var9[0] = var9[1] = true;
					var9[2] = false;
				}

				switch (guiCommandValue) {
				case ElevatorsCore.GUI_RESET:
					ElevatorsCore.elevator_reset(entityplayermp.worldObj,
							chunkPos);

					if (ElevatorsCore.elevatorRequests.containsKey(Integer
							.valueOf(entityID))) {
						ElevatorsCore.elevatorRequests.remove(Integer
								.valueOf(entityID));
					}

					break;

				case ElevatorsCore.GUI_OPTIONS_APPLY:
					ElevatorsCore.checkedFloorNames.put(chunkPos,
							guiPacket.getDataString());
					ElevatorsCore.checkedProperties.put(chunkPos, var9);
					ElevatorsCore.refreshElevator(entityplayermp.worldObj,
							chunkPos);
					break;

				default:
					if (guiCommandValue >= 1
							&& guiCommandValue <= ElevatorsCore.max_elevator_Y
							&& guiCommandValue <= var8.numFloors()
							&& guiCommandValue != var8.curFloor()) {
						ElevatorsCore.elevator_requestFloor(
								entityplayermp.worldObj, chunkPos,
								guiCommandValue);

						if (ElevatorsCore.elevatorRequests
								.containsKey(Integer.valueOf(entityID))) {
							ElevatorsCore.elevatorRequests.remove(Integer
									.valueOf(entityID));
						}
					}
				}
			} else if (guiPacket.getCommand().equals("GUI_COMMUNICATION_ERROR")) {
				entityID = entityplayermp.entityId;

				if (ElevatorsCore.elevatorRequests.containsKey(Integer
						.valueOf(entityID))) {
					ElevatorsCore.elevatorRequests.remove(Integer
							.valueOf(entityID));
				}
			}
/*			} catch (Exception var12) {
				ElevatorsCore.say(var12.getMessage(), true);
				ElevatorsCore.say("Unable to complete elevator request.", true);
			}*/
		}
	}

	@Override
	public boolean sendResponseGUIPacket(int guiTransferID, int packetCode,
			String[] floors, boolean[] properties) {
		return false;
	}
}
