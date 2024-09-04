package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import HsrMod.util.DamageUtil;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

/**
 * @author darkbloodevil
 * @date 2024/8/1 17:58
 * @description
 */
public class HsrDamageAllEnemiesAction extends AbstractGameAction {
    HsrDamageInfo info;
    public int[] damage;
    private boolean firstFrame = true, utilizeBaseDamage = false;
    AbstractGameAction.AttackEffect effect;

    public HsrDamageAllEnemiesAction(HsrDamageInfo info) {
        this(info, AttackEffect.NONE);
    }

    public HsrDamageAllEnemiesAction(HsrDamageInfo info, AbstractGameAction.AttackEffect effect) {
        this(null, effect, false);
        this.utilizeBaseDamage = false;
        this.info = info;
    }

    public HsrDamageAllEnemiesAction(int[] amount, AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.effect = effect;
        this.attackEffect = effect;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
    }

    @Override
    public void update() {
//        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//            addToBot(new HsrDamageAction(monster, info, effect));
//        }
//        isDone = true;
        if (this.firstFrame) {
            boolean playedMusic = false;
            int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.info.base);
            }
            if (this.damage == null) {
                this.damage = DamageInfo.createDamageMatrix(this.info.base, true);
            }

            for (int i = 0; i < temp; i++) {
                if (!((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                        ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                        !((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).hb.cX, ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(
                                ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).hb.cX,
                                ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).hb.cY, this.attackEffect));

                    }
                }
            }
            this.firstFrame = false;
        }
        tickDuration();
        isDone = true;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            p.onDamageAllEnemies(this.damage);
        }
        for (int i = 0; i < (AbstractDungeon.getCurrRoom()).monsters.monsters.size(); i++) {
            if (!((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDeadOrEscaped()) {

                if (this.attackEffect == AttackEffect.POISON) {
                    ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).tint.color.set(Color.CHARTREUSE);
                    ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                } else if (this.attackEffect == AttackEffect.FIRE) {
                    ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).tint.color.set(Color.RED);
                    ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                }
                HsrDamageInfo single_info = DamageUtil.alter_followUp(new HsrDamageInfo(info.damageSource, this.damage[i], info.type, info.toughness_reduction, info.is_follow_up, true), AbstractDungeon.getCurrRoom().monsters.monsters.get(i));
                ((AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).damage(single_info);
            }
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        if (!Settings.FAST_MODE)
            addToTop((AbstractGameAction) new WaitAction(0.1F));
    }
}
