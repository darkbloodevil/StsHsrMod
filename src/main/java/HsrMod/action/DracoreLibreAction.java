package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/25 14:32
 * @description
 */
public class DracoreLibreAction extends AbstractGameAction {
    int max_amount;
    private AbstractPlayer p;
    AbstractMonster target;
    boolean upgraded;
    int damage;
    int magic;
    int toughness_reduction;

    public DracoreLibreAction(AbstractMonster m, boolean upgraded, int damage, int magic, int toughness_reduction) {
        this.max_amount = 3;
        this.p = AbstractDungeon.player;
//        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.DAMAGE;
        this.target = m;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_MED;
        this.damage = damage;
        this.magic = magic;
        this.toughness_reduction = toughness_reduction;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                tmp.addToRandomSpot(c);
            }
            if (tmp.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, Math.min(this.max_amount, tmp.size()), true, "");

            tickDuration();
            return;
        }
        int size = AbstractDungeon.gridSelectScreen.selectedCards.size();
        if (size >= 2) {
            addToBot(new DrawCardAction(magic));
        }
        if (size < 3) {
            addToBot(new DamageAction(target, new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
            if (size >= 1) {
                addToBot(new DamageAction(target, new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
            }
        }else {
            addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
            addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
        }
        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            AbstractCard copy_card = c.makeStatEquivalentCopy();
            copy_card.unhover();
            AbstractDungeon.player.discardPile.addToTop(copy_card);
            if (this.p.hand.contains(c)) {
                AbstractDungeon.player.hand.removeCard(c);
            }
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();

        tickDuration();
        isDone = true;
    }
}
