package HsrMod.action;

import HsrMod.util.CardAdapter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author darkbloodevil
 * @date 2024/8/7 17:54
 * @description
 */
public class AmidstTheRejoicingCloudsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public AmidstTheRejoicingCloudsAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues(target, source, amount);
        this.duration = DURATION;
        this.amount=1;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.amount < 0) {
                AbstractDungeon.handCardSelectScreen.open("handCardSelectScreen", 99, true, true);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
            if (this.p.hand.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open("handCardSelectScreen", 1, false);
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
