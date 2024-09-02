package HsrMod.cards.skills;

import HsrMod.action.FireFlyTypeIVCompleteCombustionAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/7/31 17:41
 * @description
 */
public class FyreflyTypeIVCompleteCombustion extends BaseCard {
    public static final String ID = makeID(FyreflyTypeIVCompleteCombustion.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public FyreflyTypeIVCompleteCombustion() {
        super(ID, info);
        this.exhaust=true;
        this.retain=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FireFlyTypeIVCompleteCombustionAction(this.upgraded));
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            this.upgradesDescription=true;
            this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
        super.upgrade();
    }

}
