package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.AtWeaknessBreak;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/30 18:02
 * @description
 */
public class VictoryRushPower extends BasePower implements AtWeaknessBreak {
    public static final String POWER_ID = makeID(VictoryRushPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public int toughness_reduction = 2;

    public VictoryRushPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void at_weakness_break(AbstractCreature target) {

        this.flash();
        this.addToBot(new DamageAction(target, new HsrDamageInfo(this.owner, this.amount, DamageInfo.DamageType.NORMAL, this.toughness_reduction, true), AbstractGameAction.AttackEffect.FIRE, true));

    }
}