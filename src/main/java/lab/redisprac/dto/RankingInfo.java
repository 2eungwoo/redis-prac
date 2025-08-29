package lab.redisprac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingInfo {
    private String userId;
    private Double score;
}
