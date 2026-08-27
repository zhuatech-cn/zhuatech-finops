/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.finops.domain;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();
    public DomainCatalog() {
        actions.put("ASSESS", new WorkflowAction("ASSESS", "提交优化评估", List.of("草稿"), "评估中", "OPERATOR"));
        actions.put("APPROVE", new WorkflowAction("APPROVE", "批准优化方案", List.of("评估中"), "执行中", "ADMIN"));
        actions.put("VERIFY", new WorkflowAction("VERIFY", "验证节省结果", List.of("执行中"), "已完成", "ADMIN"));
    }
    public String systemName() { return "知华科技云成本与 FinOps 管理平台"; }
    public String scene() { return "云账号、账单、分摊、标签、预算、异常、承诺折扣、资源优化与经营分析"; }
    public String initialStatus() { return "草稿"; }
    public String partyLabel() { return "云账号/成本中心"; }
    public String amountLabel() { return "云资源成本"; }
    public String quantityLabel() { return "资源数量"; }
    public String dueLabel() { return "优化期限"; }
    public List<ModuleDefinition> modules() { return List.of(
            new ModuleDefinition("ACCOUNT", "云账号管理", "统一多云账号、组织和成本中心关系"),
            new ModuleDefinition("BILLING", "账单归集", "采集账单明细、价格和使用量"),
            new ModuleDefinition("ALLOCATION", "成本分摊", "按标签、组织、产品和规则分摊成本"),
            new ModuleDefinition("TAGGING", "标签治理", "检查标签覆盖、规范和责任归属"),
            new ModuleDefinition("BUDGET", "预算管理", "管理预算、预测、阈值和通知"),
            new ModuleDefinition("ANOMALY", "费用异常", "识别突增、闲置和异常购买"),
            new ModuleDefinition("COMMITMENT", "承诺折扣", "管理预留、节省计划和覆盖利用"),
            new ModuleDefinition("OPTIMIZATION", "资源优化", "输出规格调整、关停和存储优化建议"),
            new ModuleDefinition("UNIT_ECONOMICS", "单位经济", "关联客户、订单和产品分析单位成本")
        ); }
    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }
    public record ModuleDefinition(String code,String name,String description) {}
    public record WorkflowAction(String code,String label,List<String> from,String to,String requiredRole) {}
}
