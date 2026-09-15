# Code Review Checklist

> **Change** `helloworld` · **分支/Commit** `AI/task-DEV-73634509-84c9-11f1-9849-d5c90ba1aaae-7ee06dbe-d56d-4b81-afdb-73eb5b1d8b61` / `eca0f69` · **日期** `2026-09-15`
>
> **AI**：唯一进度源；状态仅用 `⬜` `✅` `❌` `⚠️` `N/A`。
> **完成标准**：所有核销项必须从 `⬜` 变为其他状态；`N/A` 需写原因。
>
> **执行顺序（强制）**：写入本清单并进入逐文件审查前，先在目标仓库对变更路径运行 `references/script/scan-all-rules.sh`，将输出贴入 Step 3 和 Step 4 备注；再用 LLM 完成 Step 2–5 中脚本未覆盖项及复核。

---

## Step 1 — 执行队列（产物 A）

> **Step4 列语义**：每个 **Sn / Gn** 表示「**本文件**在 Step4 审查中，对 `reliability-checklist.md` 第 **G*n*** 节、`security-checklist.md` 第 **S*n*** 节的扫描结论」。**Bug 模式（B/M/I）** 不在本表分列，在下方 **§4.1** 按清单 ID 核销（可与 `scan-all-rules.sh` 预扫结果对照）。与变更无关填 `N/A`；已扫无命中填 `✅`；命中风险填 `⚠️` 或 `❌`（并在 Step 4 明细表与 report 中写清 `Gx.x` / `Sx.x` + `path:line`）。

**列说明（与 references 章节对齐）**

| 列组 | 列名 | 对应清单章节 |
|------|------|----------------|
| 可靠性 | **G1** … **G17**（+ **G18** 仅明细表） | `reliability-checklist.md` — G1 并发 … G17 可应急；**G18** 安全补强在 Step 4.2 逐条核销，Step 1 可不单列 |
| 安全 | **S1** … **S10** | `security-checklist.md` — S1 SQL 注入 … S10 CSRF/CORS/跳转 |

| # | 文件（仓库相对路径） | 归属原因 | Step2 | Step3 | G1 | G2 | G3 | G4 | G5 | G6 | G7 | G8 | G9 | G10 | G11 | G12 | G13 | G14 | G15 | G16 | G17 | S1 | S2 | S3 | S4 | S5 | S6 | S7 | S8 | S9 | S10 | 总状态 |
|---|----------------------|----------|-------|-------|----|----|----|----|----|----|----|----|----|-----|----|----|----|----|----|----|----|----|----|-----|-----|-----|-----|-----|-----|-----|-----|--------|
| 1 | `helloworld/src/HelloWorldApplication.java` | REQ-1: 主入口 | ✅ | ⚠️ | N/A | N/A | N/A | N/A | N/A | N/A | N/A | ✅ | N/A | N/A | ✅ | N/A | N/A | N/A | N/A | ✅ | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | ✅ |
| 2 | `helloworld/test/HelloWorldApplicationTest.java` | REQ-2: 单元测试 | ✅ | ⚠️ | N/A | N/A | N/A | N/A | N/A | N/A | N/A | ✅ | N/A | N/A | ✅ | N/A | N/A | N/A | N/A | ✅ | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | ⚠️ |
| 3 | `helloworld/Makefile` | 构建脚本 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 | 跳过 |

- 由 `git diff --name-only …` 等展开；**禁止 glob**；非 Java 标 `跳过`（跳过文件的 Step4 各列可统一 `跳过` 或 `N/A(非 Java)`）。
- **守卫**：无 `.java` → 按技能终止。
- **收口**：每文件各 **Sn/Gn** 列均非 `⬜` 后，再与下方 Step 4 **逐条 ID 表** 核对一致；若某大类整节与当前文件无关，该列可一次性标 `N/A(无 SQL/无 MQ/…)`，但须在 Step 4 明细对应 ID 行同样标 `N/A` 并写原因。

