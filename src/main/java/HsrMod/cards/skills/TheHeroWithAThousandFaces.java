package HsrMod.cards.skills;

import HsrMod.action.DiscardTypeAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.ProofOfDebtPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/5 16:31
 * @description
 */
public class TheHeroWithAThousandFaces extends BaseCard {
    public static final String ID = makeID(TheHeroWithAThousandFaces.class.getSimpleName());
    private static final int max_trigger=10;
    private static int triggered=0;
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public TheHeroWithAThousandFaces() {
        super(ID, info);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
        super.upgrade();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        triggered++;
        if (triggered>=10)
            return;
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DiscardTypeAction(1, CardType.ATTACK));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (!this.retain) {
            addToBot(new DrawCardAction(AbstractDungeon.player, magicNumber));
        }
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        addToBot(new DrawCardAction(AbstractDungeon.player, magicNumber));
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        super.triggerOnCardPlayed(cardPlayed);
        triggered=0;
    }
}
