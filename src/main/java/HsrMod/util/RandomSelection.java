package HsrMod.util;

/**
 * @author darkbloodevil
 * @date 2024/8/1 11:22
 * @description
 */

import com.megacrit.cardcrawl.core.Settings;

import java.util.Random;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.floorNum;

public class RandomSelection {
    public static Random selectionRandomRng;
    public static int[] selectRandomElements(int m, int n) {
        int[] array = new int[m];
        for (int i = 0; i < m; i++) {
            array[i] = i;
        }

        int[] result = new int[n];
        int arrayLength = array.length;

        for (int i = 0; i < n; i++) {
            selectionRandomRng = new Random(Settings.seed + floorNum);
            // 从剩余的元素中随机选择一个
            int randomIndex = selectionRandomRng.nextInt(arrayLength - i);
            result[i] = array[randomIndex];

            // 将已选择的元素移动到数组末尾（逻辑上删除）
            int temp = array[randomIndex];
            array[randomIndex] = array[arrayLength - i - 1];
            array[arrayLength - i - 1] = temp;
        }

        return result;
    }
}

