package StsMod.action;

import StsMod.cards.sam.FireflyTypeIVDeathstarOverload;
import StsMod.cards.sam.FireflyTypeIVPyrogenicDecimation;
import StsMod.cards.sam.OrderAerialBombardment;
import StsMod.cards.sam.OrderFlarePropulsion;
import StsMod.util.RandomSelection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Objects;


/**
 * @author darkbloodevil
 * @date 2024/7/31 17:54
 * @description
 */
public class FireFlyTypeIVCompleteCombustionAction extends AbstractGameAction {
    boolean is_upgraded;
    private boolean retrieveCard = false;
    private boolean returnColorless = false;
    private AbstractCard.CardType cardType = null;

    public FireFlyTypeIVCompleteCombustionAction(boolean is_upgraded) {
        this.is_upgraded = is_upgraded;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> generatedCards = this.generate_cards(3);
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], (this.cardType != null));
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                AbstractCard card_to_remove = null;
                for (AbstractCard ignored : generatedCards) {
                    if (Objects.equals(ignored.cardID, disCard.cardID)) {
                        card_to_remove = ignored;
                        continue;
                    }
                    if (is_upgraded)
                        ignored.upgrade();
                }
                if (card_to_remove != null) generatedCards.remove(card_to_remove);

                if (is_upgraded)
                    disCard.upgrade();
                disCard.current_x = -1000.0F * Settings.xScale;
                if (this.amount == 1) {
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    }
                    for (AbstractCard card : generatedCards) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card.makeStatEquivalentCopy(), true, true));
                    }
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
        isDone = true;
    }

    private ArrayList<AbstractCard> generate_cards(int n) {
        ArrayList<AbstractCard> pile = new ArrayList<>();
        pile.add(new OrderFlarePropulsion());
        pile.add(new OrderAerialBombardment());
        pile.add(new FireflyTypeIVPyrogenicDecimation());
        pile.add(new FireflyTypeIVDeathstarOverload());

        ArrayList<AbstractCard> chosen = new ArrayList<>();
        for (int i : RandomSelection.selectRandomElements(pile.size(), n)) {
            chosen.add(pile.get(i));
        }


        return chosen;
    }
}
