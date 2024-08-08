package HsrMod.powers;

import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
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
    int turns = 2;
    int toughness_reduction = 2;

    public LightningLordPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 3);
    }

    public LightningLordPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        if (amount==114514){
            this.amount=3;
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.turns);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (stackAmount==114514){
            this.amount=3;
        }
        if (this.amount > 10) {
            this.amount = 10;
        }
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        turns--;
        if (turns <= 0) {
            turns = 3;
            int damage = 5;
            if (amount >= 6) {
                damage = amount;
            }
            for (int i = 0; i < this.amount; i++) {
                AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(true);
                this.addToTop(new DamageAction(monster,
                        new HsrDamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, toughness_reduction, true),
                        AbstractGameAction.AttackEffect.LIGHTNING));

            }
            this.amount = 0;
        }

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}