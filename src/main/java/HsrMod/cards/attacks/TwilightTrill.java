package HsrMod.cards.attacks;

import HsrMod.action.ApplyDotAction;
import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.LightningDotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/4 17:13
 * @description 卡芙卡q
 */
public class TwilightTrill extends BaseAttack implements ToughnessReductionInterface, DurationInterface {
    public static final String ID = makeID(TwilightTrill.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int UPG_Magic = 3;
    private static final int duration = 2;

    public TwilightTrill() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        this.magicNumber = 7;
        this.baseMagicNumber = 7;
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
        addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyDotAction(monster, p, new LightningDotPower(monster, p, duration, this.magicNumber)));
        }
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

    @Override
    public int get_duration() {
        return duration;
    }
}

