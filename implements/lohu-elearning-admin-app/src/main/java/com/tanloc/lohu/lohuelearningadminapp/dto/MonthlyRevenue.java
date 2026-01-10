package com.tanloc.lohu.lohuelearningadminapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonthlyRevenue {
    Integer month;
    Long totalMoney;
}
