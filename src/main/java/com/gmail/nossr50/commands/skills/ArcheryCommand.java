package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.archery.Archery;
import com.gmail.nossr50.util.TextComponentFactory;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArcheryCommand extends SkillCommand {
    private String skillShotBonus;
    private String dazeChance;
    private String dazeChanceLucky;
    private String retrieveChance;
    private String retrieveChanceLucky;

    private boolean canSkillShot;
    private boolean canDaze;
    private boolean canRetrieve;

    public ArcheryCommand() {
        super(PrimarySkillType.ARCHERY);
    }

    @Override
    protected void dataCalculations(Player player, float skillValue, boolean isLucky) {
        // ARCHERY_ARROW_RETRIEVAL
        if (canRetrieve) {
            String[] retrieveStrings = calculateAbilityDisplayValues(skillValue, SubSkillType.ARCHERY_ARROW_RETRIEVAL, isLucky);
            retrieveChance = retrieveStrings[0];
            retrieveChanceLucky = retrieveStrings[1];
        }
        
        // ARCHERY_DAZE
        if (canDaze) {
            String[] dazeStrings = calculateAbilityDisplayValues(skillValue, SubSkillType.ARCHERY_DAZE, isLucky);
            dazeChance = dazeStrings[0];
            dazeChanceLucky = dazeStrings[1];
        }
        
        // SKILL SHOT
        if (canSkillShot) {
            double bonus = (skillValue / Archery.skillShotIncreaseLevel) * Archery.skillShotIncreasePercentage;
            skillShotBonus = percent.format(Archery.getSkillShotBonusDamage(player, 0));
        }
    }

    @Override
    protected void permissionsCheck(Player player) {
        canSkillShot = canUseSubskill(player, SubSkillType.ARCHERY_SKILL_SHOT);
        canDaze = canUseSubskill(player, SubSkillType.ARCHERY_DAZE);
        canRetrieve = canUseSubskill(player, SubSkillType.ARCHERY_ARROW_RETRIEVAL);
    }

    @Override
    protected List<String> statsDisplay(Player player, float skillValue, boolean hasEndurance, boolean isLucky) {
        List<String> messages = new ArrayList<String>();

        if (canRetrieve) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_ARROW_RETRIEVAL, retrieveChance)
                    + (isLucky ? LocaleLoader.getString("Perks.Lucky.Bonus", retrieveChanceLucky) : ""));
        }
        
        if (canDaze) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_DAZE, dazeChance)
                    + (isLucky ? LocaleLoader.getString("Perks.Lucky.Bonus", dazeChanceLucky) : ""));
        }
        
        if (canSkillShot) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_SKILL_SHOT, skillShotBonus));
        }

        return messages;
    }

    @Override
    protected List<TextComponent> getTextComponents(Player player) {
        List<TextComponent> textComponents = new ArrayList<>();

        TextComponentFactory.getSubSkillTextComponents(player, textComponents, PrimarySkillType.ARCHERY);

        return textComponents;
    }
}
