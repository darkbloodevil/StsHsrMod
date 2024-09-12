package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.FlyingAureusPower;
import HsrMod.powers.StandoffPower;
import HsrMod.powers.TauntPower;
import HsrMod.powers.TauntedPower;
import HsrMod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

/**
 * @author darkbloodevil
 * @date 2024/9/10 14:40
 * @description 飞霄Q
 */
public class Terrasplit extends BaseCard implements StartupCard {
    public static final String ID = makeID(Terrasplit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Terrasplit() {
        super(ID, info);
        this.setMagic(3,4);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseEnergyAction(1));
    }
    public void triggerWhenDrawn() {
        addToBot(new LoseEnergyAction(1));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.hasPower(FlyingAureusPower.POWER_ID)){
                    AbstractDungeon.player.getPower(FlyingAureusPower.POWER_ID).onSpecificTrigger();
                }
                isDone=true;
            }
        });
        addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
     }

    @Override
    public boolean atBattleStartPreDraw() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FlyingAureusPower(AbstractDungeon.player,magicNumber)));
        return false;
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