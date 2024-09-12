package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.cards.specials.FarewellHit;
import HsrMod.cards.specials.RIPHomeRun;
import HsrMod.cards.specials.TheConductorsDraw;
import HsrMod.cards.specials.TheConductorsEnergy;
import HsrMod.characters.Stelle;
import HsrMod.powers.StandoffPower;
import HsrMod.powers.TauntPower;
import HsrMod.powers.TauntedPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

/**
 * @author darkbloodevil
 * @date 2024/9/6 17:09
 * @description The Conductor's Rewards
 * 列车长的馈赠
 */
public class TheConductorsRewards extends BaseCard {
    public static final String ID = makeID(TheConductorsRewards.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public TheConductorsRewards() {
        super(ID, info);
        this.exhaust = true;
    }

    public TheConductorsRewards(int timesUpgraded) {
        super(ID, info);
        this.exhaust = true;
        this.timesUpgraded = timesUpgraded;
        if (timesUpgraded > 0) {
            this.upgraded = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new TheConductorsEnergy());
        stanceChoices.add(new TheConductorsDraw());

        addToBot(new ChooseOneAction(stanceChoices));
        // 塞入一张升级次数-1的卡
        if (this.upgraded)
            addToBot(new MakeTempCardInDrawPileAction(new TheConductorsRewards(this.timesUpgraded - 1), 1, true, true));

    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeTitle();
        this.initializeDescription();
    }
}