package HsrMod.powers;

import HsrMod.util.RandomUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/12 17:29
 * @description
 */
public class DivineProvisionPower extends BasePower {
    public static final String POWER_ID = makeID(DivineProvisionPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    int magic = 0;

    public DivineProvisionPower(AbstractCreature owner, AbstractCreature source, int magic) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, 0);
        this.magic = magic;
    }

    @Override
    public void updateDescription() {
        this.description=String.format(DESCRIPTIONS[0],magic);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        addToBot( new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magic));

    }
}