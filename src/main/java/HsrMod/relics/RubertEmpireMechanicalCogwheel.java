package HsrMod.relics;

import HsrMod.characters.Stelle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static HsrMod.HsrMod.makeID;

public class RubertEmpireMechanicalCogwheel extends BaseRelic {
    public static final String ID = makeID(RubertEmpireMechanicalCogwheel.class.getSimpleName()); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final int GAIN_GOLD = 25; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.
    private static final int MAX_GOLD = 400; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.


    public RubertEmpireMechanicalCogwheel() {
        super(ID, RubertEmpireMechanicalCogwheel.class.getSimpleName(), Stelle.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], GAIN_GOLD,MAX_GOLD);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (!this.usedUp) {
            flash();
            AbstractDungeon.player.gainGold(GAIN_GOLD);
        }
    }

    @Override
    public void onGainGold() {
        if (!this.usedUp) {
            flash();
            if(AbstractDungeon.player.gold>MAX_GOLD){
                usedUp();
                this.counter = -2;
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
            }
        }
    }
}
