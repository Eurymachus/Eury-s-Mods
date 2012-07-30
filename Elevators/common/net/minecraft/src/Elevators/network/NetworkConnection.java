package net.minecraft.src.Elevators.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.Elevators;
import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = Elevators.Core.getModName();
	private static String modVersion = ElevatorsCore.version;
	private static String modChannel = Elevators.Core.getModChannel();

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = Elevators.Core.getProxy().getWorld(network);
			EntityPlayer entityplayer = Elevators.Core.getProxy().getPlayer(
					network);
			int packetID = data.read();
			switch (packetID) {
			case 0:
				PacketUpdateElevator packetElevator = new PacketUpdateElevator();
				packetElevator.readData(data);
				((ElevatorsPacketHandling) Elevators.Core.getPacketHandler())
						.handlePacket(packetElevator, entityplayer, world);
				break;
			case 1:
				PacketElevatorGui packetElevatorGui = new PacketElevatorGui();
				packetElevatorGui.readData(data);
				((ElevatorsPacketHandling) Elevators.Core.getPacketHandler())
						.handlePacket(packetElevatorGui, entityplayer, world);
				break;
			case 2:
				PacketUpdateRiders packetRiders = new PacketUpdateRiders();
				packetRiders.readData(data);
				((ElevatorsPacketHandling) Elevators.Core.getPacketHandler())
						.handlePacket(packetRiders, entityplayer, world);
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
