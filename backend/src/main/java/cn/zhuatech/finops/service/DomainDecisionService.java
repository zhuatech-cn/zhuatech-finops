/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.finops.service;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class DomainDecisionService {
 public DecisionResult assess(DecisionRequest request) { double variance=request.actualSpend()-request.monthlyBudget();double growth=request.previousSpend()==0?0:(request.actualSpend()-request.previousSpend())*100d/request.previousSpend();int score=100;List<String> actions=new ArrayList<>();if(variance>0){score-=30;actions.add("处理预算超支并更新预测");}if(growth>20){score-=20;actions.add("调查月度费用异常增长");}if(request.idleResources()>0){score-=Math.min(20,request.idleResources());actions.add("关停或降配闲置资源");}if(request.untaggedCost()>request.actualSpend()*0.05){score-=20;actions.add("补齐成本分摊标签");}if(request.committedCoverage()<70){score-=10;actions.add("评估承诺折扣覆盖");}return result(score,actions,"OPTIMIZED","OPTIMIZE","COST_ALERT",Map.of("budgetVariance",variance,"monthGrowthPercent",Math.round(growth*10)/10d,"untaggedCost",request.untaggedCost())); }
 private DecisionResult result(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=80?good:score>=50?warn:bad;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 private DecisionResult riskResult(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=70?bad:score>=40?warn:good;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 public record DecisionRequest(
        @NotBlank String accountCode,
        @PositiveOrZero double monthlyBudget,
        @PositiveOrZero double actualSpend,
        @PositiveOrZero double previousSpend,
        @PositiveOrZero int idleResources,
        @PositiveOrZero double untaggedCost,
        @DecimalMin("0") @DecimalMax("100") double committedCoverage) {}
 public record DecisionResult(String decision,int score,Map<String,Object> metrics,List<String> actions) {}
}
