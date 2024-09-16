package HsrMod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    public DivineProvisionPower(AbstractCreature owner, AbstractCreature source, int magic) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, magic);
    }

    @Override
    public void updateDescription() {
        this.description=String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        addToBot( new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));

    }
}