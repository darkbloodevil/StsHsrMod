package StsMod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ToughnessUtil {
    public static int get_toughness(AbstractMonster monster) {

        int toughness = 6;
        if (monster.type == AbstractMonster.EnemyType.BOSS) {
            toughness = 20;
        } else if (monster.type == AbstractMonster.EnemyType.ELITE) {
            toughness = 30;
        }
        return toughness;
    }
}
