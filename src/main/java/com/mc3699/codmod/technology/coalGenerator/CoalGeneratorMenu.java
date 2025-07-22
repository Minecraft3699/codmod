package com.mc3699.codmod.technology.coalGenerator;

import com.mc3699.codmod.registry.CodMenus;
import com.mc3699.codmod.technology.foundation.BaseMachineMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorMenu extends BaseMachineMenu {
    public CoalGeneratorMenu(int containerId, BlockEntity blockEntity, ContainerData data, ContainerLevelAccess access) {
        super(null, containerId, blockEntity, data, access);
    }
}
