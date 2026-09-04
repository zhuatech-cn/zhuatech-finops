/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.finops.controller;

import cn.zhuatech.finops.common.ApiResponse;
import cn.zhuatech.finops.service.CloudCommitmentAuthorizationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/finops")
public class CloudCommitmentAuthorizationController {
    private final CloudCommitmentAuthorizationService service;
    public CloudCommitmentAuthorizationController(CloudCommitmentAuthorizationService service) { this.service = service; }

    @PostMapping("/cloud-commitment-authorization")
    public ApiResponse<?> assess(@RequestBody CloudCommitmentAuthorizationService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
