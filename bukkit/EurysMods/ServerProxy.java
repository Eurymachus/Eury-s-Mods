package EurysMods;

import EurysMods.core.IProxy;
import EurysMods.network.PacketPayload;
import forge.DimensionManager;
import forge.NetworkMod;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.World;

public class ServerProxy implements IProxy
{
    public PacketPayload getPayload(int[] var1, float[] var2, String[] var3)
    {
        int var4;

        if (var1 != null)
        {
            var4 = var1.length;
        }
        else
        {
            var4 = 0;
        }

        int var5;

        if (var2 != null)
        {
            var5 = var2.length;
        }
        else
        {
            var5 = 0;
        }

        int var6;

        if (var3 != null)
        {
            var6 = var3.length;
        }
        else
        {
            var6 = 0;
        }

        PacketPayload var7 = new PacketPayload(var4, var5, var6);
        var7.intPayload = var1;
        var7.floatPayload = var2;
        var7.stringPayload = var3;
        return var7;
    }

    public void sendPacket(EntityHuman var1, Packet var2)
    {
        EntityPlayer var3 = (EntityPlayer)var1;
        var3.netServerHandler.networkManager.queue(var2);
    }

    public void sendPacketToAll(Packet var1, int var2, int var3, int var4, int var5, NetworkMod var6)
    {
        if (var1 != null)
        {
            World[] var7 = DimensionManager.getWorlds();

            for (int var8 = 0; var8 < var7.length; ++var8)
            {
                for (int var9 = 0; var9 < var7[var8].players.size(); ++var9)
                {
                    EntityPlayer var10 = (EntityPlayer)var7[var8].players.get(var9);

                    if (Math.abs(var10.locX - (double)var2) <= (double)var5 && Math.abs(var10.locY - (double)var3) <= (double)var5 && Math.abs(var10.locZ - (double)var4) <= (double)var5)
                    {
                        var10.netServerHandler.networkManager.queue(var1);
                    }
                }
            }
        }
    }
}
