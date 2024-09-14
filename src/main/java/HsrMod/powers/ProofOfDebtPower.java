package HsrMod.powers;

import HsrMod.HsrMod;
import HsrMod.action.BreakAction;
import HsrMod.action.BreakTransformAction;
import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/5 11:28
 * @description 托帕 负债证明
 */
public class ProofOfDebtPower extends BasePower {
    public static final String POWER_ID = makeID(ProofOfDebtPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ProofOfDebtPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 3);
    }

    public ProofOfDebtPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL)
            return super.onAttacked(info, damageAmount);

        this.amount--;

        if (this.amount <= 0) {
            this.amount = 3;
            addToBot(new DrawCardAction(1));
        }
        return super.onAttacked(info, damageAmount);
    }

    /**
     * 对于追加攻击 增伤x1.5
     *
     * @param info
     * @param damageAmount
     * @return
     */
    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info instanceof HsrDamageInfo) {
            HsrDamageInfo h_info = HsrDamageInfo.to_hsr_info(info);
            if (h_info.is_follow_up) {
                return super.onAttackedToChangeDamage(info, (int) (damageAmount * 1.5));
            }
        }

        return super.onAttackedToChangeDamage(info, damageAmount);
    }
}

