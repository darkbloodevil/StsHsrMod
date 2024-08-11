package HsrMod.powers;

import HsrMod.HsrMod;
import HsrMod.interfaces.AtDepletingToughness;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/9 17:23
 * @description
 */
public class KarmaPower extends BasePower implements AtDepletingToughness {
    public static final String POWER_ID = makeID(KarmaPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    int magic;
    public static final int trigger_threshold=8;

    public KarmaPower(AbstractCreature owner, int magic) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 0);
        this.magic=magic;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
        this.magic+=stackAmount;
    }

    @Override
    public void at_depleting_toughness(AbstractCreature target, int toughness_reduction) {
        this.amount-=toughness_reduction;
        HsrMod.logger.info("=====Karma: "+amount+"\ntoughness_reduction: "+toughness_reduction);
        this.flash();
        while (amount>trigger_threshold){
            this.amount-=trigger_threshold;
            addToBot(new DrawCardAction(magic));
            this.flash();
        }
    }
}