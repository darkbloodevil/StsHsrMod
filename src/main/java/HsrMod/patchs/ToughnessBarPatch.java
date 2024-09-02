package HsrMod.patchs;

/**
 * @author darkbloodevil
 * @date 2024/8/23 11:48
 * @description
 */
/*    */

import HsrMod.HsrMod;
import HsrMod.cards.attacks.BaseAttack;
import HsrMod.powers.ToughnessPower;
import HsrMod.util.ToughnessUtil;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

@SpirePatch(clz = AbstractCreature.class, method = "renderHealth", paramtypez = {SpriteBatch.class})
public class ToughnessBarPatch {
    private static float drawScale = 2.0F;
    private static float yOffsetBase = 690.0F;
    static float HEALTH_BAR_OFFSET_Y = -28F * Settings.scale;//-28.0F * Settings.scale;
    static float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
    static float HEALTH_BG_OFFSET_X = 31.0F * Settings.scale;
    static Color blockOutlineColor = new Color(0.8F, 0.8F, 0.8F, 0.8F);
    static float HEALTH_TEXT_OFFSET_Y = 6.0F * Settings.scale;
    static float HB_Y_OFFSET_DIST = 12.0F * Settings.scale;
    static float hbYOffset = HB_Y_OFFSET_DIST * 5.0F;
    @SpirePostfixPatch
    public static void Postfix(AbstractCreature _inst, SpriteBatch sb) {
        if (_inst instanceof AbstractMonster && _inst.hasPower(ToughnessPower.POWER_ID)) {
            ToughnessPower tp = (ToughnessPower) _inst.getPower(ToughnessPower.POWER_ID);
            float x = _inst.hb.cX - _inst.hb.width / 2.0F;
            float y = _inst.hb.cY - _inst.hb.height / 2.0F + hbYOffset;
            renderToughnessBar(sb, (AbstractMonster) _inst, x, y, 1.0F * tp.amount / ToughnessUtil.get_toughness((AbstractMonster) _inst));
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont,
                    tp.amount + "/" + ToughnessUtil.get_toughness((AbstractMonster) _inst),
                    _inst.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * Settings.scale,
                    new Color(0.8F, 0.8F, 0.8F, 1.0F));

        }

    }

    private static void renderToughnessBar(SpriteBatch sb, AbstractMonster m, float x, float y, float ratio) {
        try {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
            sb.draw(ImageMaster.HB_SHADOW_L, x - HEALTH_BAR_HEIGHT, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HB_SHADOW_B, x, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, m.hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HB_SHADOW_R, x + m.hb.width, y - HEALTH_BG_OFFSET_X + 3.0F * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);

            sb.setColor(blockOutlineColor);
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, ratio * m.hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + ratio * m.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        } catch (Exception exception) {
            HsrMod.logger.error(exception.getMessage());
        }

    }
}

