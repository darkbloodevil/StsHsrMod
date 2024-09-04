package HsrMod.powers;

import HsrMod.action.BreakAction;
import HsrMod.interfaces.AtDepletingToughness;
import HsrMod.interfaces.AtWeaknessBreak;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/15 17:51
 * @description
 */
public class DotVulnerablePower extends BasePower {
    public static final String POWER_ID = makeID(DotVulnerablePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public static float scaler=1.5f;

    public DotVulnerablePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }
    @Override
    public void updateDescription() {
        this.description=String.format(DESCRIPTIONS[0],scaler);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            amount--;
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
        updateDescription();
        flash();
    }


}