package HsrMod.util;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/9 20:43
 * @description
 */
public class DamageUtil {
    /**
     * 加上攻击力的追加攻击（是追加攻击 而且type是普通）
     * @param damageSource 来源
     * @param base 伤害量
     * @param toughness_reduction 削韧
     * @return HsrDamageInfo
     */
    public static HsrDamageInfo deal_followUp_info(AbstractPlayer damageSource, int base,int toughness_reduction) {
        return new HsrDamageInfo(damageSource, base + PlayerPropertyUtil.get_strength(), DamageInfo.DamageType.NORMAL, toughness_reduction, true);
    }


}
