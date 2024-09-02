package HsrMod.cards.attacks;

import HsrMod.action.CherryOnTopAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/7 15:02
 * @description 青雀强化A
 */
public class CherryOnTop extends BaseAttack implements ToughnessReductionInterface {
    public static final String ID = makeID(CherryOnTop.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    int toughness_reduction=4;

    public CherryOnTop() {
        super(ID, info);
        setDamage(DAMAGE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CherryOnTopAction(p, this.damage, this.freeToPlayOnce, this.energyOnUse, this.upgraded));

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

}

