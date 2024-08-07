package HsrMod.action;

import HsrMod.powers.BreakPower;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BreakAction extends AbstractGameAction {
    public BreakAction(AbstractMonster target, AbstractCreature source) {
        this(target, source, 1);
    }

    public BreakAction(AbstractMonster target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.actionType = ActionType.DEBUFF;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            StunMonsterPower stun_power=new StunMonsterPower((AbstractMonster)this.target, this.amount);
            stun_power.type= AbstractPower.PowerType.BUFF;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, stun_power, this.amount));
            AbstractDungeon.actionManager.addToTop(new BreakTransformAction((AbstractMonster) target, source, new BreakPower(target, source)));

        }

        this.tickDuration();
        isDone=true;
    }
}
