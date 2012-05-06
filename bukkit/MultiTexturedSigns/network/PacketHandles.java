package MultiTexturedSigns.network;

import EurysMods.network.IPacketHandling;
import EurysMods.network.PacketUpdate;
import MultiTexturedSigns.TileEntityMTSign;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class PacketHandles implements IPacketHandling
{
    public void handleTileEntityPacket(PacketUpdate var1, EntityHuman var2)
    {
        if (var1 != null && var1 instanceof PacketUpdateMTSign)
        {
            PacketUpdateMTSign var3 = (PacketUpdateMTSign)var1;
            EntityPlayer var4 = (EntityPlayer)var2;
            World var5 = var4.world;

            if (var3.targetExists(var5))
            {
                TileEntity var6 = var3.getTarget(var5);

                if (var6 != null && var6 instanceof TileEntityMTSign)
                {
                    TileEntityMTSign var7 = (TileEntityMTSign)var6;
                    var7.handleUpdatePacket(var3, var5);
                }
            }
        }
    }

    public void handleGuiPacket(PacketUpdate var1, EntityHuman var2) {}
}
