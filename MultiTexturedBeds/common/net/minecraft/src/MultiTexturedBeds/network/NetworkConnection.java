package net.minecraft.src.MultiTexturedBeds.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedBeds.MTBedsCore;
import net.minecraft.src.MultiTexturedBeds.MultiTexturedBeds;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = MultiTexturedBeds.MTBed.getModName();
	private static String modVersion = MTBedsCore.version;
	private static String modChannel = MultiTexturedBeds.MTBed.getModChannel();

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = MultiTexturedBeds.MTBed.getProxy().getWorld(network);
			EntityPlayer entityplayer = MultiTexturedBeds.MTBed.getProxy()
					.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTBED_UPDATE:
				PacketUpdateMTBed packetBed = new PacketUpdateMTBed();
				packetBed.readData(data);
				MultiTexturedBeds.MTBed.getPacketHandler()
						.handleTileEntityPacket(packetBed, entityplayer, world);
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
