package HsrMod.cards.skills;

import HsrMod.action.ExhaustTargetTypeAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

/**
 * @author darkbloodevil
 * @date 2024/8/13 20:47
 * @description
 */
public class LoveHealAndChoose extends BaseCard {
    public static final String ID = makeID(LoveHealAndChoose.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public LoveHealAndChoose() {
        super(ID, info);
        this.magicNumber = 1;
        this.baseMagicNumber = magicNumber;
        this.setBlock(6, 3);
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
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        tmp.shuffle(new Random((long) RandomUtil.random_int()));
        int i = 0;
        for (AbstractCard card : p.drawPile.group) {
            if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
                if (i < magicNumber) {
                    i++;
                    addToBot(new ExhaustTargetTypeAction(ExhaustTargetTypeAction.DRAW_PILE, card));

                } else {
                    break;
                }
            }
        }
        for (AbstractCard card : p.discardPile.group) {
            if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
                if (i < magicNumber) {
                    i++;
                    addToBot(new ExhaustTargetTypeAction(ExhaustTargetTypeAction.DISCARD_PILE, card));
                } else {
                    break;
                }
            }
        }
    }
}
