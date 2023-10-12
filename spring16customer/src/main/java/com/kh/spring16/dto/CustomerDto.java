package com.kh.spring16.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
int customerNo, customerMileage;
String customerId,customerContact,customerJoin,customerPurchase,customerLevel;
}
