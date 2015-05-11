package com.bioxx.tfc2;

import com.bioxx.tfc2.api.Global;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		MinecraftForge.EVENT_BUS.register(new RenderOverlayHandler());
		for(int l = 0; l < Global.STONE_ALL.length; l++)
		{
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Dirt), l, new ModelResourceLocation(Reference.ModID + ":Dirt/" + Global.STONE_ALL[l], "inventory"));
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Grass), l, new ModelResourceLocation(Reference.ModID + ":Grass/" + Global.STONE_ALL[l]+"/"+Global.STONE_ALL[l], "inventory"));
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Stone), l, new ModelResourceLocation(Reference.ModID + ":Stone/" + Global.STONE_ALL[l], "inventory"));
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Rubble), l, new ModelResourceLocation(Reference.ModID + ":Rubble/" + Global.STONE_ALL[l], "inventory"));
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Sand), l, new ModelResourceLocation(Reference.ModID + ":Sand/" + Global.STONE_ALL[l], "inventory"));
			registerItemMesh(Item.getItemFromBlock(TFCBlocks.Gravel), l, new ModelResourceLocation(Reference.ModID + ":Gravel/" + Global.STONE_ALL[l], "inventory"));
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Dirt), Reference.ModID + ":Dirt/" + Global.STONE_ALL[l]);
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Grass), Reference.ModID + ":Grass/" + Global.STONE_ALL[l]+"/"+Global.STONE_ALL[l]);
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Stone), Reference.ModID + ":Stone/" + Global.STONE_ALL[l]);
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Rubble), Reference.ModID + ":Rubble/" + Global.STONE_ALL[l]);
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Sand), Reference.ModID + ":Sand/" + Global.STONE_ALL[l]);
			ModelBakery.addVariantName(Item.getItemFromBlock(TFCBlocks.Gravel), Reference.ModID + ":Gravel/" + Global.STONE_ALL[l]);
		}
	}

	private void registerItemMesh(Item i, int meta, ModelResourceLocation mrl)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, meta, mrl);
	}

}
