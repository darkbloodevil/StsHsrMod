package HsrMod.cards;

import HsrMod.characters.StsCharacter;
import HsrMod.powers.SlashedDreamPower;
import HsrMod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

/**
 * @author darkbloodevil
 * @date 2024/7/30 14:33
 * @description 我为逝者哀哭
 */
public class SlashedDreamCriesInRed extends BaseCard implements StartupCard {
    public static final String ID = makeID(SlashedDreamCriesInRed.class.getSimpleName());
    private static final CardStats info = new CardStats(
            StsCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int UPG_COST = 1;

    public SlashedDreamCriesInRed() {
        super(ID, info);
        this.baseDamage = 50;
        this.isMultiDamage = true;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.hasPower(SlashedDreamPower.POWER_ID)) {
            if(p.getPower(SlashedDreamPower.POWER_ID).amount>=9){
                this.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
                return true;
            }
            this.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
            return false;
        } else {
            return canUse;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
            this.upgradeDamage(10);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
        } else {
            this.addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new ApplyPowerAction(p,p,new SlashedDreamPower(p,-9),-9));
    }

    @Override
    public boolean atBattleStartPreDraw() {
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlashedDreamPower(AbstractDungeon.player, 0)));
        return false;
    }
}
