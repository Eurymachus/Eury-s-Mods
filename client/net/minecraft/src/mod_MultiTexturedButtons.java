package net.minecraft.src;

import java.io.File;

import net.minecraft.src.MultiTexturedButtons.MTBCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedButtons extends NetworkMod
{
	public String getVersion() { return MTBCore.version; }
	
	public static NetworkMod instance;
    
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
    
    public mod_MultiTexturedButtons()
    {
    }
    
    public void load()
    {
    }

	@Override
	public boolean clientSideRequired()
	{
		return true;
	}

	@Override
	public boolean serverSideRequired()
	{
		return false;
	}
}