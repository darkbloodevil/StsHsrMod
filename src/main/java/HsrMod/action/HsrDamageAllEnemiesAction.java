package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/1 17:58
 * @description
 */
public class HsrDamageAllEnemiesAction extends AbstractGameAction {
    HsrDamageInfo info;
    AbstractGameAction.AttackEffect effect;
    public HsrDamageAllEnemiesAction(HsrDamageInfo info){
        this.info=info;
        this.effect= AttackEffect.NONE;
    }
    public HsrDamageAllEnemiesAction(HsrDamageInfo info,AbstractGameAction.AttackEffect effect){
        this.info=info;
        this.effect=effect;
    }
    @Override
    public void update() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new HsrDamageAction(monster,info,effect));
        }
        isDone=true;
    }
}
