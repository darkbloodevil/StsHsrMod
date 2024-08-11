package HsrMod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/25 19:14
 * @description
 */
public class ThanatoplumRebloomPower extends BasePower {
    public static final String POWER_ID = makeID(ThanatoplumRebloomPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public boolean is_triggered = false;

    public ThanatoplumRebloomPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 0);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onSpecificTrigger() {
        if (!is_triggered) {
            is_triggered = true;
        }

    }
}