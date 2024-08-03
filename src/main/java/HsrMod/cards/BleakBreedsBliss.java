package HsrMod.cards;

import HsrMod.characters.StsCharacter;
import HsrMod.powers.BackupDancerPower;
import HsrMod.powers.BleakBreedsBlissPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/3 14:33
 * @description
 */
public class BleakBreedsBliss extends BaseCard {
    public static final String ID = makeID(BleakBreedsBliss.class.getSimpleName());
    private static final CardStats info = new CardStats(
            StsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    static int UPG_Magic=1;
    public BleakBreedsBliss() {
        super(ID, info);
        this.magicNumber=1;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_Magic);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BleakBreedsBlissPower(abstractPlayer, abstractPlayer, magicNumber)));
    }
}