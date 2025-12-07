package com.mc3699.codmod.datagen;

import com.mc3699.codmod.block.EnderCropBlock;
import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;

public class DatagenBlockLootProvider extends BlockLootSubProvider {

    protected DatagenBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(CodBlocks.MOLTEN_COPPER.get());
        dropSelf(CodBlocks.STABLE_DISRUPTION.get());
        dropSelf(CodBlocks.SERVER.get());
        dropOther(CodBlocks.UAV_CONTROLLER.get(), Items.COD);
        dropOther(CodBlocks.RADAR.get(), Items.COD);

        LootItemCondition.Builder lootItemEnderConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(CodBlocks.ENDER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EnderCropBlock.AGE, 3));

        this.add(CodBlocks.ENDER_CROP.get(),
                LootTable.lootTable()
                        // Pool for the fruit (2-4 when mature)
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(CodItems.DISCORD_FRUIT.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                        .when(lootItemEnderConditionBuilder)
                                )
                        )
                        // Pool for the seeds (0-3 when mature, like vanilla)
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(CodItems.INERT_SEEDS.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                        .when(lootItemEnderConditionBuilder)
                                )
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(CodItems.INERT_DUST.get()) // Replace with your dust item
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                        .when(lootItemEnderConditionBuilder)
                                )
                        )
        );



    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = List.of(
                CodBlocks.MOLTEN_COPPER.get(),
                CodBlocks.UAV_CONTROLLER.get(),
                CodBlocks.RADAR.get(),
                CodBlocks.STABLE_DISRUPTION.get(),
                CodBlocks.SERVER.get(),
                CodBlocks.ENDER_CROP.get()
        );
        return blocks;
    }

}