### 自动化预扫结果（`scan-all-rules.sh`）

执行命令：`bash skills/dtazziboot-java-code-review/references/script/scan-all-rules.sh helloworld`

```
=== Step 4 Rule Scan (B/M/I + A/S/G) ===
Targets: helloworld
Engine:  ripgrep

[P2] A2.2 — WildcardImport: helloworld/test/HelloWorldApplicationTest.java:1

=== Summary: 1 findings (P0=0, P1=0, P2=1) | 52/222 rules scanned ===
```

唯一命中：`A2.2` — `helloworld/test/HelloWorldApplicationTest.java:1` 使用了通配符导入（`import static org.junit.jupiter.api.Assertions.*;`）。此项已归入 Step 3 可读性检查 A2.2。

---

## Step 2 — 功能（产物 B）

> 仅从 spec/tasks 提 **REQ**，勿臆造。不符 spec 标 **P0**。
> 每个 REQ 都必须填写 **spec 证据** 与 **关联文件**；若命中 P0，代码证据需落到 `path:line`、测试或接口行为。

需求来源：需求描述「写个helloworld」。

| REQ | Scenario | Spec证据（原文/章节） | 关联文件 | 状态 | 代码证据（文件/测试/接口） |
|-----|----------|----------------------|----------|------|----------------------------|
| REQ-1 | 应用应输出 "Hello, World!" 到控制台 | 需求描述:「写个helloworld」 | `helloworld/src/HelloWorldApplication.java` | ✅ | `HelloWorldApplication.java:27` — `System.out.println(GREETING)`，常量值 `"Hello, World!"` |
| REQ-2 | 应有单元测试覆盖 getGreeting() 和 printGreeting() | 约定俗成：helloworld 应有测试 | `helloworld/test/HelloWorldApplicationTest.java` | ✅ | `HelloWorldApplicationTest.java:30-51` — 3 条测试用例覆盖 getGreeting、main、printGreeting |

---

## Step 3 — 可读性检查（产物 C）

> 无 Java：**整节 N/A**。

对照 `references/readability-checklist.md` A1–A7 逐节核销：

| ID | 检查项 | 状态 | 备注（命中写 `path:line`） |
|----|--------|------|----------------------------|
| A1 | 源文件格式 | ✅ | 文件名=类名，UTF-8，空格合规 |
| A2 | 源文件结构/import 顺序 | ⚠️ | `helloworld/test/HelloWorldApplicationTest.java:1` — `import static org.junit.jupiter.api.Assertions.*;` 使用了通配符导入（A2.2 违规，P2） |
| A3 | 代码样式 | ✅ | K&R 大括号、4空格缩进、行宽≤120、空格使用均合规 |
| A4 | 命名规范 | ✅ | `HelloWorldApplication`(UpperCamelCase)、`printGreeting`/`getGreeting`(lowerCamelCase)、`GREETING`(UPPER_SNAKE_CASE) 均合规 |
| A5 | 编码实践 | ✅ | 无重写方法，无空catch，静态方法调用合规 |
| A6 | 特定元素样式 | ✅ | 数组方括号属于类型，修饰符顺序合规 |
| A7 | Javadoc 规范 | ✅ | 类、方法均有 Javadoc，块标记顺序合规 |

---

## Step 4 — 可靠性检查（产物 D）

> **逐条核销（强制）**：G/S 每个 ID **独占一行**，禁止合并为区间（例如 ~~`G1.1 ~ G14.3`~~）。**Bug 模式** 按 `bug-pattern-checklist.md` 中 **每条 B*/M*/I*** 独占一行核销（120 条）**；无关变更可对该 ID 标 `N/A` 并写原因。报告等级：**Blocker→P0、Major→P1、Info→P2**。

### 4.1 Bug 模式（`bug-pattern-checklist.md`）

> 可先运行 `references/script/scan-all-rules.sh`（对变更目录）将命中写入备注，再人工/LLM 补全脚本未覆盖规则。

预扫结果：无 B/M/I 命中。

