package HsrMod.cards.skills;

import HsrMod.action.ApplyDotAction;
import HsrMod.action.DanceDanceDanceAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.DotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author darkbloodevil
 * @date 2024/9/9 21:26
 * @description 希露瓦大招
 */
public class HereComesTheMechanicalFever extends BaseCard {
    public static final String ID = makeID(HereComesTheMechanicalFever.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public HereComesTheMechanicalFever() {
        super(ID, info);
        this.magicNumber = 2;
        this.baseMagicNumber = magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster no_use) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower power : m.powers) {
                if (power instanceof DotPower) {
                    DotPower dot = (DotPower) power;
                    if (upgraded) dot.stackDamage(magicNumber);


                    addToBot(new ApplyDotAction(m, p, dot,2));
                }
            }
        }
    }
}