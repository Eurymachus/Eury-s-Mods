package net.minecraft.src;

import java.io.File;

import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_EurysMods extends NetworkMod
{
	public String getVersion() { return EurysCore.version; }
    
	public static mod_EurysMods instance;
	
    public void modsLoaded()
    {
    	instance = this;
    	EurysCore.initialize();
    }
    
    public mod_EurysMods()
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