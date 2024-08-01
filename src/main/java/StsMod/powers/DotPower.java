package StsMod.powers;

import StsMod.HsrMod;
import StsMod.action.DotLoseHpAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * @author darkbloodevil
 * @date 2024/7/23 21:25
 */
public class DotPower extends BasePower {
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public int damage_amount;

    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

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

    }

    public void dot_damage(String from) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            this.addToBot(new DotLoseHpAction(this.owner, this.source, this.damage_amount, AbstractGameAction.AttackEffect.NONE));
            HsrMod.logger.info(String.format("==========%s is now triggered from=========", ID, from));

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
//                this.owner.powers.remove(this);
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));

            }

        }
    }
}