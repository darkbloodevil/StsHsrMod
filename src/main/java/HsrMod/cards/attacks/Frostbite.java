package HsrMod.cards.attacks;

import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;
import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Random;
/**
 * @author darkbloodevil
 * @date 2024/8/12 17:39
 * @description
 */
public class Frostbite extends BaseAttack {
    public static final String ID = makeID(Frostbite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Frostbite() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        List<String> targets = Arrays.asList(StrengthPower.POWER_ID, ArtifactPower.POWER_ID, PlatedArmorPower.POWER_ID, ThornsPower.POWER_ID);
        Collections.shuffle(targets,RandomUtil.get_seed());

        for (String s : targets) {
            if (m.hasPower(s)) {
                AbstractPower power = m.getPower(s);
                if (power.amount > 0) {
                    addToBot(new RemoveSpecificPowerAction(m, p, power));
                    break;
                }
            }
        }

    }
}
