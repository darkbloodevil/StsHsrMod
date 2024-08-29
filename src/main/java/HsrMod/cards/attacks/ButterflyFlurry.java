package HsrMod.cards.attacks;

import HsrMod.action.ButterflyFlurryAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/8 16:33
 * @description 希儿
 * @TODO 加入多次升级
 */
public class ButterflyFlurry extends BaseAttack {
    public static final String ID = makeID(ButterflyFlurry.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 12;
    private static final float UPG_DAMAGE_RATIO = 0.4f;


    public ButterflyFlurry() {
        super(ID, info);
        setDamage(DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        this.baseMagicNumber = 5;
        this.toughness_reduction = 6;
        this.magicNumber = baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        this.upgraded = true;
        this.upgradeMagicNumber(1);
        this.upgradeDamage((int) (UPG_DAMAGE_RATIO * this.baseDamage));
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
        this.initializeDescription();

    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ButterflyFlurryAction(m, this.makeStatEquivalentCopy(), new HsrDamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL, toughness_reduction), magicNumber));
    }
}
