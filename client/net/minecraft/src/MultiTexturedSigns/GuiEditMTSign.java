package net.minecraft.src.MultiTexturedSigns;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.MultiTexturedSigns.network.PacketUpdateMTSign;

public class GuiEditMTSign extends GuiScreen
{
	private Minecraft mc = ModLoader.getMinecraftInstance();
    protected String mtsScreenTitle;
    private TileEntityMTSign mtsEntitySign;
    private int updateCounter;
    private int mtsEditLine;
    private static final String allowedCharacters;

    public GuiEditMTSign(TileEntityMTSign mtstileentitysign)
    {
    	mtsScreenTitle = "Edit sign message:";
    	mtsEditLine = 0;
        mtsEntitySign = mtstileentitysign;
    }

    public void initGui()
    {
        controlList.clear();
        Keyboard.enableRepeatEvents(true);
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120, "Done"));
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        if (mc.theWorld.isRemote)
        {
        	mc.getSendQueue().addToSendQueue(new PacketUpdateMTSign(mtsEntitySign.xCoord, mtsEntitySign.yCoord, mtsEntitySign.zCoord, mtsEntitySign.getMetaValue(), mtsEntitySign.mtSignText).getPacket());
        }
    }

	public void updateScreen()
    {
        updateCounter++;
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled)
        {
            return;
        }
        if (guibutton.id == 0)
        {
        	mtsEntitySign.onInventoryChanged();
            mc.displayGuiScreen(null);
        }
    }

    protected void keyTyped(char c, int i)
    {
        if (i == 200)
        {
        	mtsEditLine = mtsEditLine - 1 & 3;
        }
        if (i == 208 || i == 28)
        {
        	mtsEditLine = mtsEditLine + 1 & 3;
        }
        if (i == 14 && mtsEntitySign.mtSignText[mtsEditLine].length() > 0)
        {
        	mtsEntitySign.mtSignText[mtsEditLine] = mtsEntitySign.mtSignText[mtsEditLine].substring(0, mtsEntitySign.mtSignText[mtsEditLine].length() - 1);
        }
        if (allowedCharacters.indexOf(c) >= 0 && mtsEntitySign.mtSignText[mtsEditLine].length() < 15)
        {
        	mtsEntitySign.mtSignText[mtsEditLine] += c;
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, mtsScreenTitle, width / 2, 40, 0xffffff);
        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2, 0.0F, 50F);
        float f1 = 93.75F;
        GL11.glScalef(-f1, -f1, -f1);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        Block block = mtsEntitySign.getBlockType();
        if (block == MTSCore.mtSignPost)
        {
            float f2 = (float)(mtsEntitySign.getBlockMetadata() * 360) / 16F;
            GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }
        else
        {
            int k = mtsEntitySign.getBlockMetadata();
            float f3 = 0.0F;
            if (k == 2)
            {
                f3 = 180F;
            }
            if (k == 4)
            {
                f3 = 90F;
            }
            if (k == 5)
            {
                f3 = -90F;
            }
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }
        if ((updateCounter / 6) % 2 == 0)
        {
        	mtsEntitySign.mtsLineBeingEdited = mtsEditLine;
        }
        TileEntityRenderer.instance.renderTileEntityAt(mtsEntitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        mtsEntitySign.mtsLineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }

    static
    {
        allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    }
}