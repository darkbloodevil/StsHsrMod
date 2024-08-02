package HsrMod.monsters;

import HsrMod.ModHelper;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.RitualPower;

public class StsMonster extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    private static final String ID = ModHelper.makePath("MyMonster");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    public static final String IMG = "";

    public StsMonster(float x, float y) {
        // 参数的作用分别是：
        // 名字
        // ID
        // 最大生命值，由于在之后还会设置可以随意填写
        // hitbox偏移量 - x方向
        // hitbox偏移量 - y方向
        // hitbox大小 - x方向（会影响血条宽度）
        // hitbox大小 - y方向
        // 图片
        // 怪物位置（x,y）
        super(NAME, ID, 140, 0.0F, 0.0F, 250.0F, 270.0F, IMG, x, y);

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(155, 166);
        } else {
            this.setHp(150, 160);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 2) {
            slashDmg = 20;
            multiDmg = 5;
        } else {
            slashDmg = 18;
            multiDmg = 7;
        }
        // 意图0的伤害
        this.damage.add(new DamageInfo(this, slashDmg));
        // 意图1的伤害
        this.damage.add(new DamageInfo(this, multiDmg));
    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, 5, false)));
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        int move;
        if (num < 40) {
            this.setMove((byte)0, Intent.ATTACK, damage.get(0).base);
        } else {
            this.setMove((byte)1, Intent.ATTACK, damage.get(1).base, 3, true);
        }
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch(this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1:
                for(int i = 0 ; i < 2 ; i++){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}