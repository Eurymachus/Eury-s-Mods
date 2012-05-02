package net.minecraft.src.EurysMods.core;

import net.minecraft.src.EurysMods.network.IPacketHandling;

public interface ICore
{
	String getBlockSheet();

	String getItemSheet();

	void setBlockSheet(String sheet);

	void setItemSheet(String sheet);

	void setModName(String name);

	void setModDir(String dir);

	void setModChannel(String string);

	String getModName();

	String getModDir();
	
	String getModChannel();

	IPacketHandling getPacketHandler();
	
	IProxy getProxy();
}
