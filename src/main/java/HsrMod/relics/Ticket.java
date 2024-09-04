package HsrMod.relics;

import HsrMod.characters.Stelle;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/9/2 18:13
 * @description
 */
public class Ticket extends BaseRelic {
    public static final String ID = makeID(Ticket.class.getSimpleName()); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER;//The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    boolean has_lost=false;

    public Ticket() {
        super(ID, Ticket.class.getSimpleName(), Stelle.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void onLoseRelic(){
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), this);
        flash();
        AbstractDungeon.player.decreaseMaxHealth((int) (AbstractDungeon.player.maxHealth*0.1));
        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
        has_lost=true;
        this.description=getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription() {
        super.getUpdatedDescription();
        if (!has_lost)
            return DESCRIPTIONS[0];
        return DESCRIPTIONS[1];
    }

}

