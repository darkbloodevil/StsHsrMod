package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import HsrMod.powers.BlissOfOtherworldsEmbracePower;
import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

/**
 * @author darkbloodevil
 * @date 2024/8/25 9:23
 * @description
 */
public class BlissOfOtherworldsEmbraceAction extends AbstractGameAction {
    boolean is_upgraded;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;

    public BlissOfOtherworldsEmbraceAction(AbstractPlayer p, int damage_amount, boolean freeToPlayOnce, int energyOnUse, boolean is_upgraded) {
        this.is_upgraded = is_upgraded;
        this.amount = damage_amount;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            addToBot(new ApplyPowerAction(p, p, new BlissOfOtherworldsEmbracePower(p, p, effect, is_upgraded)));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
