package HsrMod.powers;

import HsrMod.action.ApplyDotAction;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/11 14:15
 * @description
 */
public class TrendOfTheUniversalMarketPower extends BasePower {
    public static final String POWER_ID = makeID(TrendOfTheUniversalMarketPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TrendOfTheUniversalMarketPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToBot(new ApplyDotAction(info.owner, this.owner, new FireDotPower(info.owner, this.owner, 2, this.amount)));
        }

        return damageAmount;
    }
}