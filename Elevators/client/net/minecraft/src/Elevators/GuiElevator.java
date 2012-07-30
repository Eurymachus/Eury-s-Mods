package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiElevatorList;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Elevators.network.ElevatorsPacketHandling;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiElevator extends GuiScreen {
	public static Minecraft minecraftInstance;
	public static final int GUI_OPTIONS = 198;
	public static final int GUI_RESET = 199;
	public static final int GUI_CANCEL = 200;
	public static final int GUI_OPTIONS_CANCEL = 201;
	public static final int GUI_OPTIONS_SLIDER = 202;
	public static final int GUI_OPTIONS_NAMESLIST = 203;
	public static final int GUI_OPTIONS_FLOORNAME = 204;
	public static final int GUI_OPTIONS_ELEVATORNAME = 205;
	public static final int GUI_OPTIONS_APPLY = 206;
	public static final int GUI_OPTIONS_POWER = 207;
	public static final int GUI_OPTIONS_HALT = 208;
	public static final int GUI_OPTIONS_MOBILE = 209;
	protected int xSize;
	protected int ySize;
	ChunkPosition elevatorPos;
	private GuiTextField txtElevatorName;
	private GuiTextField txtCurFloorName;
	private GuiElevatorSlider floorZeroSlider;
	private GuiElevatorList floorNamesList;
	private GuiButton RenameFloor;
	private GuiButton RenameElevator;
	private GuiElevatorRadialButton canProvidePower;
	private GuiElevatorRadialButton canBeHalted;
	private GuiElevatorRadialButton mobilePower;
	private List floorButtons;
	private boolean sentPacket;
	private boolean optionsOpen;
	boolean isRemote;
	int numFloors;
	int curFloor;
	int guiTransferID;
	protected int guiLeft;
	protected int guiTop;
	protected String screenTitle;
	private String screenSubtitle;
	private int buttonId;
	public String[] floors;
	boolean[] properties;
	public int floorOne;
	int colorID;
	int titleTop;
	int subtitleTop;

	public GuiElevator(int var1, int var2, int var3, String[] var4,
			boolean[] var5, ChunkPosition var6) {
		this(var1, var2, var3, var4, var5);
		GuiElevator.minecraftInstance = this.mc;
		this.elevatorPos = var6;
	}

	public GuiElevator(int var1, int var2, int var3, String[] var4,
			boolean[] var5) {
		this.xSize = 215;
		this.ySize = 213;
		this.floorButtons = new ArrayList();
		this.sentPacket = false;
		this.optionsOpen = false;
		this.isRemote = false;
		this.numFloors = 0;
		this.curFloor = 0;
		this.guiTransferID = 0;
		this.floorOne = 1;
		this.colorID = 0;
		this.titleTop = 0;
		this.subtitleTop = 0;
		this.numFloors = var2;
		this.curFloor = var3;
		this.guiTransferID = var1;
		this.isRemote = var1 != -1;
		this.buttonId = -1;
		this.screenTitle = "";
		this.floors = var4;


		this.floorOne = Integer.parseInt(this.floors[0]);

		this.colorID = Integer.parseInt(this.floors[1]);


		this.screenTitle = this.floors[2];

		this.properties = var5;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		StringTranslate var1 = StringTranslate.getInstance();
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		int var2 = this.numFloors;

		if (var2 > 70) {
			var2 = 70;
		}

		byte var3 = 5;

		if (var2 > 48) {
			var3 = 1;
		}

		if (var2 > 63) {
			var3 = 0;
		}

		boolean var4 = true;
		boolean var5 = true;
		int var6 = this.guiTop + 155;
		int var12;
		int var13;

		if (var3 == 5) {
			var12 = var2 % 6 == 1 ? 5 : 6;
			var13 = var2 > var12 * 2 ? (var2 - 1) / var12 + 1 : 2;

			if (var13 > 2) {
				while (var2 % var13 == 1) {
					++var13;
				}
			}

			if (var13 > 8) {
				var12 = 6;
				var13 = var2 > var12 * 2 ? (var2 - 1) / var12 + 1 : 2;
			}

			var6 = var12 != 6 && var2 >= (var12 - 1) * var13 + 1 ? this.guiTop + 130
					: this.guiTop + 155;
		} else {
			var12 = var2 % 7 == 1 ? 6 : 7;
			var13 = (var2 - 1) / var12 + 1;

			if (var13 > 10) {
				var12 = 7;
				var13 = (var2 - 1) / var12 + 1;
			}
		}

		ElevatorsCore.say("Size: " + var2 + "; rows: " + var12 + "; columns: "
				+ var13);
		int var7 = this.width / 2;

		if (ElevatorsCore.invertKeys) {
			var7 += (var13 / 2 - 1) * 20 + var13 / 2 * var3;

			if (var13 % 2 == 1) {
				var7 += 10;
			} else {
				var7 += var3 / 2;
			}
		} else {
			var7 -= var13 / 2 * (var3 + 20);

			if (var13 % 2 == 1) {
				var7 -= 10;
			} else {
				var7 -= var3 / 2;
			}
		}

		int var11 = var3 + 20;

		for (int var8 = var2; var8 > 0; --var8) {
			GuiButton var9;

			if (!ElevatorsCore.invertKeys) {
				var9 = new GuiButton(var8, var7 + (var8 - 1) % var13 * var11,
						var6 - var11 * ((var8 - 1) / var13), 20, 20, "" + var8);
			} else {
				var9 = new GuiButton(var8, var7 - (var8 - 1) % var13 * var11,
						var6 - var11 * ((var8 - 1) / var13), 20, 20, "" + var8);
			}

			if (this.curFloor == var8) {
				var9.enabled = false;
			}

			this.floorButtons.add(var9);
		}

		this.controlList.addAll(this.floorButtons);
		this.titleTop = this.guiTop + 5;
		this.subtitleTop = this.guiTop + 15;
		this.controlList.add(new GuiElevatorOptionsButton(198,
				this.guiLeft + 4, this.guiTop + 4));
		this.controlList.add(new GuiButton(199, this.width / 2 - 95,
				this.guiTop + 180, 90, 20, "Reset Elevator"));
		this.controlList.add(new GuiButton(200, this.width / 2 + 5,
				this.guiTop + 180, 90, 20, "Close"));
		this.controlList.add(new GuiButton(206, this.width / 2 - 95,
				this.guiTop + 180, 90, 20, "Apply"));
		this.controlList.add(new GuiButton(201, this.width / 2 + 5,
				this.guiTop + 180, 90, 20, var1.translateKey("gui.cancel")));
		this.floorZeroSlider = new GuiElevatorSlider(202, this.width / 2 - 75,
				this.guiTop + 110, this.floorOne,
				this.numFloors, true, "First Floor: ");
		this.controlList.add(this.floorZeroSlider);
		HashSet var14 = new HashSet();

		for (int var15 = 3; var15 < this.floors.length; ++var15) {
			var14.add(Integer.valueOf(var15 - 2));
		}

		this.floorNamesList = new GuiElevatorList(this, 203,
				this.width / 2 - 103, this.guiTop + 50, 130, 20, 15, 50, var14);
		this.floorNamesList.registerScrollButtons(this.controlList, 206, 207);
		this.RenameFloor = new GuiButton(204, this.width / 2 + 40,
				this.guiTop + 50, 60, 20, "Rename...");
		this.RenameElevator = new GuiButton(205, this.width / 2 - 60,
				this.guiTop + 25, 120, 20, "Rename Elevator...");
		this.controlList.add(this.RenameFloor);
		this.controlList.add(this.RenameElevator);
		this.RenameFloor.enabled = false;
		this.RenameElevator.enabled = false;
		this.canProvidePower = new GuiElevatorRadialButton(207,
				this.width / 2 - 100, this.guiTop + 130,
				"Stationary elevators provide power");
		this.canBeHalted = new GuiElevatorRadialButton(208,
				this.width / 2 - 100, this.guiTop + 160,
				"Moving elevators can be halted");
		this.mobilePower = new GuiElevatorRadialButton(208,
				this.width / 2 - 100, this.guiTop + 145,
				"Moving elevators provide power");

		try {
			this.canProvidePower.enabled = this.properties[0];
			this.canBeHalted.enabled = this.properties[1];
			this.mobilePower.enabled = this.properties[2];
		} catch (Exception var10) {
			ElevatorsCore.say("Error occurred when getting properties");
		}

		this.controlList.add(this.canProvidePower);
		this.controlList.add(this.canBeHalted);
		this.controlList.add(this.mobilePower);
		this.toggleVisibility();
		this.renameButtons();
	}

	public void renameButtons() {
		for (int var1 = 0; var1 < this.floorButtons.size(); ++var1) {
			GuiButton var2 = (GuiButton) this.floorButtons.get(var1);
			var2.displayString = ElevatorsCore.getAbbreviatedFloorName(var2.id,
					this.floorOne);
		}

		this.screenSubtitle = ElevatorsCore.getFloorName(this.curFloor,
				this.floorOne, this.floors);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		GL11.glPushMatrix();
		GL11.glTranslatef(this.guiLeft, this.guiTop, 0.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine
				.getTexture("/gui/elevatorgui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		GL11.glPopMatrix();
		super.drawScreen(var1, var2, var3);

		if (!this.optionsOpen) {
			if (!this.screenTitle.equals("")) {
				this.drawUnshadedCenteredString(this.fontRenderer,
						this.screenTitle, this.width / 2, this.titleTop, 0);
				this.drawUnshadedCenteredString(this.fontRenderer, ""
						+ this.screenSubtitle + "", this.width / 2,
						this.subtitleTop, 0);
			} else {
				this.drawUnshadedCenteredString(this.fontRenderer, ""
						+ this.screenSubtitle + "", this.width / 2,
						this.titleTop, 0);
			}
		} else {
			if (this.screenTitle != null && !this.screenTitle.equals("")) {
				this.drawUnshadedCenteredString(this.fontRenderer,
						this.screenTitle, this.width / 2 - 20,
						this.subtitleTop, 0);
			} else {
				this.drawUnshadedCenteredString(this.fontRenderer,
						"[Unnamed Elevator]", this.width / 2, this.subtitleTop,
						0);
			}

			this.drawUnshadedCenteredString(this.fontRenderer, "Options",
					this.width / 2, this.titleTop, 0);
			this.floorNamesList.drawScreen(var1, var2, var3);
		}
	}

	public void doubleClickedListItem(int var1, int var2) {
		if (var1 == this.floorNamesList.guiID) {
			if (var2 > -1) {
				int var3 = ((Integer) this.floorNamesList.itemList.get(var2))
						.intValue();
			}
		}
	}

	private void toggleVisibility() {
		for (int var1 = 0; var1 < this.controlList.size(); ++var1) {
			GuiButton var2 = (GuiButton) this.controlList.get(var1);

			if (var2.id < 201) {
				var2.drawButton = !this.optionsOpen;
			} else {
				var2.drawButton = this.optionsOpen;
			}

			if (var2.id == 198) {
				var2.enabled = !this.optionsOpen;
			}
		}

		this.canBeHalted.drawButton = !this.isRemote && this.optionsOpen;
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton var1) {
		int var2 = var1.id;
		ElevatorsCore.say("Command " + var2 + " selected.");

		switch (var2) {
		case 198:
			this.optionsOpen = true;
			this.toggleVisibility();
			break;

		case 199:
			if (!this.isRemote) {
				ElevatorsCore.elevator_reset(
						ModLoader.getMinecraftInstance().theWorld,
						this.elevatorPos);
			}

			this.exit(199);
			break;

		case 200:
			this.exit(200);
			break;

		case 202:
			this.floorOne = (int) this.floorZeroSlider.sliderValue;
			ElevatorsCore.say(String.valueOf(this.floorOne));
			break;

		case 203:
		default:
			int var3 = var1.id;

			if (var3 < 1 || var3 > ElevatorsCore.max_elevator_Y) {
				return;
			}

			if (var3 > this.numFloors || this.curFloor == var3) {
				return;
			}

			if (!this.isRemote) {
				ElevatorsCore.elevator_requestFloor(
						ModLoader.getMinecraftInstance().theWorld,
						this.elevatorPos, var3);
			}

			this.exit(var3);
			break;

		case 204:
			this.doubleClickedListItem(this.floorNamesList.guiID,
					this.floorNamesList.selectedElement);

		case 205:
			break;

		case 206:
			try {
				this.floors[0] = String.valueOf(this.floorOne);
				this.floors[1] = String.valueOf(this.colorID);
				this.floors[2] = this.screenTitle;
				this.properties[0] = this.canProvidePower.enabled;
				this.properties[1] = this.canBeHalted.enabled;
				this.properties[2] = this.mobilePower.enabled;
			} catch (Exception var4) {
				ElevatorsCore.say("Error ocurred when saving properties");
			}

			ElevatorsCore.checkedFloorNames.put(this.elevatorPos, this.floors);
			ElevatorsCore.checkedProperties.put(this.elevatorPos,
					this.properties);
			this.exit(206, false);

		case 201:
			this.optionsOpen = false;
			this.toggleVisibility();
			this.renameButtons();
		}
	}

	private void exit(int var1) {
		this.exit(var1, true);
	}

	private void exit(int packetCode, boolean isPacketSent) {
		if (this.isRemote) {
			this.sentPacket = ((ElevatorsPacketHandling) Elevators.Core
					.getPacketHandler()).sendResponseGUIPacket(
					this.guiTransferID, packetCode, this.floors,
					this.properties);
		} else if (this.elevatorPos != null) {
			ElevatorsCore
					.refreshElevator(ModLoader.getMinecraftInstance().theWorld,
							this.elevatorPos);
			this.sentPacket = isPacketSent;
		}

		if (isPacketSent) {
			ElevatorsCore.say("Exiting gui!");
			this.mc.thePlayer.closeScreen();
		}
	}

	public void drawUnshadedCenteredString(FontRenderer var1, String var2,
			int var3, int var4, int var5) {
		var1.drawString(var2, var3 - var1.getStringWidth(var2) / 2, var4, var5);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	public void onGuiClosed() {
		if (!this.sentPacket) {
			ElevatorsCore.say("Exiting gui!");
			this.exit(200, false);
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();

		if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
			this.mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int var1, int var2, int var3) {
		if (var3 == 0 && !this.floorNamesList.mousePressed(var1, var2)
				&& this.floorNamesList.extended) {
			this.floorNamesList.minimize();
		} else if (this.floorNamesList.mousePressed(var1, var2)
				&& this.floorNamesList.extended) {
			return;
		}

		super.mouseClicked(var1, var2, var3);
	}

	/**
	 * Handles keyboard input.
	 */
	public void handleKeyboardInput() {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == 200
					&& this.floorNamesList.selectedElement > 0) {
				--this.floorNamesList.selectedElement;
				this.floorNamesList.setAmountScrolled();
			} else if (Keyboard.getEventKey() == 208
					&& this.floorNamesList.selectedElement < this.floorNamesList
							.getSize() - 1) {
				++this.floorNamesList.selectedElement;
				this.floorNamesList.setAmountScrolled();
			}
		}

		super.handleKeyboardInput();
	}
}
