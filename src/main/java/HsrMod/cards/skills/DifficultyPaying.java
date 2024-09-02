package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.ProofOfDebtPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/5 11:16
 * @description
 */
public class DifficultyPaying extends BaseCard {
    public static final String ID = makeID(DifficultyPaying.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DifficultyPaying() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new ProofOfDebtPower(m,p)));
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
}
