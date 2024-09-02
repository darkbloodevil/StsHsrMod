package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.PromiseNotCommandPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/7/30 16:51
 * @description
 */
public class PromiseNotCommand extends BaseCard implements DurationInterface, ToughnessReductionInterface {
    public static final String ID = makeID(PromiseNotCommand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 5;
    private static final int UPG_Magic = 3;
    private static final int TURNS = 3;

    public PromiseNotCommand() {
        super(ID, info);
        this.setMagic(DAMAGE);
        this.baseMagicNumber=DAMAGE;
        this.magicNumber = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_Magic);
            initializeDescription();
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        PromiseNotCommandPower pncp = new PromiseNotCommandPower(p, p, TURNS, magicNumber);

        addToBot(new ApplyPowerAction(p, p, pncp));
    }

    @Override
    public int get_duration() {
        return TURNS;
    }

    @Override
    public int get_toughness_reduction() {
        return PromiseNotCommandPower.toughness_reduction;
    }
}
