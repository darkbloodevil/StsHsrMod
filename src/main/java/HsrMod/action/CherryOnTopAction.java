package HsrMod.action;

import HsrMod.core.HsrDamageInfo;
import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

/**
 * @author darkbloodevil
 * @date 2024/8/7 15:04
 * @description
 */
public class CherryOnTopAction extends AbstractGameAction {
    boolean is_upgraded;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;

    public CherryOnTopAction(AbstractPlayer p, int damage_amount, boolean freeToPlayOnce, int energyOnUse, boolean is_upgraded) {
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
        if (is_upgraded) effect += 1;

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                int random = RandomUtil.random_int(3);
                if (random == 0) {
                    addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, amount, DamageInfo.DamageType.NORMAL)));
                } else if (random == 1) {
                    addToBot(new DrawCardAction(p,2));
                } else {
                    addToBot(new GainEnergyAction(2));
                    addToBot(new DiscardAction(p, p, 1, true));
                }
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
