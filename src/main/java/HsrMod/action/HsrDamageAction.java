package HsrMod.action;

import HsrMod.HsrMod;
import HsrMod.core.HsrDamageInfo;
import HsrMod.util.DamageUtil;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

/**
 * @author darkbloodevil
 * @date 2024/9/2 10:57
 * @description
 */
public class HsrDamageAction extends DamageAction {
    private HsrDamageInfo info;
    private boolean skipWait = false, muteSfx = false;

    public HsrDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        super(target, info, effect);
        this.info=HsrDamageInfo.to_hsr_info(info);
        setValues(target, info);
    }

    public HsrDamageAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AbstractGameAction.AttackEffect.NONE);
    }

    public HsrDamageAction(AbstractCreature target, DamageInfo info, boolean superFast) {
        this(target, info, AbstractGameAction.AttackEffect.NONE);
        this.skipWait = superFast;
    }

    public HsrDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect, boolean superFast) {
        this(target, info, effect);
        this.skipWait = superFast;
    }

    public HsrDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect, boolean superFast, boolean muteSfx) {
        this(target, info, effect, superFast);
        this.muteSfx = muteSfx;
    }

    public void update() {
        if (shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.1F) {
            if (this.info.type != DamageInfo.DamageType.THORNS && (
                    this.info.owner.isDying || this.info.owner.halfDead)) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
        }
        tickDuration();
        isDone=true;
        if (this.attackEffect == AttackEffect.POISON) {
            this.target.tint.color.set(Color.CHARTREUSE.cpy());
            this.target.tint.changeColor(Color.WHITE.cpy());
        } else if (this.attackEffect == AttackEffect.FIRE) {
            this.target.tint.color.set(Color.RED);
            this.target.tint.changeColor(Color.WHITE.cpy());
        }
        // 处理追加攻击的情况
        DamageUtil.alter_followUp(info, target);

        this.target.damage(new HsrDamageInfo(this.info.damageSource,this.info.base,this.info.type,info.toughness_reduction,info.is_follow_up));

        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        if (!this.skipWait && !Settings.FAST_MODE) {
            addToTop((AbstractGameAction) new WaitAction(0.1F));
        }
    }
}
