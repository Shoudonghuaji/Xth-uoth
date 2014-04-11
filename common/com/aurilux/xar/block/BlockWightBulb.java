package com.aurilux.xar.block;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

import com.aurilux.xar.lib.Blocks;
import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWightBulb extends BlockFlower {
	//TODO add particles that float up from the 'bulb'

    protected BlockWightBulb(int id, Material mat) {
		super(id, mat);
        this.setTickRandomly(true);
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, .9F, 0.9F);
        this.setUnlocalizedName("wightBulb");
    }

	public BlockWightBulb(int id) {
		this(id, Material.plants);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		this.blockIcon = reg.registerIcon(XAR_Ref.MOD_ID + ":wightbulb");
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlockId(x, y - 1, z) == Blocks.stoneStrange.blockID;
    }
}