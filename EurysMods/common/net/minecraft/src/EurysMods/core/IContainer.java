package net.minecraft.src.EurysMods.core;

import net.minecraft.src.TileEntity;

public abstract interface IContainer {

	public TileEntity getTileEntity(int meta);

	/**
	 * @param meta
	 *            The current Metadata
	 * @return And instance of the TileEntity class for this block
	 */
	public TileEntity getBlockEntity(int meta);

	public TileEntity getBlockEntity();
}
