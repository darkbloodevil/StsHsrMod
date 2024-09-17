package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/11 13:49
 * @description
 */
public class BurdenPower extends BasePower {
    public static final String POWER_ID = makeID(BurdenPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public BurdenPower(AbstractCreature owner, AbstractCreature source, int turns) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, turns);
        this.amount2 = 2;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount,amount2);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL)
            return super.onAttacked(info, damageAmount);

        this.amount2--;

        if (this.amount2 <= 0) {
            this.amount2 = 2;
            addToBot(new GainEnergyAction(1));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.amount--;
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}