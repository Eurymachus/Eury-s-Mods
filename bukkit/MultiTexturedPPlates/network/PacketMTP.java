package MultiTexturedPPlates.network;

import EurysMods.network.PacketUpdate;
import MultiTexturedPPlates.MultiTexturedPPlates;

public class PacketMTP extends PacketUpdate
{
    public PacketMTP(int var1)
    {
        super(var1);
        this.channel = MultiTexturedPPlates.Core.getModChannel();
    }
}
