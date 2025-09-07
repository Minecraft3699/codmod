package com.mc3699.codmod.recipe;

import com.mc3699.codmod.data.CodDataMaps;
import com.mc3699.codmod.data.SausageData;
import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class SausageRecipe extends CustomRecipe {
    public SausageRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        // Must be at least 3x3
        if (craftingInput.width() < 3 || craftingInput.height() < 3) return false;

        // Center slot (1,1) in a 3x3
        ItemStack center = craftingInput.getItem(1,1);
        if (!center.is(CodItems.BAGEL.get())) return false;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (!stack.isEmpty()) {
                SausageData data = stack.getItemHolder().getData(CodDataMaps.SAUSAGE_DATA_MAP);
                if (data != null) return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        int r = 0, g = 0, b = 0;
        int totalFoodItems = 0;
        float totalSaturation = 0;
        int totalHunger = 0;
        float totalDisgust = 0;

        for (int i = 0; i < craftingInput.size(); i++) {

            ItemStack stack = craftingInput.getItem(i);
            if (!stack.isEmpty()) {
                SausageData data = stack.getItemHolder().getData(CodDataMaps.SAUSAGE_DATA_MAP);
                if (data != null) {
                    int c = data.color();
                    r += (c >> 16) & 0xFF;
                    g += (c >> 8) & 0xFF;
                    b += c & 0xFF;

                    totalDisgust += data.disgust();
                    totalHunger += (int) data.nutrition();
                    totalSaturation += data.saturation();

                    totalFoodItems++;
                }
            }
        }

        if (totalFoodItems == 0) {
            return new ItemStack(CodItems.SAUSAGE.get());
        }

        int avgR = r / totalFoodItems;
        int avgG = g / totalFoodItems;
        int avgB = b / totalFoodItems;
        int avgColor = (avgR << 16) | (avgG << 8) | avgB;

        ItemStack sausage = new ItemStack(CodItems.SAUSAGE.get());

        FoodProperties props = new FoodProperties.Builder()
                .nutrition(totalHunger)
                .saturationModifier(totalSaturation)
                .alwaysEdible()
                .build();

        sausage.set(DataComponents.FOOD, props);
        sausage.set(CodComponents.SAUSAGE_COLOR, avgColor);
        return sausage;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i >= 3 && i1 >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CodRecipeSerializers.SAUSAGE_RECIPE_SERIALIZER.get();
    }
}
