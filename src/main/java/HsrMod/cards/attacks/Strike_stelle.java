package HsrMod.cards.attacks;

import HsrMod.action.HsrDamageAction;
import HsrMod.cards.specials.FarewellHit;
import HsrMod.cards.specials.RIPHomeRun;
import HsrMod.characters.Stelle;
import HsrMod.interfaces.MultiDamageInterface;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

/**
 * @author darkbloodevil
 * @date 2024/9/12 10:46
 * @description
 */
public class Strike_stelle extends BaseAttack{
    public static final String ID = makeID(Strike_stelle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;


    public Strike_stelle() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new HsrDamageAction(m,new DamageInfo(p,damage)));
    }

}

