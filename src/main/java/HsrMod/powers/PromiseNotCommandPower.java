package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/7/30 17:01
 * @description
 */
public class PromiseNotCommandPower extends BasePower {
    public static final String POWER_ID = makeID(PromiseNotCommandPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public int damage_amount;
    public int toughness_reduction=6;

    public PromiseNotCommandPower(AbstractCreature owner, AbstractCreature source, int amount, int damage_amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.damage_amount = damage_amount;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new DamageAction(info.owner, new HsrDamageInfo(this.owner, this.damage_amount, DamageInfo.DamageType.NORMAL,this.toughness_reduction,true), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            amount--;
            if (this.amount == 0) {
//                this.owner.powers.remove(this);
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));

            }

        }
    }
}