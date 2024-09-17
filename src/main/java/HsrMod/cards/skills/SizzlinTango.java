package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.BurdenPower;
import HsrMod.powers.StandoffPower;
import HsrMod.powers.TauntPower;
import HsrMod.powers.TauntedPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

/**
 * @author darkbloodevil
 * @date 2024/9/6 17:13
 * @description sizzlin' tango 波提欧e
 * @TODO 暂时ban掉 重做
 */
public class SizzlinTango extends BaseCard {
    public static final String ID = makeID(SizzlinTango.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public SizzlinTango() {
        super(ID, info);
        this.magicNumber = 3;
        this.baseMagicNumber = 3;
        this.setCostUpgrade(2);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new TauntedPower(p,p,1)));
        addToBot(new ApplyPowerAction(m,p,new TauntPower(m,p,1)));
        addToBot(new ApplyPowerAction(p,p,new StandoffPower(p,p,magicNumber)));
        addToBot(new ApplyPowerAction(m,p,new StandoffPower(m,p,magicNumber)));
        addToBot(new ApplyPowerAction(p, (AbstractCreature)p, (AbstractPower)new FreeAttackPower((AbstractCreature)p, 1), 1));

    }

//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeMagicNumber(UPG_Magic);
//            initializeDescription();
//        }
//        super.upgrade();
//    }
}