package HsrMod.cards.skills;

import HsrMod.action.DanceDanceDanceAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.DotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author darkbloodevil
 * @date 2024/8/25 9:16
 * @description
 */
public class LoomOfFatesCaprice extends BaseCard {
    public static final String ID = makeID(LoomOfFatesCaprice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public LoomOfFatesCaprice() {
        super(ID, info);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dots=0;
        for (AbstractPower power:m.powers){
            if (power instanceof DotPower){
                dots++;
            }
        }
        if (dots>0){
            addToBot(new GainEnergyAction(dots));
            addToBot(new DrawCardAction(dots));
        }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
        super.upgrade();
    }
}