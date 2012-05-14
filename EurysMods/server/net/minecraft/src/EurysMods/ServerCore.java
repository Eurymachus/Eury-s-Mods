package net.minecraft.src.EurysMods;

import java.util.Arrays;

import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.EurysMods.network.IPacketHandling;

public class ServerCore implements ICore
{	
	private String modName;
	private String modDir;
	private String modChannel;
	public IProxy proxy;
	public IPacketHandling packetHandle;
	
	public ServerCore(IProxy proxyParam, IPacketHandling packetHandlesParam)
	{
		this(proxyParam);
        this.packetHandle = packetHandlesParam;
	}
	
	public ServerCore(IProxy proxyParam)
	{
		this.proxy = proxyParam;
	}

	public void setModName(String name)
	{
		this.modName = name;
		this.setModDir(modName);
	}

	public void setModDir(String dir)
	{
		this.modDir = "/" + dir + "/";
	}

	public String getModName()
	{
		return this.modName;
	}

	public String getModDir()
	{
		return this.modDir;
	}

	@Override
	public void setModChannel(String channel)
	{
		this.modChannel = channel;
	}

	@Override
	public String getModChannel()
	{
		return this.modChannel;
	}

	public IPacketHandling getPacketHandler()
	{
		return this.packetHandle;
	}

	public IProxy getProxy()
	{
		return this.proxy;
	}

	@Override
	public String getBlockSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlockSheet(String sheet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItemSheet(String sheet) {
		// TODO Auto-generated method stub
		
	}
}
