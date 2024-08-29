package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.powers.DivineProvisionPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/12 17:22
 * @description Divine Provision
 */
public class TailsmanProtection extends BaseCard {
    public static final String ID = makeID(TailsmanProtection.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public TailsmanProtection() {
        super(ID, info);
        this.tags.add(AbstractCard.CardTags.HEALING);
        this.exhaust = true;
        this.magicNumber = 1;
        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DivineProvisionPower(p, p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            initializeDescription();
        }
        super.upgrade();
    }
}