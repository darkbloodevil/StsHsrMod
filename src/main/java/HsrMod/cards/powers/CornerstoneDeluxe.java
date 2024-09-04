package HsrMod.cards.powers;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.CornerstoneDeluxePower;
import HsrMod.powers.FangOfFlareFlayingPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/9/3 19:42
 * @description
 */
public class CornerstoneDeluxe extends BaseCard {
    public static final String ID = makeID(CornerstoneDeluxe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    public CornerstoneDeluxe() {
        super(ID, info);
//        this.magicNumber = 1;
//        this.baseMagicNumber = this.magicNumber;
        this.setMagic(1);
        this.setBlock(6);
    }

//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeMagicNumber(1);
//            initializeDescription();
//        }
//        super.upgrade();
//    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new GainBlockAction(p, block));
        }
        addToBot(new ApplyPowerAction(p, p, new CornerstoneDeluxePower(p, magicNumber)));
    }
}