package MultiTexturedLevers.network;

import EurysMods.network.IPacketHandling;
import EurysMods.network.PacketUpdate;
import MultiTexturedLevers.TileEntityMTLever;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class PacketHandles implements IPacketHandling
{
    public void handleTileEntityPacket(PacketUpdate var1, EntityHuman var2)
    {
        if (var1 != null && var1 instanceof PacketUpdateMTLever)
        {
            PacketUpdateMTLever var3 = (PacketUpdateMTLever)var1;
            Object var4 = null;
            World var5 = var2.world;

            if (!var3.targetExists(var5))
            {
                return;
            }

            TileEntity var6 = var3.getTarget(var5);

            if (var6 != null && var6 instanceof TileEntityMTLever)
            {
                TileEntityMTLever var7 = (TileEntityMTLever)var6;
                var7.handleUpdatePacket(var3, var5);
            }
        }
    }

    public void handleGuiPacket(PacketUpdate var1, EntityHuman var2) {}
}
