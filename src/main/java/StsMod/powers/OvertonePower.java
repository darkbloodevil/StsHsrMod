package StsMod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static StsMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/25 18:36
 * @description
 */
public class OvertonePower extends BasePower{
    public static final String POWER_ID = makeID(OvertonePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public OvertonePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}