| ID | 状态 | 备注（命中写 `path:line`；预扫可粘贴脚本摘要） |
|----|------|--------------------------------------------------|
| B001 | N/A | 无 parse/of 调用 |
| B002 | N/A | 无数组比较 |
| B003 | N/A | 无 Arrays.fill |
| B004 | N/A | 无数组 toString |
| B005 | N/A | 无 Arrays.asList |
| B006 | N/A | 无 assertEquals 调用 |
| B007 | N/A | 无 catch(Throwable) |
| B008 | N/A | 无 Executors 调用 |
| B009 | N/A | 无移位操作 |
| B010 | N/A | 无 BigDecimal |
| B011 | N/A | 无包装类型 == 比较 |
| B012 | N/A | 无 Calendar |
| B013 | N/A | 无 Calendar |
| B014 | N/A | 无集合查询 |
| B015 | N/A | 无 toArray |
| B016 | N/A | 无 Comparable |
| B017 | N/A | 无 this == null |
| B018 | N/A | 无三目数值分支 |
| B019 | N/A | 无 Money 类 |
| B020 | N/A | 无常量乘法 |
| B021 | N/A | 无 Jedis |
| B022 | N/A | 无 SimpleDateFormat |
| B023 | N/A | 无异常实例未抛出 |
| B024 | N/A | 无 Thread |
| B025 | N/A | 无双括号初始化 |
| B026 | N/A | 无 equals(null) |
| B027 | N/A | 无 equals 方法 |
| B028 | N/A | 无 DateUtil |
| B029 | N/A | 无 setter |
| B030 | N/A | 无浮点 == |
| B031 | N/A | 无 String.format |
| B032 | N/A | 无注解 getClass |
| B033 | N/A | 无 Unsafe |
| B034 | N/A | 无 Hashtable |
| B035 | N/A | 无可化简表达式 |
| B036 | N/A | 无 IdentityHashMap |
| B037 | N/A | 无可变参数 |
| B038 | N/A | 无递归 |
| B039 | N/A | 无 indexOf |
| B040 | N/A | 无 isInstance |
| B041 | N/A | 无 JDBC |
| B042 | N/A | 无 JUnit3 测试 |
| B043 | N/A | 无内部类测试 |
| B044 | N/A | 无 JUnit3+4 混用 |
| B045 | N/A | 无同步锁 |
| B046 | N/A | 无循环 |
| B047 | N/A | 无 compare |
| B048 | N/A | 无 Math.round |
| B049 | N/A | 无日期格式 |
| B050 | N/A | 无日期格式 |
| B051 | N/A | 无 Boolean.getBoolean |
| B052 | N/A | 无日期格式 |
| B053 | N/A | 无 try-catch 测试模式 |
| B054 | N/A | 无 EqualsTester |
| B055 | N/A | 无 Mockito |
| B056 | N/A | 无 Arrays.asList 修改 |
| B057 | N/A | 无增强 for 循环修改 |
| B058 | N/A | 无集合自身操作 |
| B059 | N/A | 无 nCopies |
| B060 | N/A | 无三目 null |
| B061 | N/A | 无 Base64 |
| B062 | N/A | 无 ClassLoader |
| B063 | N/A | 无 javax.xml |
| B064 | N/A | 无 Optional == |
| B065 | N/A | 无 setter 自赋值 |
| B066 | N/A | 无 Math.random 强转 |
| B067 | N/A | 无 Random |
| B068 | N/A | 无自赋值 |
| B069 | N/A | 无 compareTo |
| B070 | N/A | 无 equals 自比 |
| B071 | N/A | 无 size >= 0 |
| B072 | N/A | 无 Stream.toString |
| B073 | N/A | 无 StringBuilder(char) |
| B074 | N/A | 无 substring(0) |
| B075 | N/A | 无 for 循环 |
| B076 | N/A | 无 @Transactional |
| B077 | N/A | 无 catch(Throwable) 测试 |
| B078 | N/A | 无 Truth 自等 |
| B079 | N/A | 无 @Mock |
| B080 | N/A | 单测有断言（3条测试均有 assertEquals） |
| B081 | N/A | 无集合原地操作 |
| M001 | N/A | 无重复条件判断 |
| M002 | N/A | 无 instanceof |
| M003 | N/A | 无包装类构造器 |
| M004 | N/A | 无 printStackTrace |
| M005 | N/A | 无内部类 |
| M006 | N/A | 无布尔表达式 |
| M007 | N/A | 无空 catch |
| M008 | N/A | 无 equals/hashCode |
| M009 | N/A | 无 equals 不同类型 |
| M010 | N/A | 无比特运算 |
| M011 | N/A | 无 switch |
| M012 | N/A | 无 finally |
| M013 | N/A | 无类型转换 |
| M014 | N/A | 无枚举 getClass |
| M015 | N/A | 无继承 |
| M016 | N/A | 无时间 API |
| M017 | N/A | 测试方法均有 @Test 注解 |
| M018 | N/A | 无 Lock |
| M019 | N/A | 无 switch 枚举 |
| M020 | N/A | 无重写方法 |
| M021 | N/A | 无 equals 方法 |
| M022 | N/A | 无 Optional |
| M023 | N/A | `System.out.println` 已使用常量字符串 |
| M024 | N/A | 无 Optional |
| M025 | N/A | 无 final 类 |
| M026 | N/A | 无 @Mock |
| M027 | N/A | 无 ThreadLocal |
| I001 | N/A | 无异常断言 |
| I002 | N/A | 无 @DoNotMock |
| I003 | N/A | 无 @AutoValue |
| I004 | N/A | 无 java.util.Date |
| I005 | N/A | 使用 JUnit5 |
| I006 | N/A | setUp 有 @BeforeEach |
| I007 | N/A | tearDown 有 @AfterEach |
| I008 | N/A | 无 DataProvider |
| I009 | N/A | 统计项 |
| I010 | N/A | 无容器启动 |

