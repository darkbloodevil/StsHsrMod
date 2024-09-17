package HsrMod.relics;

import HsrMod.cards.curses.CurseOfPoverty;
import HsrMod.characters.Stelle;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import java.util.ArrayList;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/11 11:06
 * @description 首充双倍
 */
public class PaymentBonusRelic extends BaseRelic {
    public static final String ID = makeID(PaymentBonusRelic.class.getSimpleName()); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    ArrayList<Integer> triggered_cost_list=new ArrayList<>();


    public PaymentBonusRelic() {
        super(ID, PaymentBonusRelic.class.getSimpleName(), Stelle.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }

    public void atBattleStart() {
        flash();
        triggered_cost_list=new ArrayList<>();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    /**
     * from EchoPower
     * @param card
     * @param action
     */
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (triggered_cost_list.contains(card.costForTurn))
            return;
        if (!card.purgeOnUse) {
            flash();
            triggered_cost_list.add(card.costForTurn);
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
             }
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        triggered_cost_list=new ArrayList<>();
    }

    @Override
    public void onEquip() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CurseOfPoverty curseOfTheBell = new CurseOfPoverty();
        group.addToBottom(curseOfTheBell.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
    }
}
