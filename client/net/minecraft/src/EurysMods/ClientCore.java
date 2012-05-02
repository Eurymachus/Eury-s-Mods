package net.minecraft.src.EurysMods;

import java.util.Arrays;

import net.minecraft.src.ModLoader;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.EurysMods.network.IPacketHandling;

public class ClientCore implements ICore
{
	private String modName;
	private String modDir;
	private String modChannel;
	private String itemSheet = "gui/items.png";
	private String blockSheet = "terrain.png";
	public IProxy proxy;
	public IPacketHandling packetHandle;
	
	public ClientCore(IProxy proxyParam, IPacketHandling packetHandlesParam)
    {
        this(proxyParam);
        this.packetHandle = packetHandlesParam;
    }

	public ClientCore(IProxy proxyParam)
    {
        this.proxy = proxyParam;
    }
	
	public String getBlockSheet()
	{
		String concat = this.modDir + this.blockSheet;
		return concat;
	}

	public String getItemSheet()
	{
		String concat = this.modDir + this.itemSheet;
		return concat;
	}
	
	public void setBlockSheet(String sheet)
	{
		this.blockSheet = sheet;
	}

	public void setItemSheet(String sheet)
	{
		this.itemSheet = sheet;
	}

	public void setModName(String name)
	{
		this.modName = name;
		this.setModDir(this.modName);
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
}
