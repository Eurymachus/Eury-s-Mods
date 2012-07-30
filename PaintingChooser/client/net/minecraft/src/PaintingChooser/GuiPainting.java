package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

public class GuiPainting extends GuiScreen
{
    private EnumArt selected;
    private int xSize = 248;
    private int ySize = 207;
    private EntityPaintings myPainting;
    private ArrayList possiblePaintings;
    public EnumArt allArt;

    public GuiPainting(EntityPaintings entitypaintings, ArrayList artList)
    {
        this.myPainting = entitypaintings;
        this.possiblePaintings = artList;
        this.selected = EnumArt.Kebab;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 205, (this.height - this.ySize) / 2 + 180, 38, 20, "Random"));
        this.addPaintingsToGui();
    }

    private void addPaintingsToGui()
    {
        for (int var1 = 0; var1 < this.possiblePaintings.size(); ++var1)
        {
            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Kebab)
            {
                this.controlList.add(new GuiButtonPic(1, (this.width - this.xSize) / 2 + 143, (this.height - this.ySize) / 2 + 89, EnumArt.Kebab.offsetX, EnumArt.Kebab.offsetY, EnumArt.Kebab.sizeX, EnumArt.Kebab.sizeY));
                this.controlList.add(new GuiButtonPic(2, (this.width - this.xSize) / 2 + 161, (this.height - this.ySize) / 2 + 89, EnumArt.Aztec.offsetX, EnumArt.Aztec.offsetY, EnumArt.Aztec.sizeX, EnumArt.Aztec.sizeY));
                this.controlList.add(new GuiButtonPic(3, (this.width - this.xSize) / 2 + 179, (this.height - this.ySize) / 2 + 89, EnumArt.Alban.offsetX, EnumArt.Alban.offsetY, EnumArt.Alban.sizeX, EnumArt.Alban.sizeY));
                this.controlList.add(new GuiButtonPic(4, (this.width - this.xSize) / 2 + 197, (this.height - this.ySize) / 2 + 89, EnumArt.Aztec2.offsetX, EnumArt.Aztec2.offsetY, EnumArt.Aztec2.sizeX, EnumArt.Aztec2.sizeY));
                this.controlList.add(new GuiButtonPic(5, (this.width - this.xSize) / 2 + 143, (this.height - this.ySize) / 2 + 107, EnumArt.Bomb.offsetX, EnumArt.Bomb.offsetY, EnumArt.Bomb.sizeX, EnumArt.Bomb.sizeY));
                this.controlList.add(new GuiButtonPic(6, (this.width - this.xSize) / 2 + 161, (this.height - this.ySize) / 2 + 107, EnumArt.Plant.offsetX, EnumArt.Plant.offsetY, EnumArt.Plant.sizeX, EnumArt.Plant.sizeY));
                this.controlList.add(new GuiButtonPic(7, (this.width - this.xSize) / 2 + 179, (this.height - this.ySize) / 2 + 107, EnumArt.Wasteland.offsetX, EnumArt.Wasteland.offsetY, EnumArt.Wasteland.sizeX, EnumArt.Wasteland.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Pool)
            {
                this.controlList.add(new GuiButtonPic(8, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 27, EnumArt.Pool.offsetX, EnumArt.Pool.offsetY, EnumArt.Pool.sizeX, EnumArt.Pool.sizeY));
                this.controlList.add(new GuiButtonPic(9, (this.width - this.xSize) / 2 + 41, (this.height - this.ySize) / 2 + 27, EnumArt.Courbet.offsetX, EnumArt.Courbet.offsetY, EnumArt.Courbet.sizeX, EnumArt.Courbet.sizeY));
                this.controlList.add(new GuiButtonPic(10, (this.width - this.xSize) / 2 + 75, (this.height - this.ySize) / 2 + 27, EnumArt.Sea.offsetX, EnumArt.Sea.offsetY, EnumArt.Sea.sizeX, EnumArt.Sea.sizeY));
                this.controlList.add(new GuiButtonPic(11, (this.width - this.xSize) / 2 + 109, (this.height - this.ySize) / 2 + 27, EnumArt.Sunset.offsetX, EnumArt.Sunset.offsetY, EnumArt.Sunset.sizeX, EnumArt.Sunset.sizeY));
                this.controlList.add(new GuiButtonPic(12, (this.width - this.xSize) / 2 + 143, (this.height - this.ySize) / 2 + 27, EnumArt.Creebet.offsetX, EnumArt.Creebet.offsetY, EnumArt.Creebet.sizeX, EnumArt.Creebet.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Wanderer)
            {
                this.controlList.add(new GuiButtonPic(13, (this.width - this.xSize) / 2 + 207, (this.height - this.ySize) / 2 + 10, EnumArt.Wanderer.offsetX, EnumArt.Wanderer.offsetY, EnumArt.Wanderer.sizeX, EnumArt.Wanderer.sizeY));
                this.controlList.add(new GuiButtonPic(14, (this.width - this.xSize) / 2 + 225, (this.height - this.ySize) / 2 + 10, EnumArt.Graham.offsetX, EnumArt.Graham.offsetY, EnumArt.Graham.sizeX, EnumArt.Graham.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Match)
            {
                this.controlList.add(new GuiButtonPic(15, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 47, EnumArt.Match.offsetX, EnumArt.Match.offsetY, EnumArt.Match.sizeX, EnumArt.Match.sizeY));
                this.controlList.add(new GuiButtonPic(16, (this.width - this.xSize) / 2 + 41, (this.height - this.ySize) / 2 + 47, EnumArt.Bust.offsetX, EnumArt.Bust.offsetY, EnumArt.Bust.sizeX, EnumArt.Bust.sizeY));
                this.controlList.add(new GuiButtonPic(17, (this.width - this.xSize) / 2 + 75, (this.height - this.ySize) / 2 + 47, EnumArt.Stage.offsetX, EnumArt.Stage.offsetY, EnumArt.Stage.sizeX, EnumArt.Stage.sizeY));
                this.controlList.add(new GuiButtonPic(18, (this.width - this.xSize) / 2 + 109, (this.height - this.ySize) / 2 + 47, EnumArt.Void.offsetX, EnumArt.Void.offsetY, EnumArt.Void.sizeX, EnumArt.Void.sizeY));
                this.controlList.add(new GuiButtonPic(19, (this.width - this.xSize) / 2 + 143, (this.height - this.ySize) / 2 + 47, EnumArt.SkullAndRoses.offsetX, EnumArt.SkullAndRoses.offsetY, EnumArt.SkullAndRoses.sizeX, EnumArt.SkullAndRoses.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Fighters)
            {
                this.controlList.add(new GuiButtonPic(20, (this.width - this.xSize) / 2 + 177, (this.height - this.ySize) / 2 + 47, EnumArt.Fighters.offsetX, EnumArt.Fighters.offsetY, EnumArt.Fighters.sizeX, EnumArt.Fighters.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Pointer)
            {
                this.controlList.add(new GuiButtonPic(21, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 135, EnumArt.Pointer.offsetX, EnumArt.Pointer.offsetY, EnumArt.Pointer.sizeX, EnumArt.Pointer.sizeY));
                this.controlList.add(new GuiButtonPic(22, (this.width - this.xSize) / 2 + 73, (this.height - this.ySize) / 2 + 135, EnumArt.Pigscene.offsetX, EnumArt.Pigscene.offsetY, EnumArt.Pigscene.sizeX, EnumArt.Pigscene.sizeY));
                this.controlList.add(new GuiButtonPic(23, (this.width - this.xSize) / 2 + 139, (this.height - this.ySize) / 2 + 135, EnumArt.BurningSkull.offsetX, EnumArt.BurningSkull.offsetY, EnumArt.BurningSkull.sizeX, EnumArt.BurningSkull.sizeY));
            }

            if ((EnumArt)this.possiblePaintings.get(var1) == EnumArt.Skeleton)
            {
                this.controlList.add(new GuiButtonPic(24, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 83, EnumArt.Skeleton.offsetX, EnumArt.Skeleton.offsetY, EnumArt.Skeleton.sizeX, EnumArt.Skeleton.sizeY));
                this.controlList.add(new GuiButtonPic(25, (this.width - this.xSize) / 2 + 73, (this.height - this.ySize) / 2 + 83, EnumArt.DonkeyKong.offsetX, EnumArt.DonkeyKong.offsetY, EnumArt.DonkeyKong.sizeX, EnumArt.DonkeyKong.sizeY));
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char var1, int var2)
    {
        if (var2 == 1 || var2 == this.mc.gameSettings.keyBindInventory.keyCode)
        {
            this.myPainting.dropItem(PChooserCore.itemPaintings.shiftedIndex, 1);
            this.myPainting.setDead();
            this.mc.thePlayer.closeScreen();
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        if (var1.enabled)
        {
            if (var1.id == 0)
            {
                this.myPainting.setPainting((EnumArt)this.possiblePaintings.get((new Random()).nextInt(this.possiblePaintings.size())));
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 1)
            {
                this.myPainting.setPainting(EnumArt.Kebab);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 2)
            {
                this.myPainting.setPainting(EnumArt.Aztec);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 3)
            {
                this.myPainting.setPainting(EnumArt.Alban);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 4)
            {
                this.myPainting.setPainting(EnumArt.Aztec2);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 5)
            {
                this.myPainting.setPainting(EnumArt.Bomb);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 6)
            {
                this.myPainting.setPainting(EnumArt.Plant);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 7)
            {
                this.myPainting.setPainting(EnumArt.Wasteland);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 8)
            {
                this.myPainting.setPainting(EnumArt.Pool);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 9)
            {
                this.myPainting.setPainting(EnumArt.Courbet);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 10)
            {
                this.myPainting.setPainting(EnumArt.Sea);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 11)
            {
                this.myPainting.setPainting(EnumArt.Sunset);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 12)
            {
                this.myPainting.setPainting(EnumArt.Creebet);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 13)
            {
                this.myPainting.setPainting(EnumArt.Wanderer);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 14)
            {
                this.myPainting.setPainting(EnumArt.Graham);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 15)
            {
                this.myPainting.setPainting(EnumArt.Match);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 16)
            {
                this.myPainting.setPainting(EnumArt.Bust);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 17)
            {
                this.myPainting.setPainting(EnumArt.Stage);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 18)
            {
                this.myPainting.setPainting(EnumArt.Void);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 19)
            {
                this.myPainting.setPainting(EnumArt.SkullAndRoses);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 20)
            {
                this.myPainting.setPainting(EnumArt.Fighters);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 21)
            {
                this.myPainting.setPainting(EnumArt.Pointer);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 22)
            {
                this.myPainting.setPainting(EnumArt.Pigscene);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 23)
            {
                this.myPainting.setPainting(EnumArt.BurningSkull);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 24)
            {
                this.myPainting.setPainting(EnumArt.Skeleton);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 25)
            {
                this.myPainting.setPainting(EnumArt.DonkeyKong);
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {}

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        int var6 = this.mc.renderEngine.getTexture("/PaintingChooser/gui/paintings.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var6);
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        this.fontRenderer.drawString("Choose a painting:", var4 + 10, var5 + 10, 4210752);
        super.drawScreen(var1, var2, var3);
    }
}
