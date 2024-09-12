package HsrMod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/6 11:00
 * @description 发动嘲讽的角色用这个
 */
public class TauntPower extends BasePower {
    public static final String POWER_ID = makeID(TauntPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public TauntPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        amount--;
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            AbstractPlayer p=AbstractDungeon.player;
            if (owner!=p&&p.hasPower(TauntedPower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(StandoffPower.POWER_ID)));
            }
        }

        updateDescription();
        flash();
    }

    /**
     * 死亡时解除嘲讽
     */
    @Override
    public void onDeath() {
        super.onDeath();
        AbstractPlayer p=AbstractDungeon.player;
        if (owner!=p&&p.hasPower(TauntedPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(StandoffPower.POWER_ID)));
        }
    }

    /**
     * 嘲讽不可累加
     * @param stackAmount
     */
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
    }
}