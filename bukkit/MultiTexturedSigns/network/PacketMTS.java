package MultiTexturedSigns.network;

import EurysMods.network.PacketUpdate;
import MultiTexturedSigns.MultiTexturedSigns;

public class PacketMTS extends PacketUpdate
{
    public PacketMTS(int var1)
    {
        super(var1);
        this.channel = MultiTexturedSigns.MTS.getModChannel();
    }
}
