package HsrMod.cards.attacks;

import HsrMod.cards.BaseCard;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;


/**
 * @author darkbloodevil
 * @date 2024/8/8 15:27
 * @description 主要用于实现韧性条
 */
public abstract class BaseAttack extends BaseCard implements ToughnessReductionInterface {
    public int toughness_reduction = 4;

    public BaseAttack(String ID, CardStats info) {
        super(ID, info);
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

    /**
     * from anonMod
     *
     * @param sb
     * @param xPos
     * @param yPos
     * @param yOffsetBase
     * @param scale
     */
    public void renderCardSign(SpriteBatch sb, float xPos, float yPos, float yOffsetBase, float scale) {
        String msg = "";
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            msg = cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            return;
        }
        if (this.isFlipped || this.isLocked || this.transparency <= 0.0F) {
            return;
        }
        /* 74 */
        float offsetY = yOffsetBase * Settings.scale * scale / 2.0F;
        /* 75 */
        BitmapFont.BitmapFontData fontData = FontHelper.cardTitleFont.getData();
        /* 76 */
        float originalScale = fontData.scaleX;
        /* 77 */
        float scaleMultiplier = 0.8F;
        /*    */
        /* 79 */
        fontData.setScale(scaleMultiplier * scale * 0.85F);
        /* 80 */
        Color color = Settings.CREAM_COLOR.cpy();
        /* 81 */
        color.a = this.transparency;
        /* 82 */
        FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, msg, xPos, yPos, 0.0F, offsetY, this.angle, true, color);
        /*    */
        /* 84 */
        fontData.setScale(originalScale);
        /*    */
        /*    */
    }

    public void renderCardSign(SpriteBatch sb) {
        /* 87 */
        renderCardSign(sb, this.current_x, this.current_y, 400.0F, this.drawScale);
        /*    */
    }

    public void render(SpriteBatch sb) {
        /* 89 */
        super.render(sb);
        /* 90 */
        renderCardSign(sb);
        /*    */
    }

}
