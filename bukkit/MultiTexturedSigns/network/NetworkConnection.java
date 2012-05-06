package MultiTexturedSigns.network;

import EurysMods.network.INetworkConnections;
import MultiTexturedSigns.MTSCore;
import MultiTexturedSigns.MultiTexturedSigns;
import forge.MessageManager;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.server.ModLoader;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;

public class NetworkConnection implements INetworkConnections
{
    private static String modName = MultiTexturedSigns.MTS.getModName();
    private static String modChannel = MultiTexturedSigns.MTS.getModChannel();
    private static String modVersion = MTSCore.version;

    public void onPacketData(NetworkManager var1, String var2, byte[] var3)
    {
        DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var3));

        try
        {
            NetServerHandler var5 = (NetServerHandler)var1.getNetHandler();
            int var6 = var4.read();

            switch (var6)
            {
                case 0:
                    PacketUpdateMTSign var7 = new PacketUpdateMTSign();
                    var7.readData(var4);
                    MultiTexturedSigns.MTS.getPacketHandler().handleTileEntityPacket(var7, var5.getPlayerEntity());
            }
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
        }
    }

    public void onConnect(NetworkManager var1) {}

    public void onLogin(NetworkManager var1, Packet1Login var2)
    {
        MessageManager.getInstance().registerChannel(var1, this, modChannel);
        ModLoader.getMinecraftServerInstance().sendMessage("Channel Registered: " + modName + " " + modVersion);
    }

    public void onDisconnect(NetworkManager var1, String var2, Object[] var3) {}
}
