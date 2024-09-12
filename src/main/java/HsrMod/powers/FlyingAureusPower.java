package HsrMod.powers;

import HsrMod.action.HsrDamageAction;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/10 17:05
 * @description
 */
public class FlyingAureusPower  extends BasePower implements ToughnessReductionInterface {
    public static final String POWER_ID = makeID(FlyingAureusPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    int damage_amount=0;
    int threshold=12;
    int attack_times=6;
    AbstractCreature target;
    int exhaust_stack=5;

    public FlyingAureusPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, 0);
        this.damage_amount=amount;
    }

    /**
     * 不累计点数，累积伤害量
     * @param stackAmount
     */
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(0);
        this.damage_amount+=stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],threshold,damage_amount,attack_times,get_toughness_reduction());
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        HsrDamageInfo h_info=HsrDamageInfo.to_hsr_info(info);
        if (!h_info.is_aoe&&h_info.type== DamageInfo.DamageType.NORMAL){
            this.amount++;
        }
        if (target!=this.owner&&!target.isDead){
            this.target=target;
        }

        flash();
        updateDescription();
    }

    @Override
    public void onDamageAllEnemies(int[] damage) {
        super.onDamageAllEnemies(damage);
        this.amount++;
        flash();
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        this.amount+=exhaust_stack;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (amount>=threshold){
            flash();
            updateDescription();
            if (this.target==null || this.target.isDead){
                this.target= AbstractDungeon.getRandomMonster();
            }
            this.amount-=threshold;
            for (int i = 0; i < attack_times; i++) {
                if (!target.isDead){
                    addToBot(new HsrDamageAction(target, DamageUtil.deal_followUp_info(AbstractDungeon.player,damage_amount,get_toughness_reduction())));
                    // 这部分追加攻击不能叠层
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (AbstractDungeon.player.hasPower(FlyingAureusPower.POWER_ID)){
                                AbstractDungeon.player.getPower(FlyingAureusPower.POWER_ID).amount--;
                            }
                            this.isDone=true;
                        }
                    });
                }
            }
        }
    }

    @Override
    public int get_toughness_reduction() {
        return 2;
    }
}