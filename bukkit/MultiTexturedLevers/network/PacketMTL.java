package MultiTexturedLevers.network;

import EurysMods.network.PacketUpdate;
import MultiTexturedLevers.MultiTexturedLevers;

public class PacketMTL extends PacketUpdate
{
    public PacketMTL(int var1)
    {
        super(var1);
        this.channel = MultiTexturedLevers.Core.getModChannel();
    }
}
