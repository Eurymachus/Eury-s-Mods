package net.minecraft.src.MultiTexturedButtons.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedButtons.MultiTexturedButtons;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections
{
	private static String modChannel = MultiTexturedButtons.MTBCore.getModChannel();
	
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
			case PacketIds.MTBUTTON_UPDATE:
				PacketUpdateMTButton packetButton = new PacketUpdateMTButton();
				packetButton.readData(data);
				//mc.thePlayer.addChatMessage("Meta: " + packetButton.getItemDamage());
				MultiTexturedButtons.MTBCore.getPacketHandler().handleTileEntityPacket(packetButton, null);
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
