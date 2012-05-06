package EurysMods.network;

import net.minecraft.server.EntityHuman;

public interface IPacketHandling
{
    void handleTileEntityPacket(PacketUpdate var1, EntityHuman var2);

    void handleGuiPacket(PacketUpdate var1, EntityHuman var2);
}
