package HsrMod.cards.attacks;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.powers.LightningLordPower;
import HsrMod.powers.SlashedDreamPower;
import HsrMod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

/**
 * @author darkbloodevil
 * @date 2024/8/8 9:15
 * @description 景元元
 * lightning-Lord
 */
public class PranaExtirpated extends BaseAttack implements StartupCard {
    public static final String ID = makeID(PranaExtirpated.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public PranaExtirpated() {
        super(ID, info);
        setDamage(5, 3);
        this.setMagic(2);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (AbstractDungeon.player.hasPower(LightningLordPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningLordPower(AbstractDungeon.player, magicNumber)));
        }
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (!AbstractDungeon.player.hasPower(LightningLordPower.POWER_ID)) {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningLordPower(AbstractDungeon.player,3),0));
        }
        return false;
    }
}
