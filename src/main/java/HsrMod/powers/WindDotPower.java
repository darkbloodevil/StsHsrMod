package HsrMod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/24 22:07
 */
public class WindDotPower extends DotPower{
    public static final String POWER_ID = makeID(WindDotPower.class.getSimpleName());

    public WindDotPower(AbstractCreature owner, AbstractCreature source, int amount, int damage_amount) {
        super(POWER_ID, owner, source, amount, damage_amount);
    }
    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount2);
    }
}
