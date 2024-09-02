package HsrMod.cards.powers;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.DreamdiverPower;
import HsrMod.powers.VictoryRushPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/9 11:46
 * @description
 */
public class Dreamdiver extends BaseCard {
    public static final String ID = makeID(Dreamdiver.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    public Dreamdiver() {
        super(ID, info);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
        super.upgrade();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DreamdiverPower(p)));
    }

}
