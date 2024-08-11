package HsrMod.action;

import HsrMod.util.CardAdapter;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author darkbloodevil
 * @date 2024/8/7 17:37
 * @description
 */
public class AdaptCardAction extends AbstractGameAction {
    AbstractCard target_card;
    CardAdapter adapter;

    public AdaptCardAction(AbstractCard target_card, CardAdapter ca) {
        this.target_card = target_card;
        this.adapter = ca;
    }

    @Override
    public void update() {
        AbstractCard copy_card = target_card.makeStatEquivalentCopy();
        copy_card.unhover();
        if (copy_card.baseDamage >= 0) {
            copy_card.baseDamage += adapter.getDamage();

        }
        if (copy_card.cost >= 0 && adapter.is_cost_modified) {
            copy_card.cost = adapter.getCost();
            copy_card.costForTurn = adapter.getCost();
            copy_card.superFlash(Color.GOLD.cpy());
        }
        if (copy_card.cost >= 0 && adapter.is_costForTurn_modified) {
            copy_card.costForTurn = adapter.getCostForTurn();
            copy_card.superFlash(Color.GOLD.cpy());
        }
        if (copy_card.baseBlock >= 0) {
            copy_card.baseBlock += adapter.getBlock();
        }
        if (copy_card.magicNumber >= 0) {
            copy_card.baseMagicNumber += adapter.getMagicNumber();
        }
        copy_card.initializeDescription();
        AbstractDungeon.player.hand.addToTop(copy_card);
        AbstractDungeon.player.hand.removeCard(target_card);
        isDone = true;
    }


}
