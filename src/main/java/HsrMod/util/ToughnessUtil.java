package HsrMod.util;

import HsrMod.powers.BreakPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ToughnessUtil {
    public static int get_toughness(AbstractMonster monster) {

        int toughness = 10;
        if (monster.type == AbstractMonster.EnemyType.BOSS) {
            toughness = 30;
        } else if (monster.type == AbstractMonster.EnemyType.ELITE) {
            toughness = 20;
        }
        return toughness;
    }

    /**
     * 判断目标是否在弱点击破状态
     * @param monster
     * @return
     */
    public static boolean target_on_break(AbstractMonster monster){

        return monster.hasPower(BreakPower.POWER_ID);
    }
}
