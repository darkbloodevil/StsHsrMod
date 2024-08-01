package StsMod.powers;

import StsMod.action.BreakAction;
import StsMod.interfaces.AtWeaknessBreak;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static StsMod.HsrMod.makeID;

public class ToughnessPower extends BasePower {
    public static final String POWER_ID = makeID(ToughnessPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean is_toughness_protect = false;

    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ToughnessPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        if (stackAmount > 0) {
            super.stackPower(stackAmount);
            is_toughness_protect = false;
        } else {
            if (!is_toughness_protect) {
                super.stackPower(stackAmount);
                if (this.amount <= 0) {
                    this.amount = 0;
                    this.fontScale = 8.0F;
                    addToTop(new BreakAction((AbstractMonster) this.owner, this.source));
                    // 弱点击破时生效
                    for (AbstractPower power : AbstractDungeon.player.powers) {
                        if (power instanceof AtWeaknessBreak) {
                            AtWeaknessBreak dt_power = (AtWeaknessBreak) power;
                            dt_power.at_weakness_break(owner);
                            power.flash();
                        }
                    }


                    this.is_toughness_protect = true;
                }
            }
        }

    }
}