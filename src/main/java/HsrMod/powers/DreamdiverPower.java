package HsrMod.powers;

import HsrMod.action.DreamdiverAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/9 11:49
 * @description
 */
public class DreamdiverPower extends BasePower{
    public static final String POWER_ID = makeID(DreamdiverPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DreamdiverPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 0);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new DreamdiverAction());
        super.atStartOfTurnPostDraw();
    }
}
