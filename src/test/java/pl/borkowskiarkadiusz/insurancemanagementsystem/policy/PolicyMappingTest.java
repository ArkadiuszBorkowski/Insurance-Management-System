package pl.borkowskiarkadiusz.insurancemanagementsystem.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.borkowskiarkadiusz.insurancemanagementsystem.dto.PolicyDTO;
import pl.borkowskiarkadiusz.insurancemanagementsystem.entity.Policy;
import pl.borkowskiarkadiusz.insurancemanagementsystem.enums.PolicyStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicyMappingTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void testPolicyToPolicyDTOMapping() {
        // Tworzenie obiektu Policy
        Policy policy = new Policy();
        policy.setPolicyStatus(PolicyStatus.AKTYWNA);
        PolicyStatus policyStatus = policy.getPolicyStatus();


        // Mapowanie Policy na PolicyDTO
        PolicyDTO policyDTO = modelMapper.map(policy, PolicyDTO.class);
        PolicyStatus policyStatusDTO = policyDTO.getPolicyStatus();


        // Sprawdzenie, czy status został poprawnie zmapowany
        assertEquals(policyStatus, policyStatusDTO);
    }

}
