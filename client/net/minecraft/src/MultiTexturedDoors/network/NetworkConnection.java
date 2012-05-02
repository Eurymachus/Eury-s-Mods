package net.minecraft.src.MultiTexturedDoors.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedDoors.MultiTexturedDoors;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections
{
	private static String modChannel = MultiTexturedDoors.MTDCore.getModChannel();
	
	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes) 
	{
		//mc.thePlayer.addChatMessage("Client Packet Received");
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			NetClientHandler net = (NetClientHandler)network.getNetHandler();

			int packetID = data.read();
			switch (packetID) {
			case PacketIds.MTDOOR_UPDATE:
				//mc.thePlayer.addChatMessage("PacketID is Door");
				PacketUpdateMTDoor packetDoor = new PacketUpdateMTDoor();
				packetDoor.readData(data);
				MultiTexturedDoors.MTDCore.getPacketHandler().handleTileEntityPacket(packetDoor, null);
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
		//mc.thePlayer.addChatMessage("Client Channel Registered");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args)
	{
		
	}
}
