package HsrMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

/**
 * @author darkbloodevil
 * @date 2024/8/7 15:31
 * @description
 */
public class DiscardTypeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean endTurn;
    public static int numDiscarded;
    private static float DURATION;
    AbstractCard.CardType exclude;

    public DiscardTypeAction(int amount, AbstractCard.CardType exclude) {
        this(AbstractDungeon.player, AbstractDungeon.player, amount, false, exclude);
    }

    public DiscardTypeAction(AbstractCreature target, AbstractCreature source, int amount, boolean endTurn, AbstractCard.CardType exclude) {
        this.p = (AbstractPlayer) target;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
        this.endTurn = endTurn;
        DiscardTypeAction.DURATION = Settings.ACTION_DUR_XFAST;
        this.duration = DURATION;
        this.exclude = exclude;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                if (c.type != this.exclude) {
                    cardGroup.addToTop(c);
                }
            }

            for (int i = 0; i < this.amount; i++) {
                if (!cardGroup.isEmpty()) {
                    AbstractCard c = cardGroup.getRandomCard(AbstractDungeon.cardRandomRng);
                    this.p.hand.moveToDiscardPile(c);
                    cardGroup.removeCard(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(this.endTurn);
                } else {
                    break;
                }
            }

            this.isDone = true;
        }
    }

}
