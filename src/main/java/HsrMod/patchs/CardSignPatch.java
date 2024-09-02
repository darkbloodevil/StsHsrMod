package HsrMod.patchs;


import HsrMod.cards.attacks.BaseAttack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

/**
 * from anonMod
 */

public class CardSignPatch{
    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
    public static class SingleCardViewPatch{
        private static float drawScale = 2.0F;
        private static float yOffsetBase = -90.0F;

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup _inst, SpriteBatch sb, AbstractCard ___card, float ___current_y) {
            if (___card instanceof BaseAttack) {
                BaseAttack card = (BaseAttack) ___card;
                card.renderCardSign(sb, Settings.WIDTH / 2.0F, ___current_y-5F, yOffsetBase, drawScale);
            }
        }
    }
}