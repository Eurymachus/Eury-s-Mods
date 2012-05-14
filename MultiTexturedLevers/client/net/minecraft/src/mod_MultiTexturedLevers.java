package net.minecraft.src;

import java.io.File;

import net.minecraft.src.MultiTexturedLevers.MTLCore;
import net.minecraft.src.MultiTexturedLevers.RenderMTLever;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedLevers extends NetworkMod
{
	public String getVersion() { return MTLCore.version; }
	
	public static NetworkMod instance;
    
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

    public mod_MultiTexturedLevers()
    {
    }
    
    public void load()
    {
    }
    
    @Override
    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
    	return RenderMTLever.renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
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