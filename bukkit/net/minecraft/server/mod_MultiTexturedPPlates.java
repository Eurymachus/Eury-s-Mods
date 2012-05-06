package net.minecraft.server;

import MultiTexturedPPlates.MTPCore;
import forge.NetworkMod;

public class mod_MultiTexturedPPlates extends NetworkMod
{
    public static NetworkMod instance;

    public String getVersion()
    {
        return MTPCore.version;
    }

    public void modsLoaded()
    {
        if (ModLoader.isModLoaded("mod_EurysMods"))
        {
            instance = this;
            MTPCore.initialize();
        }
        else
        {
            ModLoader.getLogger().warning("[MultiTexturedPPlates] requires EurysMods-Core to work.");
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
