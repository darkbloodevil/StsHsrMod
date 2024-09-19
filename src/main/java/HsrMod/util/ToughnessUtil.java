package HsrMod.util;

import HsrMod.interfaces.SpecialToughnessInterface;
import HsrMod.powers.BreakPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ToughnessUtil {
    public static int get_toughness(AbstractMonster monster) {

        int toughness;
        if (monster instanceof SpecialToughnessInterface) {
            SpecialToughnessInterface sti = (SpecialToughnessInterface) monster;
            toughness = sti.get_toughness();
        } else if (monster.type == AbstractMonster.EnemyType.BOSS) {
            toughness = 32;
            switch (AbstractDungeon.id) {
                case "Exordium": {
                    break;
                }
                case "TheCity": {
                    toughness = 36;
                    break;
                }case "TheBeyond": {
                    toughness = 40;
                    break;
                }case "TheEnding": {
                    toughness = 45;
                    break;
                }
            }
        } else if (monster.type == AbstractMonster.EnemyType.ELITE) {
            if (monster.maxHealth >= 150)
                toughness = 28+(monster.maxHealth-150)/25;
            else if (monster.maxHealth >= 120)
                toughness = 26;
            else if (monster.maxHealth >= 90)
                toughness = 24;
            else if (monster.maxHealth >= 60)
                toughness = 22;
            else
                toughness = 20;
        } else {
            if (monster.maxHealth >= 100) {
                toughness = 16+(monster.maxHealth-100)/25;
            } else if (monster.maxHealth >= 60) {
                toughness = 14;
            } else if (monster.maxHealth >= 40) {
                toughness = 12;
            } else if (monster.maxHealth >= 20) {
                toughness = 10;
            } else {
                toughness = 8;
            }

        }

        return toughness;
    }

    /**
     * 判断目标是否在弱点击破状态
     *
     * @param monster
     * @return
     */
    public static boolean target_on_break(AbstractMonster monster) {

        return monster.hasPower(BreakPower.POWER_ID);
    }
}
