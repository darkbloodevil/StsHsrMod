package HsrMod.powers;

import HsrMod.action.HsrDamageAction;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/14 15:55
 * @description
 */
public class GirlPower extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(GirlPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public int toughness_reduction = 4;

    public GirlPower(AbstractCreature owner, AbstractCreature source, int amount, int damage_amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount2 = damage_amount;
        this.amount = amount;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.owner.currentBlock > 0) {
            this.flash();
            this.addToTop(new HsrDamageAction(info.owner, DamageUtil.deal_followUp_info((AbstractPlayer) this.owner, this.amount2, this.toughness_reduction), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount2);
    }

    @Override
    public void atStartOfTurn() {

        this.amount--;
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));

        }


    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}