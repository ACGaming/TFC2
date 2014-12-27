package com.bioxx.tfc2.Networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class TestPacket extends AbstractPacket
{
	String msg;

	public TestPacket() {}

	public TestPacket(String S)
	{
		msg = S;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, msg);
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		msg = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		System.out.println("++++++++++++Client: "+msg);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		System.out.println("++++++++++++Server: "+msg);
	}

}
