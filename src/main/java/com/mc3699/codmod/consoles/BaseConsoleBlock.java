package com.mc3699.codmod.consoles;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;

public abstract class BaseConsoleBlock extends Block implements EntityBlock {
    public BaseConsoleBlock(Properties properties) {
        super(properties);
    }
}
