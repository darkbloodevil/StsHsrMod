package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.AtWeaknessBreak;
import HsrMod.interfaces.ToughnessReductionInterface;
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
public class VictoryRushPower extends BasePower implements AtWeaknessBreak, ToughnessReductionInterface {
    public static final String POWER_ID = makeID(VictoryRushPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public int toughness_reduction;

    public VictoryRushPower(AbstractCreature owner, AbstractCreature source, int amount,int reduction) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.toughness_reduction=reduction;
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount,toughness_reduction);
    }


    @Override
    public void at_weakness_break(AbstractCreature target) {

        this.flash();
        this.addToBot(new DamageAction(target, new HsrDamageInfo(this.owner, this.amount, DamageInfo.DamageType.NORMAL, this.toughness_reduction, true), AbstractGameAction.AttackEffect.FIRE, true));

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}