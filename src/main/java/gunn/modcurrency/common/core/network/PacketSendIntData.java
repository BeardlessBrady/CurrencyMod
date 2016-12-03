package gunn.modcurrency.common.core.network;

import gunn.modcurrency.common.blocks.BlockVendor;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Distributed with the Currency-Mod for Minecraft.
 * Copyright (C) 2016  Brady Gunn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File Created on 2016-11-06.
 */
public class PacketSendIntData implements IMessage {
    private BlockPos blockPos;
    private int data, mode;

    public PacketSendIntData() {
    }

    public void setData(int data, BlockPos pos, int mode) {
        this.blockPos = pos;
        this.data = data;
        this.mode = mode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        data = buf.readInt();
        mode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());
        buf.writeInt(data);
        buf.writeInt(mode);
    }

    public static class Handler implements IMessageHandler<PacketSendIntData, IMessage> {

        @Override
        public IMessage onMessage(final PacketSendIntData message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSendIntData message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
            World world = playerEntity.worldObj;

            switch (message.mode) {
                case 0:     //BlockVendor set Lock [to server]
                    BlockVendor block0 = (BlockVendor) world.getBlockState(message.blockPos).getBlock();
                    block0.getTile(world, message.blockPos).setField(1, message.data);
                    break;
                case 1:     //Block Vendor set Cost [to server]
                    BlockVendor block1 = (BlockVendor) world.getBlockState(message.blockPos).getBlock();
                    block1.getTile(world, message.blockPos).setItemCost(message.data);
                    break;
                case 2:     //Block Vendor, updated cost [to Client]
                    BlockVendor block2 = (BlockVendor) world.getBlockState(message.blockPos).getBlock();
                    block2.getTile(world, message.blockPos)
                            .setItemCost(message.data);
                    break;
                case 3:     //Enable/Disable Creative Button [to server]
                    BlockVendor block3 = (BlockVendor) world.getBlockState(message.blockPos).getBlock();
                    block3.getTile(world, message.blockPos)
                            .setField(6, message.data);
            }
        }
    }
}
