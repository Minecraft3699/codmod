package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SausageItem extends Item {
    public SausageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack defaultItem = new ItemStack(this);
        defaultItem.set(CodComponents.SAUSAGE_COLOR, 0xFFFF0000);

        return defaultItem;
    }
}
