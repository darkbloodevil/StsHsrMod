package HsrMod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/25 11:48
 * @description
 */
public class BackupDancerPower extends BasePower{
    public static final String POWER_ID = makeID(BackupDancerPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BackupDancerPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 0);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
