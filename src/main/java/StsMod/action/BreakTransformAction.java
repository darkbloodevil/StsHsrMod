package StsMod.action;

import StsMod.powers.BasePower;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author darkbloodevil
 * @date 2024/7/23 22:53
 * @description 实现击破状态转化
 */
public class BreakTransformAction extends AbstractGameAction {
    private BasePower next_power;

    public BreakTransformAction(AbstractMonster target, AbstractCreature source, BasePower next_power) {
        this.target = target;
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
        this.next_power=next_power;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            addToTop(new ApplyPowerAction(target,source,next_power,next_power.amount));
        }

        this.tickDuration();
        this.isDone=true;
    }
}

