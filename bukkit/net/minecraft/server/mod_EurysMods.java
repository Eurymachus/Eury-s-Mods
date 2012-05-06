package net.minecraft.server;

import EurysMods.EurysCore;
import forge.NetworkMod;

public class mod_EurysMods extends NetworkMod
{
    public static mod_EurysMods instance;

    public String getVersion()
    {
        return EurysCore.version;
    }

    public void modsLoaded()
    {
        instance = this;
        EurysCore.initialize();
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
