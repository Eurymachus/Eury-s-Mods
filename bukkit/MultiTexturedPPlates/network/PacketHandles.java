package MultiTexturedPPlates.network;

import EurysMods.network.IPacketHandling;
import EurysMods.network.PacketUpdate;
import MultiTexturedPPlates.TileEntityMTPPlate;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class PacketHandles implements IPacketHandling
{
    public void handleTileEntityPacket(PacketUpdate var1, EntityHuman var2)
    {
        if (var1 != null && var1 instanceof PacketUpdateMTPPlate)
        {
            PacketUpdateMTPPlate var3 = (PacketUpdateMTPPlate)var1;
            World var4 = var2.world;

            if (!var3.targetExists(var4))
            {
                return;
            }

            TileEntity var5 = var3.getTarget(var4);

            if (var5 != null && var5 instanceof TileEntityMTPPlate)
            {
                TileEntityMTPPlate var6 = (TileEntityMTPPlate)var5;
                var6.handleUpdatePacket(var3, var4);
            }
        }
    }

    public void handleGuiPacket(PacketUpdate var1, EntityHuman var2) {}
}
