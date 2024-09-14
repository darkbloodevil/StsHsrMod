package HsrMod.util;

import HsrMod.interfaces.SpecialToughnessInterface;
import HsrMod.powers.BreakPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ToughnessUtil {
    public static int get_toughness(AbstractMonster monster) {

        int toughness;
        if (monster instanceof SpecialToughnessInterface){
            SpecialToughnessInterface sti=(SpecialToughnessInterface)monster;
            toughness=sti.get_toughness();
        }else if (monster.type == AbstractMonster.EnemyType.BOSS) {
            toughness = 30;
        } else if (monster.type == AbstractMonster.EnemyType.ELITE) {
            if (monster.maxHealth>=160)
                toughness = 24;
            else if (monster.maxHealth>=80)
                toughness = 22;
            else
                toughness = 20;
        }else{
            if (monster.maxHealth>=160){
                toughness = 16;
            } else if (monster.maxHealth>=80) {
                toughness = 14;
            }else if (monster.maxHealth>=40) {
                toughness = 12;
            }else if (monster.maxHealth>=20) {
                toughness = 10;
            }else {
                toughness=8;
            }

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
