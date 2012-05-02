package net.minecraft.src.MultiTexturedDoors.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedDoors.MTDCore;
import net.minecraft.src.MultiTexturedDoors.MultiTexturedDoors;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections
{
	private static String modChannel = MultiTexturedDoors.MTDCore.getModChannel();
	private static String modName = MultiTexturedDoors.MTDCore.getModName();
	private static String modVersion = MTDCore.version;
	
	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes) 
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			NetServerHandler net = (NetServerHandler)network.getNetHandler();

			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTDOOR_UPDATE:
				PacketUpdateMTDoor packetLever = new PacketUpdateMTDoor();
				packetLever.readData(data);
				MultiTexturedDoors.MTDCore.getPacketHandler().handleTileEntityPacket(packetLever, net.getPlayerEntity());
				break;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network)
	{
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login)
	{
		MessageManager.getInstance().registerChannel(network, this, modChannel);
		ModLoader.getMinecraftServerInstance().log("Channel Registered: " + modName + " " + modVersion);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args)
	{
		
	}
}
