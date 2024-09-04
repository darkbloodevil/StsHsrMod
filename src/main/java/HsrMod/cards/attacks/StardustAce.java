package HsrMod.cards.attacks;

import HsrMod.cards.BaseCard;
import HsrMod.cards.specials.FarewellHit;
import HsrMod.cards.specials.RIPHomeRun;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.MultiDamageInterface;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.powers.FireDotPower;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

/**
 * @author darkbloodevil
 * @date 2024/8/6 15:39
 * @description 毁灭主q
 * Stardust Ace
 * Farewell hit
 * RIP Home Run
 */
public class StardustAce extends BaseAttack implements MultiDamageInterface {
    public static final String ID = makeID(StardustAce.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 4;


    public StardustAce() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new FarewellHit(m.id,damage));
        stanceChoices.add(new RIPHomeRun(damage));

        if (this.upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }
        addToBot((AbstractGameAction) new ChooseOneAction(stanceChoices));
    }

    @Override
    public int[] get_multi_damage() {
        int[] damages = new int[2];
        damages[0] = this.damage*2;
        damages[1] = this.damage;
//        damages[0] = FarewellHit.DAMAGE;
//        damages[1] = RIPHomeRun.DAMAGE;
//        if (upgraded) {
//            damages[0]+=FarewellHit.UPG_DAMAGE;
//            damages[1]+=RIPHomeRun.UPG_DAMAGE;
//        }

        return damages;
    }
}

