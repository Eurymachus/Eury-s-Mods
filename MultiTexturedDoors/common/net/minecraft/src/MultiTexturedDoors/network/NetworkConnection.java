package net.minecraft.src.MultiTexturedDoors.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedDoors.MTDCore;
import net.minecraft.src.MultiTexturedDoors.MultiTexturedDoors;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = MultiTexturedDoors.Core.getModName();
	private static String modVersion = MTDCore.version;
	private static String modChannel = MultiTexturedDoors.Core.getModChannel();

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = MultiTexturedDoors.Core.getProxy().getWorld(network);
			EntityPlayer entityplayer = MultiTexturedDoors.Core.getProxy()
					.getPlayer(network);
			
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTDOOR_UPDATE:
				PacketUpdateMTDoor packetDoor = new PacketUpdateMTDoor();
				packetDoor.readData(data);
				MultiTexturedDoors.Core.getPacketHandler()
						.handleTileEntityPacket(packetDoor, entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login) {
		MessageManager.getInstance().registerChannel(network, this, modChannel);
		ModLoader.getLogger().fine(
				"Channel[" + modChannel + "] Registered: " + modName + " "
						+ modVersion);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {

	}
}
