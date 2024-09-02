package HsrMod.cards.skills;

import HsrMod.action.AmidstTheRejoicingCloudsAction;
import HsrMod.action.VoxHarmoniqueOpusCosmiqueAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/7 17:22
 * @description
 */
public class AmidstTheRejoicingClouds extends BaseCard {
    public static final String ID = makeID(AmidstTheRejoicingClouds.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    static int UPG_MAGIC = 3;

    public AmidstTheRejoicingClouds() {
        super(ID, info);
        this.magicNumber = 7;
        this.baseMagicNumber = 5;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AmidstTheRejoicingCloudsAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            initializeDescription();
        }
        super.upgrade();
    }
}