package HsrMod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/25 8:58
 * @description
 */
public class PrayerOfAbyssFlowerPower extends BasePower {
    public static final String POWER_ID = makeID(DivineProvisionPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PrayerOfAbyssFlowerPower(AbstractCreature owner, AbstractCreature source,int amount, int magic) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount2=magic;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount2);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public int onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        if (damageAmount > 0) {
            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, Math.min(damageAmount,amount2)));

            amount--;
            flash();
            if (amount<=0){
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }

        }

        return damageAmount;

    }
}