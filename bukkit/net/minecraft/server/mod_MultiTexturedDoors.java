package net.minecraft.server;

import MultiTexturedDoors.MTDCore;
import forge.NetworkMod;

public class mod_MultiTexturedDoors extends NetworkMod
{
    public static NetworkMod instance;

    public String getVersion()
    {
        return MTDCore.version;
    }

    public void modsLoaded()
    {
        if (ModLoader.isModLoaded("mod_EurysMods"))
        {
            instance = this;
            MTDCore.initialize();
        }
        else
        {
            ModLoader.getLogger().warning("[MultiTexturedDoors] requires EurysMods-Core to work.");
        }
    }

    public void load() {}

    public boolean clientSideRequired()
    {
        return true;
    }

    public boolean serverSideRequired()
    {
        return true;
    }
}
