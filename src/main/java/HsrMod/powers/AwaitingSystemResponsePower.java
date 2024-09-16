package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/2 17:18
 * @description
 */
public class AwaitingSystemResponsePower extends BasePower {
    public static final String POWER_ID = makeID(AwaitingSystemResponsePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public AwaitingSystemResponsePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 0);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        HsrDamageInfo h_info =HsrDamageInfo.to_hsr_info(info) ;
        if (target != null && target != this.owner && info.type == DamageInfo.DamageType.NORMAL && !h_info.is_aoe) {
            int random = RandomUtil.random_int(3);
            if (random == 0) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
                if (!target.hasPower("Artifact")) {
                    this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (random == 1) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
            } else {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
            }
        }
    }

    @Override
    public void onDamageAllEnemies(int[] damage) {
        super.onDamageAllEnemies(damage);
        int random = RandomUtil.random_int(3);

        for (AbstractMonster target : AbstractDungeon.getMonsters().monsters) {
            if (random == 0) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
                if (!target.hasPower("Artifact")) {
                    this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (random == 1) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
            } else {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
            }

        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}