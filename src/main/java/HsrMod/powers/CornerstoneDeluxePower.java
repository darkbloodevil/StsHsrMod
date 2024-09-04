package HsrMod.powers;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/3 19:43
 * @description
 */
public class CornerstoneDeluxePower extends BasePower {
    public static final String POWER_ID = makeID(CornerstoneDeluxePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public CornerstoneDeluxePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        HsrDamageInfo h_info=HsrDamageInfo.to_hsr_info(info);

        if (!h_info.is_aoe &&h_info.type== DamageInfo.DamageType.NORMAL){
            addToBot(new GainBlockAction(owner, amount));
        }

    }

    /**
     * 对群的攻击每一下都叠一下
     *
     * @param use_length
     */
    @Override
    public void onDamageAllEnemies(int[] use_length) {
        super.onDamageAllEnemies(use_length);
        addToBot(new GainBlockAction(owner, amount * use_length.length));
//        addToBot(new GainBlockAction(owner, amount));

    }

}