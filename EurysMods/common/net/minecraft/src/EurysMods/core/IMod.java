package net.minecraft.src.EurysMods.core;

public interface IMod {
	public void initialize();

	public void load();

	public void addItems();

	public void addNames();

	public void addRecipes();

	public int configurationProperties();
}
