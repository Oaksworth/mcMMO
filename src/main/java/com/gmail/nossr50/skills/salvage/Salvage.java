package com.gmail.nossr50.skills.salvage;

import com.gmail.nossr50.config.AdvancedConfig;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.util.skills.RankUtils;
import org.bukkit.Material;

public class Salvage {
    public static Material anvilMaterial = Config.getInstance().getSalvageAnvilMaterial();

    public static int    salvageMaxPercentageLevel = AdvancedConfig.getInstance().getSalvageMaxPercentageLevel();
    public static double salvageMaxPercentage      = AdvancedConfig.getInstance().getSalvageMaxPercentage();

    public static int advancedSalvageUnlockLevel = RankUtils.getRankUnlockLevel(SubSkillType.SALVAGE_ADVANCED_SALVAGE, 1);

    public static boolean arcaneSalvageDowngrades  = AdvancedConfig.getInstance().getArcaneSalvageEnchantDowngradeEnabled();
    public static boolean arcaneSalvageEnchantLoss = AdvancedConfig.getInstance().getArcaneSalvageEnchantLossEnabled();

    protected static int calculateSalvageableAmount(short currentDurability, short maxDurability, int baseAmount) {
        double percentDamaged = (maxDurability <= 0) ? 1D : (double) (maxDurability - currentDurability) / maxDurability;

        return (int) Math.floor(baseAmount * percentDamaged);
    }
}