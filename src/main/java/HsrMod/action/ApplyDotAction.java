package HsrMod.action;

import HsrMod.powers.DotPower;
import HsrMod.util.PlayerPropertyUtil;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;

import java.util.Collections;

/**
 * @author darkbloodevil
 * @date 2024/7/23 16:07
 * @description 类似*ApplyPowerAction*, 但是只有同伤害的同类dot才是叠加amount（持续时间）
 */
public class ApplyDotAction extends AbstractGameAction {
    //    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ApplyDotAction.class.getSimpleName());
//    public static final String[] TEXT = uiStrings.TEXT;
    public static final String[] TEXT = new String[]{"ApplyDotAction", "ApplyDotAction"};
    private DotPower powerToApply;
    private float startingDuration;

    public ApplyDotAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else if (isFast) {
            this.startingDuration = Settings.ACTION_DUR_FASTER;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.setValues(target, source, stackAmount);
        this.duration = this.startingDuration;
        this.powerToApply = (DotPower) powerToApply;

        // dot伤害受到攻击力修正
        this.powerToApply.stackDamage(PlayerPropertyUtil.get_strength());

        this.actionType = ActionType.POWER;
        this.attackEffect = effect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
        }

    }

    public ApplyDotAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast) {
        this(target, source, powerToApply, stackAmount, isFast, AttackEffect.NONE);
    }

    public ApplyDotAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply) {
        this(target, source, powerToApply, powerToApply.amount);
    }

    public ApplyDotAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(target, source, powerToApply, stackAmount, false, AttackEffect.NONE);
    }

    public ApplyDotAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, AbstractGameAction.AttackEffect effect) {
        this(target, source, powerToApply, stackAmount, false, effect);
    }

    public void update() {
        if (this.target == null || this.target.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        if (this.duration == this.startingDuration) {
            if (this.source != null) {
                for (AbstractPower pow : this.source.powers) {
                    pow.onApplyPower(this.powerToApply, this.target, this.source);
                }
            }

            if (this.target instanceof com.megacrit.cardcrawl.monsters.AbstractMonster && this.target.isDeadOrEscaped()) {
                this.duration = 0.0F;
                this.isDone = true;
                return;
            }

            if (this.target.hasPower("Artifact") && this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                addToTop((AbstractGameAction) new TextAboveCreatureAction(this.target, TEXT[0]));
                this.duration -= Gdx.graphics.getDeltaTime();
                CardCrawlGame.sound.play("NULLIFY_SFX");
                this.target.getPower("Artifact").flashWithoutSound();
                this.target.getPower("Artifact").onSpecificTrigger();
                tickDuration();
                this.isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            boolean hasBuffAlready = false;
            for (AbstractPower p : this.target.powers) {
                if (p.ID.equals(this.powerToApply.ID)) {
                    DotPower old_dot = (DotPower) p;
                    DotPower new_dot = (DotPower) powerToApply;
                    if (old_dot.damage_amount == new_dot.damage_amount) {
                        p.stackPower(this.amount);
                        p.flash();
                        if (this.amount > 0) {

                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" +
                                    Integer.toString(this.amount) + " " + this.powerToApply.name));

                        }
                        p.updateDescription();
                        hasBuffAlready = true;
                        AbstractDungeon.onModifyPower();
                    }

                }
            }

            this.target.useFastShakeAnimation(0.5F);

            if (!hasBuffAlready) {
                this.target.powers.add(this.powerToApply);
                Collections.sort(this.target.powers);
                this.powerToApply.onInitialApplication();
                this.powerToApply.flash();

                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name));
                AbstractDungeon.onModifyPower();
            }
            this.isDone = true;
            tickDuration();
        }
    }
}