### 4.2 可靠性（`reliability-checklist.md`）

| ID | 状态 | 备注 |
|----|------|------|
| G1.1 | N/A | 无并发场景 |
| G1.2 | N/A | 无并发场景 |
| G1.3 | N/A | 无并发场景 |
| G1.4 | N/A | 无并发场景 |
| G2.1 | N/A | 无写接口 |
| G2.2 | N/A | 无重试/MQ |
| G2.3 | N/A | 无幂等 |
| G3.1 | N/A | 无事务 |
| G3.2 | N/A | 无事务 |
| G4.1 | N/A | 无 SQL |
| G4.2 | N/A | 无 SQL |
| G4.3 | N/A | 无 SQL |
| G4.4 | N/A | 无 SQL |
| G5.1 | N/A | 无 MQ |
| G6.1 | N/A | 无缓存 |
| G6.2 | N/A | 无缓存 |
| G7.1 | N/A | 无调度任务 |
| G7.2 | N/A | 无调度任务 |
| G8.1 | ✅ | 无 catch 块 |
| G8.2 | N/A | 无外部依赖 |
| G8.3 | N/A | 无 I/O 流/连接/锁 |
| G8.4 | N/A | 无线程池 |
| G8.5 | N/A | 无 ThreadLocal |
| G8.6 | N/A | 无线程池 |
| G9.1 | N/A | 无外部调用 |
| G9.2 | N/A | 无外部调用 |
| G9.3 | N/A | 无重试 |
| G10.1 | N/A | 无接口契约 |
| G10.2 | N/A | 无接口契约 |
| G11.1 | ✅ | 有单测，3 条均有断言 |
| G11.2 | ✅ | 覆盖正常路径（getGreeting、main、printGreeting），边界场景对于静态输出不适用 |
| G11.3 | N/A | 无入参校验需求（无 public 方法接受外部参数） |
| G11.4 | N/A | 无数值运算 |
| G12.1 | N/A | 无资金场景 |
| G12.2 | N/A | 无资金场景 |
| G13.1 | N/A | 无错误日志 |
| G14.1 | N/A | 无金额运算 |
| G14.2 | N/A | 无多租户 |
| G14.3 | N/A | 无时间处理 |
| G14.4 | N/A | 无时间格式化 |
| G15.1 | N/A | 无 DDL |
| G15.2 | N/A | 无接口共存 |
| G15.3 | N/A | 无开关 |
| G16.1 | N/A | 核心路径为控制台输出，非服务 |
| G16.2 | ✅ | 无异常路径 |
| G16.3 | N/A | 无日志 |
| G16.4 | N/A | 无 catch 块 |
| G17.1 | N/A | 无功能开关 |
| G17.2 | N/A | 无降级 |
| G17.3 | N/A | 无数据变更 |

