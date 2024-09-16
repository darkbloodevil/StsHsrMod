package HsrMod.powers;

import HsrMod.HsrMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/13 22:43
 * @description
 */
public class StunMonsterPower extends BasePower {
    public static final String POWER_ID = makeID(StunMonsterPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

    public StunMonsterPower(AbstractMonster owner, AbstractCreature source) {
        this(owner, source, 1);
    }

    public StunMonsterPower(AbstractMonster owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void atEndOfRound() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

    }

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (StunMonsterPower.this.owner instanceof AbstractMonster) {
                    StunMonsterPower.this.moveByte = ((AbstractMonster) StunMonsterPower.this.owner).nextMove;
                    StunMonsterPower.this.moveIntent = ((AbstractMonster) StunMonsterPower.this.owner).intent;

                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        StunMonsterPower.this.move = (EnemyMoveInfo) f.get(StunMonsterPower.this.owner);
                        EnemyMoveInfo stunMove = new EnemyMoveInfo(StunMonsterPower.this.moveByte, AbstractMonster.Intent.STUN, -1, 0, false);
                        f.set(StunMonsterPower.this.owner, stunMove);
                        ((AbstractMonster) StunMonsterPower.this.owner).createIntent();
                    } catch (NoSuchFieldException | IllegalAccessException var3) {
                        ReflectiveOperationException e = var3;
                        e.printStackTrace();
                    }
                }

                this.isDone = true;
            }
        });
    }

    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (m.intent!=AbstractMonster.Intent.STUN){
                m.setMove(m.nextMove, m.intent);
            }else if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }


            m.createIntent();
            m.applyPowers();
        }
    }

    /**
     * 一旦目标死亡，立刻移除自己
     */
    @Override
    public void onDeath() {
        super.onDeath();
        HsrMod.logger.info(owner.id + " has dead. remove stun");
        this.type=PowerType.DEBUFF;
//        this.onRemove();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
//        StunMonsterPower removeMe=(StunMonsterPower)owner.getPower(StunMonsterPower.POWER_ID);
//        removeMe.onRemove();
//        owner.powers.remove(removeMe);
//        AbstractDungeon.onModifyPower();
    }
}
