package EurysMods.core;

import EurysMods.network.IPacketHandling;

public interface ICore
{
    String getBlockSheet();

    String getItemSheet();

    void setBlockSheet(String var1);

    void setItemSheet(String var1);

    void setModName(String var1);

    void setModDir(String var1);

    void setModChannel(String var1);

    String getModName();

    String getModDir();

    String getModChannel();

    IPacketHandling getPacketHandler();

    IProxy getProxy();
}
