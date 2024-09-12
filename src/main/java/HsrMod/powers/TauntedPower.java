package HsrMod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/6 11:14
 * @description 被嘲讽的角色用这个
 */
public class TauntedPower extends BasePower {
    public static final String POWER_ID = makeID(TauntedPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;// 不清掉
    private static final boolean TURN_BASED = true;

    public TauntedPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        amount--;
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }

        updateDescription();
        flash();
    }
    /**
     * 嘲讽不可累加
     * @param stackAmount
     */
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
    }

}