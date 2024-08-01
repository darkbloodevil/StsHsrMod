package StsMod.cards;

import StsMod.characters.StsCharacter;
import StsMod.powers.PromiseNotCommandPower;
import StsMod.powers.VictoryRushPower;
import StsMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/7/30 18:00
 * @description
 */
public class VictoryRush extends BaseCard {
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

    public VictoryRush() {
        super(ID, info);
        this.magicNumber = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_Magic);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        VictoryRushPower pncp = new VictoryRushPower(p, p, magicNumber);

        addToBot(new ApplyPowerAction(p, p, pncp));
    }
}
