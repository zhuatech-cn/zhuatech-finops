/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.finops.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class CloudCommitmentAuthorizationServiceTest {
    private final CloudCommitmentAuthorizationService service = new CloudCommitmentAuthorizationService();

    @Test void authorizesGovernedCloudCommitment() {
        var result = service.assess(new CloudCommitmentAuthorizationService.Request("COM-100", true, true, true,
                true, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(CloudCommitmentAuthorizationService.Decision.BUY);
    }

    @Test void routesCommercialGapsToReview() {
        var result = service.assess(new CloudCommitmentAuthorizationService.Request("COM-101", true, true, false,
                true, true, false, false, true, true, false, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(CloudCommitmentAuthorizationService.Decision.REVIEW);
    }

    @Test void blocksUnsafeCloudCommitment() {
        var result = service.assess(new CloudCommitmentAuthorizationService.Request("", false, false, false,
                false, false, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(8);
        assertThat(result.decision()).isEqualTo(CloudCommitmentAuthorizationService.Decision.BLOCKED);
    }
}
