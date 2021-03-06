package com.bioxx.tfc2.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bioxx.tfc2.Core;
import com.bioxx.tfc2.TFCBlocks;

public class BlockVegetation extends BlockTerra implements IPlantable
{
	public static final PropertyEnum META_PROPERTY = PropertyEnum.create("veg", VegType.class);
	/** Whether this fence connects in the northern direction */
	public static final PropertyBool IS_ON_STONE = PropertyBool.create("isonstone");

	public BlockVegetation()
	{
		super(Material.VINE, META_PROPERTY);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setSoundType(SoundType.GROUND);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(META_PROPERTY, VegType.Grass0).withProperty(IS_ON_STONE, Boolean.valueOf(false)));
		float f = 0.35F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}

	/*******************************************************************************
	 * 1. Content
	 *******************************************************************************/

	@Override
	public void onNeighborChange(IBlockAccess worldIn, BlockPos pos, BlockPos blockIn)
	{

		super.onNeighborChange(worldIn, pos, blockIn);
		checkAndDropBlock((World) worldIn, pos, worldIn.getBlockState(pos));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		checkAndDropBlock(worldIn, pos, state);
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!canBlockStay(worldIn, pos, state))
		{
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		BlockPos down = pos.down();
		IBlockState soil = worldIn.getBlockState(down);
		if (state.getBlock() != this) 
			return canPlaceBlockOn(state, soil);
		return soil.getBlock().canSustainPlant(soil, worldIn, down, EnumFacing.UP, this);
	}

	protected boolean canPlaceBlockOn(IBlockState state, IBlockState soil)
	{
		VegType veg = (VegType)state.getValue(META_PROPERTY);

		if(veg == VegType.DeadBush)
			return Core.isTerrain(soil);

		return Core.isSoil(soil);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
	{
		IBlockState plant = plantable.getPlant(world, pos.offset(direction));
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

		VegType veg = (VegType)state.getValue(META_PROPERTY);
		if(plant.getBlock() == this)
		{
			if(veg == VegType.DoubleGrassBottom && plant.getValue(META_PROPERTY) == VegType.DoubleGrassTop)
				return true;
			if(veg == VegType.DoubleFernBottom && plant.getValue(META_PROPERTY) == VegType.DoubleFernTop)
				return true;
		}
		return false;
	}

	/*******************************************************************************
	 * 2. Rendering
	 *******************************************************************************/

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return Block.EnumOffsetType.NONE;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	/*******************************************************************************
	 * 3. Blockstate 
	 *******************************************************************************/

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(IS_ON_STONE, world.getBlockState(pos.down()).getBlock() == TFCBlocks.Stone);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(META_PROPERTY, VegType.getTypeFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((VegType)state.getValue(META_PROPERTY)).getMeta();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.2, 0, 0.2, 0.8, 0.75, 0.8);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { META_PROPERTY, IS_ON_STONE});
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 3;
	}

	public enum VegType implements IStringSerializable
	{
		Grass0("grass0", 0),
		Grass1("grass1", 1),
		DeadBush("deadbush", 2),
		DoubleGrassBottom("doublegrassbottom", 3),
		DoubleGrassTop("doublegrasstop", 4),
		Fern("fern", 5),
		DoubleFernBottom("doublefernbottom", 6),
		DoubleFernTop("doubleferntop", 7),
		ShortGrass("shortgrass", 8),
		ShorterGrass("shortergrass", 9);

		private String name;
		private int meta;

		VegType(String s, int id)
		{
			name = s;
			meta = id;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta()
		{
			return meta;
		}

		public static VegType getTypeFromMeta(int meta)
		{
			for(int i = 0; i < VegType.values().length; i++)
			{
				if(VegType.values()[i].meta == meta)
					return VegType.values()[i];
			}
			return null;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) 
	{
		if(world.getBlockState(pos).getValue(META_PROPERTY) == VegType.DeadBush)
			return EnumPlantType.Desert;
		return EnumPlantType.Plains;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) 
	{
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) 
			return getDefaultState();
		return state;
	}
}
