package HsrMod.relics;

import HsrMod.action.DepleteToughnessAction;
import HsrMod.characters.Stelle;
import HsrMod.core.HsrDamageInfo;
import HsrMod.powers.*;
import HsrMod.util.ToughnessUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static HsrMod.HsrMod.makeID;

public class HonkaiStarRailRelic extends BaseRelic {

    public static final String ID = makeID(HonkaiStarRailRelic.class.getSimpleName()); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    public HonkaiStarRailRelic() {
        super(ID, HonkaiStarRailRelic.class.getSimpleName(), Stelle.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], makeID("Toughness"));
    }

    @Override
    public void atBattleStart() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            onSpawnMonster(monster);
        }
    }

    @Override
    public void onSpawnMonster(AbstractMonster no_use) {
        super.onSpawnMonster(no_use);
        for (AbstractMonster monster:AbstractDungeon.getMonsters().monsters) {
            // 如果没有韧性条 那就加入韧性条
            if (!monster.hasPower(ToughnessPower.POWER_ID)){
                int toughness = ToughnessUtil.get_toughness(monster);
                addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new ToughnessPower(monster, AbstractDungeon.player, toughness),
                        toughness));
            }
        }

    }

    /**
     * 计算削韧值
     *
     * @param info
     * @return
     */
    private int get_toughness_reduction(DamageInfo info) {
        if (info instanceof HsrDamageInfo) {
            HsrDamageInfo h_info = (HsrDamageInfo) info;
            // 有弦外音就x1.5
            if (AbstractDungeon.player.hasPower(OvertonePower.POWER_ID)) {
                return (int) (h_info.toughness_reduction * 1.5);
            } else {
                return h_info.toughness_reduction;
            }
        }
        return 4;
    }
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        flash();
        if (info.type != DamageInfo.DamageType.NORMAL) {
            isDone = true;
            return;
        }
        if (target instanceof AbstractMonster) {
            int reduction_amount = get_toughness_reduction(info);


            if (target.hasPower(BreakPower.POWER_ID) || target.hasPower(ToughnessProtectPower.POWER_ID)) {
                // 留给超击破
                if (target.hasPower(BreakPower.POWER_ID) && AbstractDungeon.player.hasPower(BackupDancerPower.POWER_ID)) {

                    addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, reduction_amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                }

            } else {
                // 削韧
                addToTop(new DepleteToughnessAction((AbstractMonster) target, AbstractDungeon.player, reduction_amount));

            }
        }
        isDone = true;
    }
}
