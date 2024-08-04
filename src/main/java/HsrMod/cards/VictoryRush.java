package HsrMod.cards;

import HsrMod.characters.StsCharacter;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.VictoryRushPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/7/30 18:00
 * @description
 */
public class VictoryRush extends BaseCard implements ToughnessReductionInterface {
    public static final String ID = makeID(VictoryRush.class.getSimpleName());
    private static final CardStats info = new CardStats(
            StsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 10;
    private static final int UPG_Magic = 5;
    private static final int REDUCTION = 2;

    public VictoryRush() {
        super(ID, info);
        this.magicNumber = DAMAGE;
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        VictoryRushPower pncp = new VictoryRushPower(p, p, magicNumber,REDUCTION);

        addToBot(new ApplyPowerAction(p, p, pncp));
    }

    @Override
    public int get_toughness_reduction() {
        return REDUCTION;
    }
}