### 4.3 安全（`security-checklist.md`）

| ID | 状态 | 备注 |
|----|------|------|
| S1.1 | N/A | 无 SQL |
| S1.2 | N/A | 无 SQL |
| S1.3 | N/A | 无 SQL |
| S2.1 | N/A | 无 HTML/Web 输出 |
| S2.2 | N/A | 无富文本 |
| S2.3 | N/A | 无模板引擎 |
| S3.1 | N/A | 无外部 URL |
| S3.2 | N/A | 无外部 URL |
| S3.3 | N/A | 无外部 URL |
| S4.1 | N/A | 无命令执行 |
| S4.2 | N/A | 无文件操作 |
| S5.1 | N/A | 无 XML |
| S5.2 | N/A | 无 XML |
| S6.1 | N/A | 无反序列化 |
| S6.2 | N/A | 无反序列化 |
| S6.3 | N/A | 无反序列化 |
| S7.1 | N/A | 无文件上传 |
| S7.2 | N/A | 无文件上传 |
| S7.3 | N/A | 无文件上传 |
| S8.1 | N/A | 无 Web 接口 |
| S8.2 | N/A | 无 Web 接口 |
| S8.3 | N/A | 无 Web 接口 |
| S8.4 | N/A | 无 Web 接口 |
| S9.1 | N/A | 无密钥 |
| S9.2 | N/A | 无日志记录敏感信息 |
| S9.3 | N/A | 无传输/存储加密 |
| S9.4 | N/A | 无随机数 |
| S10.1 | N/A | 无 Web 接口 |
| S10.2 | N/A | 无 Web 接口 |
| S10.3 | N/A | 无跳转 |

---

## Step 5 — 自定义扩展检查（产物 E）

> 按 `customized-checklist.md` 逐条核销；若未启用可整节写 `N/A(未启用自定义规则)`。

### 5.1 自定义扩展（`customized-checklist.md`）

| ID | 状态 | 备注 |
|----|------|------|
| U1.1 | N/A | 示例项，本项目未启用自定义规则 |
| U1.2 | N/A | 示例项，本项目未启用自定义规则 |
| U1.3 | N/A | 示例项，本项目未启用自定义规则 |
| U2.1 | N/A | 示例项，本项目未启用自定义规则 |
| U2.2 | N/A | 示例项，本项目未启用自定义规则 |
| U2.3 | N/A | 示例项，本项目未启用自定义规则 |

---

## 终检（防漏检）

- [x] 执行队列中每个文件 `Step2`、`Step3`、**S1–S10 / G1–G17** 各列均非 `⬜`（跳过文件除外）；
- [x] Step 2 的每个 REQ/Scenario 均非 `⬜`
- [x] Step 3 的 A1–A7 均非 `⬜`
- [x] Step 4 全部 **G/S** 与 **B001–B081 / M001–M027 / I001–I010** ID 均非 `⬜`（允许 `N/A`，但有原因）
- [x] Step 5 全部 U* ID 均非 `⬜`（允许 `N/A(未启用自定义规则)`）
- [x] 所有 `❌/⚠️` 已写入 report，且包含 `ID + path:line`