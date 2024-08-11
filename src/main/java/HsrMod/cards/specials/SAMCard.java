package HsrMod.cards.specials;

import HsrMod.cards.attacks.BaseAttack;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.util.CardStats;
import HsrMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/9 14:33
 * @description
 */
public abstract class SAMCard extends BaseAttack {
    int toughness_reduction = 8;



    public SAMCard(String ID, CardStats info) {
        super(ID, info);
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (ToughnessUtil.target_on_break(m)) {
            on_break_trigger(p,m);
        }
    }

    public void on_break_trigger(AbstractPlayer p,AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, toughness_reduction, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}