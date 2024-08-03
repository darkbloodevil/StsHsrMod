package HsrMod.action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import javax.swing.*;

/**
 * @author darkbloodevil
 * @date 2024/8/3 16:25
 * @description
 */
public class VoxHarmoniqueOpusCosmiqueAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    int amount;
    private AbstractPlayer p;

    public VoxHarmoniqueOpusCosmiqueAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group) {
                tmp.addToRandomSpot(c);
            }
            if (tmp.isEmpty()){
                isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, Math.min(this.amount,tmp.size()), "", false);

            tickDuration();
            return;

        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractCard copy_card=c.makeStatEquivalentCopy();
                copy_card.unhover();
                if (copy_card.cost>0){
                    copy_card.cost = 0;
                    copy_card.costForTurn = 0;
                    copy_card.isCostModified = true;
                    copy_card.superFlash(Color.GOLD.cpy());
                }

                AbstractDungeon.player.drawPile.addToTop(copy_card);
                AbstractDungeon.player.drawPile.removeCard(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

        }
        tickDuration();
        isDone = true;

    }
}

