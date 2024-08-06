package HsrMod.cards.specials;

import HsrMod.cards.BaseCard;
import HsrMod.cards.attacks.HighPoles;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.FireDotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author darkbloodevil
 * @date 2024/8/6 21:15
 * @description
 */
public class FarewellHit extends BaseCard implements ToughnessReductionInterface {
    public static final String ID = makeID(FarewellHit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 8;
    private static final int toughness_reduction = 10;

    String target;

    public FarewellHit() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        target="";
    }

    public FarewellHit(String m_id) {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        target=m_id;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
    public void onChoseThisOption() {
        for (AbstractMonster m:AbstractDungeon.getMonsters().monsters) {
            if (m.id.equals(target)){
                addToBot(new DamageAction(m, new HsrDamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            }
        }

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

}

