package com.finfan.server;

import com.finfan.server.enums.AtkType;
import com.finfan.server.enums.Direction;
import lombok.experimental.UtilityClass;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

@UtilityClass
public class GameUtil {

    public int valuate(int atk, int pDef, int mDef, AtkType atkType, int atkArrows) {
        int p1 = 0;
        int p2 = 0;
        int arrowPrice = 0;
        for (Direction direction : Direction.values()) {
            if ((direction.getMask() & atkArrows) != 0) {
                int p3 = 0;
                if (p1 == 0) {
                    p1 = 100;
                } else if (p2 == 0) {
                    p2 = 200;
                } else {
                    p3 = p2 + p1;
                    p1 = p2;
                    p2 = p3;
                }
                arrowPrice += p3;
            }
        }
        return (Math.max(atk * 100, 100) + Math.max(mDef * 100, 100) + Math.max(pDef * 100, 100)) + atkType.getValuable() + arrowPrice;
    }

    public LocalDateTime getNextExecutionTime(String cronExpression) {
        CronExpression expression = CronExpression.parse(cronExpression);
        return expression.next(LocalDateTime.now());
    }

}
