package StsMod.powers;

import StsMod.action.BreakAction;
import StsMod.action.BreakTransformAction;
import StsMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static StsMod.StsMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/23 22:34
 */
public class BreakPower extends BasePower {
    public static final String POWER_ID = makeID(BreakPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.
    public BreakPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 1);
    }

    public BreakPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public void atStartOfTurn() {
        // 有残梅绽 则再晕一次
        if (this.owner.hasPower(ThanatoplumRebloomPower.POWER_ID)) {
            ThanatoplumRebloomPower trp = (ThanatoplumRebloomPower) this.owner.getPower(ThanatoplumRebloomPower.POWER_ID);
            // 残梅绽未触发，则触发
            if (!trp.is_triggered) {
                trp.onSpecificTrigger();
                addToTop(new BreakAction((AbstractMonster) this.owner, this.source));

            }
        }
    }

    @Override
    public void atEndOfTurn(boolean is_player) {
        if (!is_player) {
            amount--;
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction((AbstractMonster) owner, owner, this));
                addToTop(new BreakTransformAction((AbstractMonster) owner, source, new ToughnessProtectPower(owner, source)));
            }
            // 删除残梅绽
            if (this.owner.hasPower(ThanatoplumRebloomPower.POWER_ID)) {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ThanatoplumRebloomPower.POWER_ID));

            }
        }
    }
}