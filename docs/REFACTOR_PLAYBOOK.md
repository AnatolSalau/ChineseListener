# Refactor playbook — ChineseListener

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

Step-by-step guide for refactors: Kotlin collection style, audit order, verification. Extends [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) and [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md).

**Master pattern catalog:** [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md)  
**Strict refactor how-to:** [REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md)  
**Living audit:** [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md)

---

## 1. When to use which doc

| Question | Read |
|----------|------|
| Layers, UDF, ports, testing | [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) |
| Power, Flow, lifecycle | [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md) |
| **How to refactor safely** | **[REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md)** + [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md) |
| **Gradle verify + Kotlin collection matrix** | **This file** §4–6 |
| **Pattern names** | [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) |
| Agent enforcement | `.cursor/rules/chinese-listener-refactor.mdc` |

---

## 2. Architectural patterns (typical mapping)

Full lists: **[PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md)**. Prefer **ports & adapters** over leaking framework types into domain.

| Pattern | Intent | Typical use |
|---------|--------|-------------|
| **Layered / Clean** | Dependencies inward | `ui` → `presentation` → `domain` ← `data` |
| **Ports & adapters** | Interfaces in domain; platform in data | Repository ports, gateway ports |
| **Repository** | Hide I/O | Data layer implements domain interface |
| **Use case** | One operation, testable | Small classes in `domain` |
| **UDF / MVI-shaped** | State down, events up | ViewModel + sealed events |
| **Facade** | Simple entry over subsystem | Factory / coordinator at composition root |
| **Strategy** | Pluggable algorithm | Swappable policies behind interface |
| **DTO → domain mapper** | Wire shapes stay in data | Mappers at rim |

---

## 3. Algorithmic patterns (when applicable)

Catalog: **[PATTERNS_REFERENCE.md §6](./PATTERNS_REFERENCE.md)**. Use names in PR/audit when refactoring logic (greedy selection, validation, filter+rank, throttle, dedupe by key, etc.).

---

## 4. Kotlin collections and stdlib (style matrix)

**Mandatory:** use **stdlib collection operations** instead of imperative `for`/`while`. Agent rule: `.cursor/rules/chinese-listener-kotlin-collections.mdc`. Parity doc: [CODE_AND_REFACTOR_STYLE.md](./CODE_AND_REFACTOR_STYLE.md). Scan helpers: `util/KotlinLoopReplacements.kt`.

| Goal | Prefer | Avoid when |
|------|--------|------------|
| Transform each element | `map`, `mapIndexed` | Need `break` mid-collection |
| Drop / keep | `filter`, `filterNot`, `takeWhile` | — |
| Flatten nested lists | `flatMap`, `flatten` | — |
| One match | `firstOrNull`, `singleOrNull`, `find` | — |
| Group | `groupBy`, `associate`, `associateBy` | — |
| Split two buckets | `partition` | — |
| Accumulate | `fold`, `reduce`, `runningFold` | Complex early-exit (`firstNotNullOfOrNull`, `generateSequence`) |
| Build list immutably | `buildList { }` | — |
| Lazy multi-step | `asSequence()` / `sequence { }` | Hot tiny lists |
| Strings / lines | `lineSequence()`, `split`, `trim` | — |
| Side effect per item | `forEach` | Replacing `for` that **returns** a value |

### Modern Kotlin checklist

- `data class` + `copy` for state; `sealed interface` for events and results.
- `val` over `var`; avoid `!!` — use `requireNotNull` or early return.
- Coroutines: `viewModelScope`, `StateFlow`, `update { }`; no `GlobalScope`.

Official references:

- [Collection operations](https://kotlinlang.org/docs/collection-operations.html)
- [Coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Flow](https://kotlinlang.org/docs/flow.html)

---

## 5. Step-by-step refactor procedure

Follow **[REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md) Phases 0–7**. This section is a **short index**.

| Phase | REFACTOR_GUIDE | This playbook |
|------:|----------------|---------------|
| 0 | Plan, approval, KISS/YAGNI | — |
| 1 | PR-§1 SOLID, DRY, stop-ship | — |
| 2 | PR-§2 GRASP | — |
| 3 | PR-§3 architecture, UDF | §2 |
| 4 | PR-§4–5 GoF tags | §2 |
| 5 | PR-§6 algorithms | §3 |
| 6 | PR-§7–9 concurrency, Kotlin, tests | §4 |
| 7 | Close batch, audit | §6 |

---

## 6. Verification

After **any** refactor batch:

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

- **No behavior change** without test updates or explicit product approval.
- Permission/background changes: device smoke per [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md).

---

## 7. Approval gates

Per [chinese-listener-core.mdc](../.cursor/rules/chinese-listener-core.mdc):

- Multi-file refactors: **problem, approach, files, risks, verify** in chat; wait for **go ahead**.
- New dependencies, SDK/manifest changes: explicit approval.

---

## 8. Work package order (code phase)

| Order | Scope |
|------:|--------|
| 1 | Dead code, unused scaffold |
| 2 | Package structure (optional) | `ui.*` subpackages — only if audit priority ≥ medium |
| 3 | Domain style + DRY | use cases, validators |
| 4 | Presentation / UI | ViewModel, composables |
| 5 | Data / platform | adapters, mappers |

Update [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md) as each package completes.
