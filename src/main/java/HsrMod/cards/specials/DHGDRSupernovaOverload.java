package HsrMod.cards.specials;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.util.CardStats;
import HsrMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

/**
 * @author darkbloodevil
 * @date 2024/8/29 10:59
 * @description
 */
public class DHGDRSupernovaOverload extends SAMCard {
    public static final String ID = makeID(DHGDRSupernovaOverload.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public DHGDRSupernovaOverload() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster no_use) {
        addToBot(new HsrDamageAllEnemiesAction(new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
        addToBot(new DrawCardAction(1));
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (ToughnessUtil.target_on_break(m)) {
                on_break_trigger(p, m);
            }
        }
    }
}