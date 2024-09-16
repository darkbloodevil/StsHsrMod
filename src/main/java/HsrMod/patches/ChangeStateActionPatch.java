package HsrMod.patches;

import HsrMod.HsrMod;
import HsrMod.powers.StunMonsterPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/9/14 16:02
 * @description 试图解决状态转化的问题
 */
//@SpirePatch(clz = ChangeStateAction.class, method = "update")

public class ChangeStateActionPatch {
//    @SpirePostfixPatch
//    public static void Postfix(ChangeStateAction _inst, AbstractMonster ___m) {
//        if (___m!=null&&___m.hasPower(StunMonsterPower.POWER_ID)){
//            HsrMod.logger.info("change state; remove stun");
//            StunMonsterPower removeMe=(StunMonsterPower)___m.getPower(StunMonsterPower.POWER_ID);
//            removeMe.onRemove();
//            ___m.powers.remove(removeMe);
//            AbstractDungeon.onModifyPower();
////            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(___m,___m,StunMonsterPower.POWER_ID));
//        }
//
//    }
}
