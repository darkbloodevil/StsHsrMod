package HsrMod.cards.specials;

import HsrMod.cards.BaseCard;
import HsrMod.cards.attacks.BaseAttack;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;
import HsrMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/1 10:58
 * @description
 */
public class OrderFlarePropulsion extends SAMCard {
    public static final String ID = makeID(OrderFlarePropulsion.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int UPG_Magic = 1;

    public OrderFlarePropulsion() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber=2;
        this.magicNumber=baseMagicNumber;
    }
    @Override
    public void upgrade() {
        if (!upgraded){
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_Magic);
            initializeDescription();
        }
        super.upgrade();
    }


    @Override
    public void on_break_trigger(AbstractPlayer p, AbstractMonster m) {
        super.on_break_trigger(p, m);
        addToBot(new DrawCardAction(magicNumber));
    }
}