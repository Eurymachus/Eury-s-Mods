package EurysMods.core;

import EurysMods.network.PacketPayload;
import forge.NetworkMod;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Packet;

public interface IProxy
{
    PacketPayload getPayload(int[] var1, float[] var2, String[] var3);

    void sendPacket(EntityHuman var1, Packet var2);

    void sendPacketToAll(Packet var1, int var2, int var3, int var4, int var5, NetworkMod var6);
}
