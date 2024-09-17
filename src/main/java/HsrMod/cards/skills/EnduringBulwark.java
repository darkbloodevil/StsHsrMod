package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;

/**
 * @author darkbloodevil
 * @date 2024/9/3 19:38
 * @description
 */
public class EnduringBulwark  extends BaseCard {
    public static final String ID = makeID(EnduringBulwark.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    int UPG_BLOCK=6;
    public EnduringBulwark() {
        super(ID, info);
        this.exhaust=true;
        this.magicNumber=1;
        this.baseMagicNumber=1;
        this.setBlock(20,UPG_BLOCK);
        this.setMagic(magicNumber,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber), magicNumber));
    }
//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeBlock(UPG_BLOCK);
//            this.upgradeMagicNumber(1);
//            initializeDescription();
//        }
//        super.upgrade();
//    }
}