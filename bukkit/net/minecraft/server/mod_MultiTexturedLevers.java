package net.minecraft.server;

import MultiTexturedLevers.MTLCore;
import forge.NetworkMod;

public class mod_MultiTexturedLevers extends NetworkMod
{
    public static NetworkMod instance;

    public String getVersion()
    {
        return MTLCore.version;
    }

    public void modsLoaded()
    {
        if (ModLoader.isModLoaded("mod_EurysMods"))
        {
            instance = this;
            MTLCore.initialize();
        }
        else
        {
            ModLoader.getLogger().warning("[MultiTexturedLevers] requires EurysMods-Core to work.");
        }
    }

    public void load() {}

    public boolean clientSideRequired()
    {
        return true;
    }

    public boolean serverSideRequired()
    {
        return false;
    }
}
