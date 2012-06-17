package net.minecraft.src.MultiTexturedPPlates.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedPPlates.MTPCore;
import net.minecraft.src.MultiTexturedPPlates.MultiTexturedPPlates;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = MultiTexturedPPlates.Core.getModName();
	private static String modVersion = MTPCore.version;
	private static String modChannel = MultiTexturedPPlates.Core
			.getModChannel();

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = MultiTexturedPPlates.Core.getProxy()
					.getWorld(network);
			EntityPlayer entityplayer = MultiTexturedPPlates.Core.getProxy()
					.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTPPLATE_UPDATE:
				PacketUpdateMTPPlate packetPPlate = new PacketUpdateMTPPlate();
				packetPPlate.readData(data);
				MultiTexturedPPlates.Core.getPacketHandler()
						.handleTileEntityPacket(packetPPlate, entityplayer);
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
