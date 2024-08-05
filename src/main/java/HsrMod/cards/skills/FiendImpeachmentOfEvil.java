package HsrMod.cards.skills;

import HsrMod.cards.BaseCard;
import HsrMod.characters.Stelle;
import HsrMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

/**
 * @author darkbloodevil
 * @date 2024/8/4 17:46
 * @description 霍霍秘技
 */
public class FiendImpeachmentOfEvil extends BaseCard {
    public static final String ID = makeID(FiendImpeachmentOfEvil.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public FiendImpeachmentOfEvil() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster target:AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
            if (!target.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
            }
            addToBot(new ApplyPowerAction(target,  AbstractDungeon.player, new WeakPower(target, 1, false), 1));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
