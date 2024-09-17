package HsrMod.cards.attacks;

import HsrMod.action.HsrDamageAllEnemiesAction;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.powers.LightningLordPower;
import HsrMod.util.CardStats;
import HsrMod.util.DamageUtil;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

/**
 * @author darkbloodevil
 * @date 2024/8/14 17:29
 * @description
 */
public class MeteorStorm extends BaseAttack {
    public static final String ID = makeID(MeteorStorm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Stelle.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public MeteorStorm() {
        super(ID, info);
        setDamage(1);
        this.setMagic(5);
        toughness_reduction = 2;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeDamage(1);
            this.initializeDescription();
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        for (int i = 0; i < magicNumber; i++) {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(true);
            if (!monsters.contains(monster)) {
                monsters.add(monster);
            }
            // 连续攻击 所以是top
            this.addToTop(new DamageAction(monster,
                    new HsrDamageInfo(p, damage, DamageInfo.DamageType.NORMAL, toughness_reduction)));
        }

        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, monsters.size())));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, monsters.size())));
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}
