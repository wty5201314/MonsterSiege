package com.example.examplemod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public  class msg {

    public static void sendMsgToNearbyPlayers(Level level, String translatable, String extraStr, BlockPos blockPos) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) level;
            // 遍历所有在线玩家
            for (ServerPlayer player : serverLevel.players()) {
                // 计算玩家与方块实体的距离
                double distance = blockPos.distSqr(player.blockPosition());
                // 如果距离小于或等于 10000 (100 * 100)，则向玩家发送消息
                if (distance <= 10000) {
                    Component component=Component.literal(extraStr).withStyle(ChatFormatting.WHITE);
                    player.sendSystemMessage(Component.translatable(translatable,component));
                    //player.sendSystemMessage(new myComponent(1,new HashMap<>()).withStyle(ChatFormatting.DARK_AQUA));
                }
            }
        }
    }
}
