package HsrMod.core;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author darkbloodevil
 * @date 2024/7/25 11:38
 * @description 加入了削韧量和元素类型
 */
public class HsrDamageInfo extends DamageInfo {
    // 默认4削韧
    public int toughness_reduction = 4;
    public boolean is_follow_up = false;
    public AbstractCreature damageSource;
    public int base;
    public DamageType type;
    public boolean is_aoe=false;

    /**
     * 把DamageInfo转为HsrDamageInfo
     * @param info DamageInfo
     * @return HsrDamageInfo
     */
    public static HsrDamageInfo to_hsr_info(DamageInfo info){
        HsrDamageInfo h_info;
        if (info instanceof HsrDamageInfo) {
            h_info = (HsrDamageInfo) info;
        } else {
            h_info = new HsrDamageInfo(info);
        }
        return h_info;
    }

    public HsrDamageInfo(DamageInfo info) {
        super(info.owner, info.base, info.type);
        this.damageSource=info.owner;
        this.base=info.base;
        this.type=info.type;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
        this.toughness_reduction = toughness_reduction;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction, boolean is_follow_up) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
        this.toughness_reduction = toughness_reduction;
        this.is_follow_up = is_follow_up;
    }
    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction,boolean is_follow_up,boolean is_aoe) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
        this.toughness_reduction = toughness_reduction;
        this.is_follow_up = is_follow_up;
        this.is_aoe=is_aoe;
    }

    public HsrDamageInfo clone(){
        return new HsrDamageInfo(damageSource,base,type,toughness_reduction,is_follow_up);
    }
}
