package HsrMod.powers;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.core.HsrDamageInfo;
import HsrMod.util.DamageUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/1 17:16
 * @description
 */
public class FineIwillDoItMyselfPower extends BasePower {
    public static final String POWER_ID = makeID(FineIwillDoItMyselfPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private ArrayList<String> under_half;
    int damage_amount;
    int toughness_reduction=4;
    AbstractGameAction trigger_action;

    public FineIwillDoItMyselfPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.damage_amount = amount;
        under_half = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((double) monster.currentHealth / monster.maxHealth < 0.5) {
                under_half.add(monster.id);
            }
        }
        this.trigger_action = new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.hasPower(POWER_ID)) {
                    AbstractPower ap = AbstractDungeon.player.getPower(POWER_ID);
                    ap.onSpecificTrigger();
                }
                isDone = true;
            }
        };
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], damage_amount, toughness_reduction);
    }

    @Override
    public void onSpecificTrigger() {
        boolean triggered = false;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((double) monster.currentHealth / monster.maxHealth < 0.5 && !under_half.contains(monster.id)) {
                under_half.add(monster.id);
                triggered = true;
                addToTop(new HsrDamageAllEnemiesAction(DamageUtil.deal_followUp_info(AbstractDungeon.player, damage_amount, toughness_reduction)));
                ;
            }
        }
        if (!triggered) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        addToBot(this.trigger_action);
    }

    //    @Override
//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
//        super.atDamageFinalGive(damage, type);
//
//        if (damage >= 0) {
//            boolean triggered = false;
//
//            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//                if ((double) monster.currentHealth / monster.maxHealth < 0.5 && !under_half.contains(monster.id)) {
//                    under_half.add(monster.id);
//                    triggered = true;
//                    addToTop(new HsrDamageAllEnemiesAction(new HsrDamageInfo(AbstractDungeon.player, damage_amount, DamageInfo.DamageType.NORMAL, toughness_reduction)));
//                }
//            }
//            if (!triggered) {
//                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
//            }
//
//        }
////        else {
////            addToTop(new DamageAction(target,new HsrDamageInfo(info.owner,0,info.type,0)));
////        }
//        return damage;
//    }
}