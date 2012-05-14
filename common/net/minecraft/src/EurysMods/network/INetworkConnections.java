package net.minecraft.src.EurysMods.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;
import net.minecraft.src.forge.MessageManager;

public interface INetworkConnections extends IConnectionHandler, IPacketHandler
{
	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes);

	@Override
	public void onConnect(NetworkManager network);

	@Override
	public void onLogin(NetworkManager network, Packet1Login login);

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args);
}
