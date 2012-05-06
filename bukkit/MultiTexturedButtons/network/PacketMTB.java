package MultiTexturedButtons.network;

import EurysMods.network.PacketUpdate;
import MultiTexturedButtons.MultiTexturedButtons;

public abstract class PacketMTB extends PacketUpdate
{
    public PacketMTB(int var1)
    {
        super(var1);
        this.channel = MultiTexturedButtons.Core.getModChannel();
    }
}
