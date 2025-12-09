package com.mc3699.codmod.datagen;

import com.mc3699.codmod.registry.CodItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public DatagenRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.END_STONE, 2)
                .pattern(" ##")
                .pattern(" ##")
                .define('#', CodItems.INERT_DUST.get())
                .unlockedBy("has_inert_dust", has(CodItems.INERT_DUST.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CodItems.INERT_SEEDS.get(), 8)
                .pattern("WWW")
                .pattern("WPW")
                .pattern("WWW")
                .define('W', Items.WHEAT_SEEDS)
                .define('P', Items.ENDER_PEARL)
                .unlockedBy("has_seeds", has(CodItems.INERT_SEEDS.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PURPUR_BLOCK, 2)
                .pattern(" #$")
                .pattern(" $#")
                .define('#', CodItems.INERT_DUST.get())
                .define('$', CodItems.DISCORD_FRUIT.get())
                .unlockedBy("poopoo_caca", has(CodItems.DISCORD_FRUIT.get()))
                .save(recipeOutput);

    }
}
