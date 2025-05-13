package com.mc3699.codmod.test;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AISignGen {

    public static String[] splitToSign(String aiResponse) {
        List<String> signLines = new ArrayList<>();
        for (int i = 0; i < aiResponse.length(); i += 15) {
            int end = Math.min(i + 15, aiResponse.length());
            signLines.add(aiResponse.substring(i, end));
        }
        return signLines.toArray(new String[0]);
    }

    public static void updateSign(ServerLevel level, BlockPos playerPos, String response) {
        List<BlockPos> signs = new ArrayList<>();
        for (int x = -5; x <= 5; x++)
            for (int y = -5; y <= 5; y++)
                for (int z = -5; z <= 5; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    if (level.getBlockState(pos).getBlock() == Blocks.OAK_SIGN)
                        signs.add(pos);
                }

        String[] lines = AISignGen.splitToSign(response);
        for (int i = 0; i < lines.length; i++)
            lines[i] = lines[i].length() > 15 ? lines[i].substring(0, 15) : lines[i];

        BlockPos signPos = signs.isEmpty() ? playerPos : signs.get(level.random.nextInt(signs.size()));
        BlockState state = signs.isEmpty() ? Blocks.OAK_SIGN.defaultBlockState() : level.getBlockState(signPos);

        if (state.getBlock() == Blocks.OAK_SIGN) {
            SignBlockEntity sign = new SignBlockEntity(signPos, state);
            try {
                level.setBlockEntity(sign);
                SignText text = sign.getFrontText();
                for (int i = 0; i < 4; i++)
                    text = text.setMessage(i, Component.literal(i < lines.length ? lines[i] : ""));
                sign.setText(text, true);
                sign.setChanged();
            } catch (Exception e) {
                System.out.println("Error updating sign at " + signPos + ": " + e.getMessage());
            }
        }
    }

}
