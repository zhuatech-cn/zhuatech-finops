/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.finops.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CloudCommitmentAuthorizationService {
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.commitmentId() == null || request.commitmentId().isBlank()) blockers.add("云资源承诺编号不能为空");
        if (!request.usageBaselineStable()) blockers.add("历史用量基线不稳定");
        if (!request.forecastConfidenceHigh()) blockers.add("需求预测置信度不足");
        if (!request.budgetApproved()) blockers.add("承诺采购预算未批准");
        if (!request.termRiskReviewed()) blockers.add("期限与锁定风险未评审");
        if (!request.financeApproved()) blockers.add("财务审批缺失");
        if (!request.buyerSeparated()) blockers.add("方案制定与采购执行未职责分离");
        if (!request.auditReady()) blockers.add("云资源承诺审计证据不完整");
        if (!request.discountVerified()) actions.add("核验折扣与按需成本基线");
        if (!request.portabilityAssessed()) actions.add("评估区域、实例和服务可迁移性");
        if (!request.ownerApproved()) actions.add("取得成本责任人批准");
        if (!request.exitPlanReady()) actions.add("准备转售、转换或退出方案");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.BUY : Decision.REVIEW;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public enum Decision { BUY, REVIEW, BLOCKED }
    public record Request(String commitmentId, boolean usageBaselineStable,
                          boolean forecastConfidenceHigh, boolean discountVerified,
                          boolean budgetApproved, boolean termRiskReviewed,
                          boolean portabilityAssessed, boolean ownerApproved,
                          boolean financeApproved, boolean buyerSeparated,
                          boolean exitPlanReady, boolean auditReady) {}
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}
