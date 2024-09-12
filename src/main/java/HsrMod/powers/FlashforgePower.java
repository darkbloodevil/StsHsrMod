package HsrMod.powers;

import HsrMod.action.HsrDamageAction;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/5 17:06
 * @description
 */
public class FlashforgePower extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(FlashforgePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static int toughness_reduction = 6;
    boolean has_block;
    int draws = 2;

    public FlashforgePower(AbstractCreature owner, AbstractCreature source, int amount, int draws) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount = amount;
        this.draws = draws;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (AbstractDungeon.player.currentBlock > 0) {
            has_block = true;
        }
    }


    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {

        if (has_block && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.owner.currentBlock <= 0) {
            this.flash();
            this.addToTop(new HsrDamageAction(info.owner, DamageUtil.deal_followUp_info((AbstractPlayer) this.owner, this.amount * 2, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            addToBot(new DrawCardAction(draws));
        }


        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount,get_toughness_reduction());
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new HsrDamageAction(AbstractDungeon.getRandomMonster(), DamageUtil.deal_followUp_info((AbstractPlayer) this.owner, this.amount * 2, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}
