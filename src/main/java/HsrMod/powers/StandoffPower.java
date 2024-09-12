package HsrMod.powers;

import HsrMod.interfaces.AtWeaknessBreak;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/6 11:29
 * @description 波提欧对峙
 */
public class StandoffPower extends BasePower implements AtWeaknessBreak {
    public static final String POWER_ID = makeID(StandoffPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public StandoffPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount,amount);
    }

    /**
     * 怪物回合结束，移除
     * @param isPlayer
     */
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer){
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            AbstractPlayer p=AbstractDungeon.player;
            if (p.hasPower(StandoffPower.POWER_ID))
                addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(StandoffPower.POWER_ID)));
        }
        flash();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if (this.owner!= AbstractDungeon.player){
            onSpecificTrigger();
        }
    }

    /**
     * 对峙结束 回能回费 解除对峙
     */
    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        AbstractPlayer p=AbstractDungeon.player;
        addToBot(new GainEnergyAction(amount));
        addToBot(new DrawCardAction(amount));
        if (p.hasPower(StandoffPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(StandoffPower.POWER_ID)));
        if (p.hasPower(TauntedPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(StandoffPower.POWER_ID)));
        }
        if (p.hasPower(TauntPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(TauntPower.POWER_ID)));
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        if (owner.hasPower(TauntedPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(owner, owner, owner.getPower(TauntedPower.POWER_ID)));
        }
        if (owner.hasPower(TauntPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(owner, owner, owner.getPower(TauntPower.POWER_ID)));
        }

    }

    /**
     * 对峙状态的角色才可造成伤害
     * @param info
     * @param damageAmount
     * @return
     */
    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner!=null&& info.owner.hasPower(StandoffPower.POWER_ID))
            return super.onAttackedToChangeDamage(info, damageAmount);
        else
            return 0;
    }

    @Override
    public void at_weakness_break(AbstractCreature target) {
        if (!this.owner.equals(target)){
            onSpecificTrigger();
        }
    }
}