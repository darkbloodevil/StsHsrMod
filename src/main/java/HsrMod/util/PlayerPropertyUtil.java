package HsrMod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

/**
 * @author darkbloodevil
 * @date 2024/8/9 20:26
 * @description
 */
public class PlayerPropertyUtil {
    /**
     *
     * @return 角色攻击力
     */
    public static int get_strength() {
        int strength = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        return strength;
    }
}
