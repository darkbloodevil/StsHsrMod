package HsrMod.powers;

import HsrMod.HsrMod;
import HsrMod.action.DotLoseHpAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * @author darkbloodevil
 * @date 2024/7/23 21:25
 */
public class DotPower extends BasePower {
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public int damage_amount;

    public DotPower(String POWER_ID, AbstractCreature owner, AbstractCreature source, int amount, int damage_amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.damage_amount = damage_amount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateDescription();
        flash();
    }

    public void stackDamage(int stackAmount){
        HsrMod.logger.info(String.format("===stackDamage before %d  amount %d===", damage_amount, stackAmount));
        this.damage_amount+=stackAmount;
        HsrMod.logger.info(String.format("===stackDamage after %d  amount %d===", damage_amount, stackAmount));
    }

    public void dot_damage(String from) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            this.addToBot(new DotLoseHpAction(this.owner, this.source, this.damage_amount, AbstractGameAction.AttackEffect.POISON));
            HsrMod.logger.info(String.format("==========%s is now triggered from %s=========", ID, from));
            HsrMod.logger.info(String.format("===dot amount %d===", damage_amount));

        }
    }

    @Override
    public void atStartOfTurn() {
        dot_damage("atStartOfTurn");
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