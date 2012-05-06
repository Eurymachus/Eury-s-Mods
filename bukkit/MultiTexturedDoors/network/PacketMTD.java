package MultiTexturedDoors.network;

import EurysMods.network.PacketUpdate;
import MultiTexturedDoors.MultiTexturedDoors;

public class PacketMTD extends PacketUpdate
{
    public PacketMTD(int var1)
    {
        super(var1);
        this.channel = MultiTexturedDoors.Core.getModChannel();
    }
}
