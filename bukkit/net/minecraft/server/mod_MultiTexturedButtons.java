package net.minecraft.server;

import MultiTexturedButtons.MTBCore;
import forge.NetworkMod;

public class mod_MultiTexturedButtons extends NetworkMod
{
    public static NetworkMod instance;

    public String getVersion()
    {
        return MTBCore.version;
    }

    public void modsLoaded()
    {
        if (ModLoader.isModLoaded("mod_EurysMods"))
        {
            instance = this;
            MTBCore.initialize();
        }
        else
        {
            ModLoader.getLogger().warning("[MultiTexturedButtons] requires EurysMods-Core to work.");
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
