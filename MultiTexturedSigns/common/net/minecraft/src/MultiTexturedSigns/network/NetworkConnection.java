package net.minecraft.src.MultiTexturedSigns.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedSigns.MTSCore;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = MultiTexturedSigns.MTS.getModName();
	private static String modChannel = MultiTexturedSigns.MTS.getModChannel();
	private static String modVersion = MTSCore.version;

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = MultiTexturedSigns.MTS.getProxy().getWorld(network);
			EntityPlayer entityplayer = MultiTexturedSigns.MTS.getProxy().getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTSIGN_UPDATE:
				PacketUpdateMTSign packetSign = new PacketUpdateMTSign();
				packetSign.readData(data);
				MultiTexturedSigns.MTS.getPacketHandler()
						.handleTileEntityPacket(packetSign,
								entityplayer);
				break;
			case PacketIds.MTSIGN_GUI:
				PacketOpenGui packetGui = new PacketOpenGui();
				packetGui.readData(data);
				MultiTexturedSigns.MTS.getPacketHandler().handleGuiPacket(
						packetGui, entityplayer);
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
				"Channel Registered: " + modName + " " + modVersion);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {

	}
}
