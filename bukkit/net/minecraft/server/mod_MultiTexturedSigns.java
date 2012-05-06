package net.minecraft.server;

import MultiTexturedSigns.MTSCore;
import forge.NetworkMod;

public class mod_MultiTexturedSigns extends NetworkMod
{
    public static NetworkMod instance;

    public String getVersion()
    {
        return MTSCore.version;
    }

    public void modsLoaded()
    {
        if (ModLoader.isModLoaded("mod_EurysMods"))
        {
            instance = this;
            MTSCore.initialize();
        }
        else
        {
            ModLoader.getLogger().warning("[MultiTexturedSigns] requires EurysMods-Core to work.");
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
