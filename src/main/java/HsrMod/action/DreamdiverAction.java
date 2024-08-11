package HsrMod.action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author darkbloodevil
 * @date 2024/8/9 11:52
 * @description
 */
public class DreamdiverAction extends AbstractGameAction {
    int amount;
    private AbstractPlayer p;

    public DreamdiverAction() {
        this.amount = 1;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.discardPile.group) {
                tmp.addToRandomSpot(c);
            }
            if (tmp.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, Math.min(this.amount, tmp.size()), true, "");
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractCard copy_card = c.makeStatEquivalentCopy();
                copy_card.unhover();
                AbstractDungeon.player.drawPile.addToTop(copy_card);
                AbstractDungeon.player.discardPile.removeCard(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

        }
        tickDuration();
        isDone = true;
    }
}
