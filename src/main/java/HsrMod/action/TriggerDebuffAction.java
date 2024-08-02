package HsrMod.action;

import HsrMod.powers.DotPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

/**
 * @author darkbloodevil
 * 引爆所有debuff（atStartOfTurn的效果），引爆amount次
 */

public class TriggerDebuffAction extends AbstractGameAction {
    public TriggerDebuffAction(AbstractMonster target, AbstractCreature source) {
        this(target, source, 1);
    }

    public TriggerDebuffAction(AbstractMonster target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.actionType = ActionType.DEBUFF;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        for (int i = 0; i < this.amount; i++) {
            ArrayList<DotPower> triggered = new ArrayList<>();
            for (AbstractPower power : this.target.powers) {
                if (power instanceof DotPower) {
                    if (!triggered.contains((DotPower) power)) {
                        ((DotPower) power).dot_damage(TriggerDebuffAction.class.getSimpleName());
                        triggered.add((DotPower) power);
                    }
                }
            }
        }
        isDone = true;
        tickDuration();

    }
}
