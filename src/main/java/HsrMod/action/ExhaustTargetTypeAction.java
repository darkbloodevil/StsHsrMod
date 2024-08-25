package HsrMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @author darkbloodevil
 * @date 2024/8/13 21:05
 * @description
 */
public class ExhaustTargetTypeAction extends AbstractGameAction {
    public static String HAND = "hand", DRAW_PILE = "draw pile", DISCARD_PILE = "discard pile";
    String group_name;
    AbstractCard c;

    public ExhaustTargetTypeAction(String group_name, AbstractCard c) {
        this.group_name = group_name;
        this.c = c;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (group_name.equals(HAND)) {
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            } else if (group_name.equals(DRAW_PILE)) {
                AbstractDungeon.player.drawPile.moveToExhaustPile(c);
            } else if (group_name.equals(DISCARD_PILE)) {
                AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            }
            c.current_y = -200.0F * Settings.scale;
            c.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            c.target_y = Settings.HEIGHT / 2.0F;
            c.targetAngle = 0.0F;
            c.lighten(false);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;

            if (!Settings.FAST_MODE) {
                addToTop((AbstractGameAction) new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop((AbstractGameAction) new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }
        isDone = true;
    }
}
