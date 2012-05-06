package EurysMods.network;

import forge.IConnectionHandler;
import forge.IPacketHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;

public interface INetworkConnections extends IConnectionHandler, IPacketHandler
{
    void onPacketData(NetworkManager var1, String var2, byte[] var3);

    void onConnect(NetworkManager var1);

    void onLogin(NetworkManager var1, Packet1Login var2);

    void onDisconnect(NetworkManager var1, String var2, Object[] var3);
}
