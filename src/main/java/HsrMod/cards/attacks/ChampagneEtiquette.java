package HsrMod.cards.attacks;

import HsrMod.action.HsrDamageAction;
import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.FineIwillDoItMyselfPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

/**
 * @author darkbloodevil
 * @date 2024/8/14 17:11
 * @description
 */
public class ChampagneEtiquette  extends BaseAttack{
    public static final String ID = makeID(ChampagneEtiquette.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    public ChampagneEtiquette() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HsrDamageAction(m,new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL,toughness_reduction)));
//        addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL,toughness_reduction), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new FreeAttackPower((AbstractCreature)p, 1), 1));
    }
}
