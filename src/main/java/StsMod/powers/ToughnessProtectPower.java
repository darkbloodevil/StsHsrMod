package StsMod.powers;

import StsMod.action.BreakAction;
import StsMod.action.BreakTransformAction;
import StsMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static StsMod.StsMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/23 22:50
 * @description 韧性条保护状态
 */
public class ToughnessProtectPower extends BasePower {
    public static final String POWER_ID = makeID(ToughnessProtectPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public ToughnessProtectPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 1);
    }

    public ToughnessProtectPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void atEndOfTurn(boolean is_player) {
        if (!is_player) {
            amount--;
            if (this.amount == 0) {

                addToTop(new BreakTransformAction((AbstractMonster) owner, source, new ToughnessPower(owner, source, ToughnessUtil.get_toughness((AbstractMonster) owner))));

                addToBot(new RemoveSpecificPowerAction(owner, owner, this));

                flash();
            }
        }
    }
}