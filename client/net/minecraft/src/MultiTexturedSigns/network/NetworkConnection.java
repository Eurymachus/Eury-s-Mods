package net.minecraft.src.MultiTexturedSigns.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections
{
	private static String modChannel = MultiTexturedSigns.MTS.getModChannel();
	
	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes) 
	{
		//mc.thePlayer.addChatMessage("Client Packet Received from Channel: " + channel);
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			NetClientHandler net = (NetClientHandler)network.getNetHandler();
			EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
			int packetID = data.read();
			//mc.thePlayer.addChatMessage("Client Packet ID: " + packetID);
			switch (packetID) {
			case PacketIds.MTSIGN_UPDATE:
				PacketUpdateMTSign packetSign = new PacketUpdateMTSign();
				packetSign.readData(data);
				MultiTexturedSigns.MTS.getPacketHandler().handleTileEntityPacket(packetSign, player);
				break;
			case PacketIds.MTSIGN_GUI:
				PacketOpenGui packetGui = new PacketOpenGui();
				packetGui.readData(data);
				player.addChatMessage("PacketX: " + packetGui.xPosition);
				player.addChatMessage("PacketY: " + packetGui.yPosition);
				player.addChatMessage("PacketZ: " + packetGui.zPosition);
				MultiTexturedSigns.MTS.getPacketHandler().handleGuiPacket(packetGui, player);
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
