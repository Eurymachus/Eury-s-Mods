package net.minecraft.src.PaintingChooser.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.INetworkConnections;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.PaintingChooser.PChooserCore;
import net.minecraft.src.PaintingChooser.PaintingChooser;
import net.minecraft.src.forge.MessageManager;

public class NetworkConnection implements INetworkConnections {
	private static String modName = PaintingChooser.PChooser.getModName();
	private static String modVersion = PChooserCore.version;
	private static String modChannel = PaintingChooser.PChooser.getModChannel();

	@Override
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			World world = PaintingChooser.PChooser.getProxy().getWorld(network);
			EntityPlayer entityplayer = PaintingChooser.PChooser.getProxy()
					.getPlayer(network);
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.PAINTING_UPDATE:
				PacketUpdatePainting packetPainting = new PacketUpdatePainting();
				packetPainting.readData(data);
				((IPaintingPacketHandling)PaintingChooser.PChooser.getPacketHandler())
						.handlePacket(packetPainting, entityplayer, world);
				break;
			case PacketIds.PAINTING_GUI:
				PacketPaintingGui packetPaintingGui = new PacketPaintingGui();
				packetPaintingGui.readData(data);
				PaintingChooser.PChooser.getPacketHandler()
						.handleGuiPacket(packetPaintingGui, entityplayer, world);
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
