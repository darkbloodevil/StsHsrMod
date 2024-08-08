package HsrMod.cards.specials;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.cards.BaseCard;
import HsrMod.cards.attacks.BaseAttack;
import HsrMod.cards.attacks.HighPoles;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.FireDotPower;
import HsrMod.util.CardStats;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

/**
 * @author darkbloodevil
 * @date 2024/8/6 21:18
 * @description
 */
public class RIPHomeRun extends BaseAttack {
    public static final String ID = makeID(RIPHomeRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    public static final int DAMAGE = 10;
    public static final int UPG_DAMAGE = 4;


    public RIPHomeRun() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        toughness_reduction = 6;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public void onChoseThisOption() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new DamageAction(m, new HsrDamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster no_use) {
//        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
//            addToBot(new DamageAction(m, new HsrDamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
//        }
        addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL,toughness_reduction), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }

}

