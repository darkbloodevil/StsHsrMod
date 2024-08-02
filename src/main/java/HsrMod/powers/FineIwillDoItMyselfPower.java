package HsrMod.powers;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.core.HsrDamageInfo;
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
    int damage;

    public FineIwillDoItMyselfPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        under_half = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((double) monster.currentHealth / monster.maxHealth < 0.5) {
                under_half.add(monster.id);
            }
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        super.atDamageFinalGive(damage, type);

        if (damage>=0){
            boolean triggered=false;

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((double) monster.currentHealth / monster.maxHealth < 0.5 && !under_half.contains(monster.id)) {
                    under_half.add(monster.id);
                    triggered=true;

                    addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(AbstractDungeon.player,7, DamageInfo.DamageType.NORMAL)));
                }
            }
            if (!triggered){
                addToBot(new RemoveSpecificPowerAction( owner, owner, this));
            }

        }
//        else {
//            addToTop(new DamageAction(target,new HsrDamageInfo(info.owner,0,info.type,0)));
//        }
        return damage;

    }
}