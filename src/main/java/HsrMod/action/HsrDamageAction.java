package HsrMod.action;

import HsrMod.HsrMod;
import HsrMod.core.HsrDamageInfo;
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
        if (info instanceof HsrDamageInfo) {
            HsrMod.logger.info("is hsr info");
            this.info = (HsrDamageInfo) info;
        } else {
            this.info = new HsrDamageInfo(info);
        }
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
        if (this.isDone) {
            if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) {
                this.target.tint.color.set(Color.CHARTREUSE.cpy());
                this.target.tint.changeColor(Color.WHITE.cpy());
            } else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
                this.target.tint.color.set(Color.RED);
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            HsrMod.logger.info("====HsrDamageAction===info.base"+info.base);
            HsrMod.logger.info("====is_follow_up==="+info.is_follow_up);
            // 追加攻击手动实现易伤和虚弱
            if (info.is_follow_up){
                if (target.hasPower(VulnerablePower.POWER_ID)){
                    info.base=(int) target.getPower(VulnerablePower.POWER_ID).atDamageReceive(info.base,info.type);
                }
                if (AbstractDungeon.player.hasPower(WeakPower.POWER_ID)){
                    info.base=(int) AbstractDungeon.player.getPower(WeakPower.POWER_ID).atDamageGive(info.base,info.type);
                }
                HsrMod.logger.info("====altered==="+info.base);
            }

            this.target.damage(new DamageInfo(this.info.damageSource,this.info.base,this.info.type));

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            if (!this.skipWait && !Settings.FAST_MODE) {
                addToTop((AbstractGameAction) new WaitAction(0.1F));
            }
        }
    }
}
