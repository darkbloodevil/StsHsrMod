package HsrMod.cards.attacks;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.DotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * @author darkbloodevil
 * @date 2024/8/7 17:15
 * @description
 */
public class GalvnicChords extends BaseAttack implements ToughnessReductionInterface {
    public static final String ID = makeID(GalvnicChords.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;


    public GalvnicChords() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        int toughness_reduction = 4;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.NONE));
        for (AbstractPower power : m.powers) {
            if (power instanceof DotPower) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                        addToTop(new GainEnergyAction(1));
                        isDone = true;
                    }
                });
            }
        }
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

}
