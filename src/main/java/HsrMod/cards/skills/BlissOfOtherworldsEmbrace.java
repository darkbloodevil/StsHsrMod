package HsrMod.cards.skills;

import HsrMod.action.BlissOfOtherworldsEmbraceAction;
import HsrMod.action.CherryOnTopAction;
import HsrMod.cards.BaseCard;
import HsrMod.cards.attacks.DecadenceFalseTwilight;
import HsrMod.characters.Stelle;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/25 9:21
 * @description
 */
public class BlissOfOtherworldsEmbrace extends BaseCard {
    public static final String ID = makeID(BlissOfOtherworldsEmbrace.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BlissOfOtherworldsEmbrace() {
        super(ID, info);
        this.cardsToPreview = new LoomOfFatesCaprice();
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BlissOfOtherworldsEmbraceAction(p, this.damage, this.freeToPlayOnce, this.energyOnUse, this.upgraded));

    }
}
