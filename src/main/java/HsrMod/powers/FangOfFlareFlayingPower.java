package HsrMod.powers;

import HsrMod.HsrMod;
import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.interfaces.AtDepletingToughness;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/14 16:59
 * @description
 */
public class FangOfFlareFlayingPower extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(FangOfFlareFlayingPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    int damage;
    public static final int trigger_threshold=8;
    int toughness_reduction=4;

    public FangOfFlareFlayingPower(AbstractCreature owner, int damage) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 0);
        this.damage=damage;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
        this.damage+=stackAmount;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        this.amount++;
        while (amount>=trigger_threshold){
            this.amount-=trigger_threshold;
            addToBot(new HsrDamageAllEnemiesAction(DamageUtil.deal_followUp_info(AbstractDungeon.player,damage,toughness_reduction)));
        }
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}