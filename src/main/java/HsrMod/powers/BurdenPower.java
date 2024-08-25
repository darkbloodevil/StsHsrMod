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
    int turns;

    public BurdenPower(AbstractCreature owner, AbstractCreature source, int turns) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 2);
        this.turns = turns;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL)
            return super.onAttacked(info, damageAmount);

        this.amount--;

        if (this.amount <= 0) {
            this.amount = 2;
            addToBot(new GainEnergyAction(1));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.turns--;
        if (turns == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}