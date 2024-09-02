package HsrMod.powers;

import HsrMod.action.HsrDamageAction;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/8 14:22
 * @description
 */
public class LightningLordPower extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(LightningLordPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    int turns = 3;
    int toughness_reduction = 2;

    public LightningLordPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 3);
    }

    public LightningLordPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        if (amount == 114514) {
            this.amount = 3;
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.turns, this.get_damage_per_attack(), this.amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (stackAmount == 114514 || this.amount>=114514) {
            this.amount = 3;
        }
        if (this.amount > 10) {
            this.amount = 10;
        }
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    private int get_damage_per_attack() {
        if (amount >= 6)
            return amount;
        return 5;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        turns--;
        if (turns <= 0) {
            turns = 3;
            int damage = get_damage_per_attack();

            for (int i = 0; i < this.amount; i++) {
                AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(true);
                // 连续攻击 所以是top
                this.addToTop(new HsrDamageAction(monster,
                        DamageUtil.deal_followUp_info(AbstractDungeon.player, damage, toughness_reduction),
                        AbstractGameAction.AttackEffect.LIGHTNING));
            }
            this.amount = 0;
        }
        this.updateDescription();
        this.flash();

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}