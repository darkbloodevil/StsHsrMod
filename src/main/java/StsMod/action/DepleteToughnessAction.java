package StsMod.action;

import StsMod.powers.ToughnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DepleteToughnessAction extends AbstractGameAction {
    AbstractCreature owner;

    public DepleteToughnessAction(AbstractMonster target, AbstractCreature owner, int amount) {
        this.target = target;
        this.amount = amount;
        this.owner = owner;
    }

    @Override
    public void update() {
        if (amount > 0) {
            addToBot(new ApplyPowerAction(target, owner, new ToughnessPower(target, this.owner,-this.amount), -this.amount));

        }
        this.isDone = true;
    }
}