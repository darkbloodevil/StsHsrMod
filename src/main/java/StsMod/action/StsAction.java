package StsMod.action;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StsAction extends AbstractGameAction {
    // 伤害信息
    public DamageInfo info;

    public StsAction(AbstractMonster target, DamageInfo info) {
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        this.target.damage(this.info);
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead
                && !this.target.hasPower("Minion")) {
            this.addToTop(new DrawCardAction(1));
        }
        this.isDone = true;
    }

}