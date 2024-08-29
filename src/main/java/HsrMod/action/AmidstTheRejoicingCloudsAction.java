package HsrMod.action;

import HsrMod.cards.skills.AmidstTheRejoicingClouds;
import HsrMod.util.CardAdapter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

/**
 * @author darkbloodevil
 * @date 2024/8/7 17:54
 * @description
 */
public class AmidstTheRejoicingCloudsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(AmidstTheRejoicingCloudsAction.class.getSimpleName());

    public AmidstTheRejoicingCloudsAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(target, source, amount);
        this.duration = DURATION;
        this.amount=amount;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }


            if (!this.p.hand.isEmpty()) {
                AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[0], 1, false);
            }
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;

        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            CardAdapter adapter= new CardAdapter();
            adapter.setCostForTurn(0);
            adapter.setDamage(this.amount);
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                addToBot(new AdaptCardAction(c, adapter));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
        isDone = true;
    }
}
