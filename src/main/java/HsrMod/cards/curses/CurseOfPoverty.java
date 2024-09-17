package HsrMod.cards.curses;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/9/17 1:20
 * @description
 */
public class CurseOfPoverty extends BaseCard{
    public static final String ID = makeID(CurseOfPoverty.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.CURSE, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public CurseOfPoverty() {
        super(ID, info);
        magicNumber=1;
        this.baseMagicNumber=magicNumber;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.player.loseGold(1);
                CurseOfPoverty.this.flash();
                isDone=true;
            }
        });
    }
}

