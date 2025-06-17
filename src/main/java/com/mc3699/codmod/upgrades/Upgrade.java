package com.mc3699.codmod.upgrades;

/**
 * An upgrade.
 *
 * @param id             The ID of the upgrade.
 * @param defaultLevel   The starting level of the upgrade, generally 0.
 * @param maxLevel       The maximum level of the upgrade.
 * @param initialCost    The initial cost of the upgrade.
 * @param additionalCost The amount that gets added to the cost for each level upgraded.
 */
public record Upgrade(String id, int defaultLevel, int maxLevel, int initialCost, int additionalCost) {
    public int getCost(int level) {
        return initialCost + (additionalCost * (level - 1));
    }
}
