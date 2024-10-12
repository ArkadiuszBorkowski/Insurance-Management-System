package pl.borkowskiarkadiusz.insurancemanagementsystem.policy;

import org.junit.jupiter.api.Test;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicyDTOTest {

    @Test
    public void testUpdatePolicyStatus_Aktywna() {
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setStartDate(LocalDate.now().minusDays(1));
        policyDTO.setEndDate(LocalDate.now().plusDays(1));
        policyDTO.setReserveAmount(100.0);

        policyDTO.updatePolicyStatus();

        assertEquals(PolicyStatus.AKTYWNA, policyDTO.getPolicyStatus());
    }

    @Test
    public void testUpdatePolicyStatus_Wygasla() {
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setStartDate(LocalDate.now().minusDays(10));
        policyDTO.setEndDate(LocalDate.now().minusDays(1));
        policyDTO.setReserveAmount(100.0);

        policyDTO.updatePolicyStatus();

        assertEquals(PolicyStatus.WYGASŁA, policyDTO.getPolicyStatus());
    }

    @Test
    public void testUpdatePolicyStatus_Zamknieta() {
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setStartDate(LocalDate.now().minusDays(10));
        policyDTO.setEndDate(LocalDate.now().plusDays(10));
        policyDTO.setReserveAmount((double) 0);

        policyDTO.updatePolicyStatus();

        assertEquals(PolicyStatus.ZAMKNIĘTA, policyDTO.getPolicyStatus());
    }
}