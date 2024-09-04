package HsrMod.util;

import HsrMod.HsrMod;
import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

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

    public static HsrDamageInfo alter_followUp(HsrDamageInfo info, AbstractCreature target){
        HsrMod.logger.info("====HsrDamageAction===info.base"+info.base);
        HsrMod.logger.info("====is_follow_up==="+info.is_follow_up);
        // 追加攻击手动实现易伤和虚弱
        if (info.is_follow_up){
            if (target.hasPower(VulnerablePower.POWER_ID)){
                info.base=(int) target.getPower(VulnerablePower.POWER_ID).atDamageReceive(info.base,info.type);
            }
            if (info.damageSource.hasPower(WeakPower.POWER_ID)){
                info.base=(int) AbstractDungeon.player.getPower(WeakPower.POWER_ID).atDamageGive(info.base,info.type);
            }
            HsrMod.logger.info("====altered==="+info.base);
        }

        return info;
    }


}
