package HsrMod.cards.powers;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.OvertonePower;
import HsrMod.powers.TheShieldPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/7/25 21:30
 * @description
 */
public class StringSingsSlowSwirls extends BaseCard {
    public static final String ID = makeID(StringSingsSlowSwirls.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    public StringSingsSlowSwirls() {
        super(ID, info);
        this.isInnate=false;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            upgradeName();
            this.isInnate=true;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new ApplyPowerAction(abstractPlayer, abstractPlayer, new OvertonePower(abstractPlayer, abstractPlayer, 1)));
    }

    /**
     * @author darkbloodevil
     * @date 2024/8/13 15:33
     * @description
     */
    public static class TheShield extends BaseCard {
        public static final String ID = makeID(TheShield.class.getSimpleName());
        private static final CardStats info = new CardStats(
                Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
                CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
                CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
                CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
                1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
        );

        public TheShield() {
            super(ID, info);
            this.baseMagicNumber=12;
            this.magicNumber=baseMagicNumber;
            this.setBlock(magicNumber,4);
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new ApplyPowerAction(p,p,new TheShieldPower(p,p,magicNumber)));
        }
    }
}
