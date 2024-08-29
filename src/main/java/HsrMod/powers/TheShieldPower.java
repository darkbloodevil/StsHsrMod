package HsrMod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/13 16:39
 * @description
 */
public class TheShieldPower extends BasePower {
    public static final String POWER_ID = makeID(TheShieldPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public TheShieldPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;

    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount,amount);
    }

    @Override
    public void atStartOfTurn() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new GainBlockAction(monster, amount));
        }
    }
}