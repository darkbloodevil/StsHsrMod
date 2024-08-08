package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

/**
 * @author darkbloodevil
 * @date 2024/8/8 16:53
 * @description
 */
public class ButterflyFlurryAction extends AbstractGameAction {

    private HsrDamageInfo info;
    private static final float DURATION = 0.1F;
    AbstractCard card;
    AbstractPlayer p;
    int amount;

    public ButterflyFlurryAction(AbstractCreature target, AbstractCard card, HsrDamageInfo info,int amount) {
        this.info = info;
        setValues(target, info);
        this.card = card;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.p = AbstractDungeon.player;
        this.amount = 5;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            if (((this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractDungeon.player.hand.addToTop(card);
                addToBot(new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, new StrengthPower((AbstractCreature) p, this.amount), this.amount));
                addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, new LoseStrengthPower((AbstractCreature) p, this.amount), this.amount));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
        isDone = true;
    }
}

