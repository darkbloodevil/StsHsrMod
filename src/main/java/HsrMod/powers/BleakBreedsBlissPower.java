package HsrMod.powers;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/3 14:34
 * @description
 */
public class BleakBreedsBlissPower  extends BasePower {
    public static final String POWER_ID = makeID(BleakBreedsBlissPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private ArrayList<String> under_half;
    int damage_amount;

    public BleakBreedsBlissPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 1);
        this.amount=1;
    }

    public BleakBreedsBlissPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount=amount;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        super.onLoseHp(damageAmount);
        if (damageAmount>0){
            if ((double) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth <= 0.5){
                addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) damageAmount));
            }
        }

        return damageAmount;

    }
    @Override
    public void atStartOfTurn() {

        this.amount--;
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));

        }


    }
}