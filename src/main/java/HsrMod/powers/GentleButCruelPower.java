package HsrMod.powers;

import HsrMod.action.ApplyDotAction;
import HsrMod.action.HsrDamageAction;
import HsrMod.cards.attacks.CaressingMoonlight;
import HsrMod.cards.attacks.TwilightTrill;
import HsrMod.cards.powers.GentleButCruel;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/9 20:24
 * @description
 */
public class GentleButCruelPower extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(GentleButCruelPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public GentleButCruelPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        amount2 = 1;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        amount2 = 1;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        HsrDamageInfo h_info=HsrDamageInfo.to_hsr_info(info);
        if (h_info.is_follow_up||h_info.is_aoe) return;

        if (info.type != DamageInfo.DamageType.NORMAL) return;

        if (amount2 ==1) {
            addToBot(new HsrDamageAction(target, DamageUtil.deal_followUp_info(AbstractDungeon.player, amount, get_toughness_reduction())));
            addToBot(new ApplyDotAction(target, owner, new LightningDotPower(target, owner, 2, amount)));
            amount2 = 0;
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        if (usedCard instanceof GentleButCruel || usedCard instanceof CaressingMoonlight || usedCard instanceof TwilightTrill) {
            amount2 = 1;
        }
    }


    @Override
    public int get_toughness_reduction() {
        return 2;
    }
    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],amount,get_toughness_reduction(),amount);
    }
}
