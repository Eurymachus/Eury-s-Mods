package EurysMods;

import EurysMods.core.ICore;
import EurysMods.core.IProxy;
import EurysMods.network.IPacketHandling;

public class ServerCore implements ICore
{
    private String modName;
    private String modDir;
    private String modChannel;
    public IProxy proxy;
    public IPacketHandling packetHandle;

    public ServerCore(IProxy var1, IPacketHandling var2)
    {
        this(var1);
        this.packetHandle = var2;
    }

    public ServerCore(IProxy var1)
    {
        this.proxy = var1;
    }

    public void setModName(String var1)
    {
        this.modName = var1;
        this.setModDir(this.modName);
    }

    public void setModDir(String var1)
    {
        this.modDir = "/" + var1 + "/";
    }

    public String getModName()
    {
        return this.modName;
    }

    public String getModDir()
    {
        return this.modDir;
    }

    public void setModChannel(String var1)
    {
        this.modChannel = var1;
    }

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

    public String getBlockSheet()
    {
        return null;
    }

    public String getItemSheet()
    {
        return null;
    }

    public void setBlockSheet(String var1) {}

    public void setItemSheet(String var1) {}
}
