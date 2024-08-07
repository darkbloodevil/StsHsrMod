package HsrMod.action;

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
    int damage;
    int cost;
    int block;

    public AdaptCardAction(AbstractCard target_card, int damage,int cost,int block) {
        this.target_card = target_card;
        this.damage = damage;
        this.cost = cost;
        this.block = block;
    }

    @Override
    public void update() {
        AbstractCard copy_card=target_card.makeStatEquivalentCopy();
        copy_card.unhover();
        if (copy_card.cost>0){
            copy_card.baseDamage += damage;
            copy_card.cost = cost;
            copy_card.costForTurn = cost;
            copy_card.baseBlock += block;
            copy_card.superFlash(Color.GOLD.cpy());
        }

        AbstractDungeon.player.hand.addToTop(copy_card);
        AbstractDungeon.player.hand.removeCard(target_card);
        isDone = true;
    }


}
