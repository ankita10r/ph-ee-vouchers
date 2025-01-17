package org.mifos.pheevouchermanagementsystem.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModalityDTO {
    private String paymentModality;
    private String financialAddress;
    private String bankingInstitutionCode;
}
