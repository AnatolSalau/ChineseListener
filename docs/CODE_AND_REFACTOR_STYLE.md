# Code & refactor style (parity with kotlin-mutliplatform-test)

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

ChineseListener uses the **same engineering approach** as the template repo `kotlin-mutliplatform-test` for **how** we write and refactor Kotlin — not the template’s product features or KMP module layout.

| Topic | This repo | Template (reference only) |
|-------|-----------|---------------------------|
| Refactor procedure | [REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md) Phases 0–7 | Same phase model |
| Pattern names | [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) (PR-§*n*) | Same catalog |
| Kotlin collections | **No** imperative `for` / `while` / `do-while` | Same rule |
| Style matrix | [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) §4 | Same matrix |
| Loop replacements | `util/KotlinLoopReplacements.kt` | `KotlinLoopReplacements.kt` in template |
| Layers / UDF | [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) (`:app` packages) | `KMP_PRACTICES.md` (`:shared` source sets) |

## Mandatory for all new and changed Kotlin

1. **Collection pipelines** — `map`, `filter`, `fold`, `buildList`, `lineSequence`, `generateSequence`, etc. See [REFACTOR_PLAYBOOK.md §4](./REFACTOR_PLAYBOOK.md).
2. **Immutability** — `val`, `data class` + `copy`, `sealed interface` for events/results/errors.
3. **Refactor batches** — follow [REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md); record progress in [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md).
4. **Pattern tags** — name patterns from [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) in PR/chat when restructuring (Strategy, Repository, UDF, etc.).

## When a loop seems required

- Numeric / indexed search: `(start until end step stride).firstOrNull { … }`, `generateSequence`, or helpers in `com.example.chineselistener.util.KotlinLoopReplacements`.
- Angle / range wrap: `tailrec` (see `wrapDegrees` in that file).
- Channels: `consumeEach`, not `for` over channel.
- Early exit over a collection: `firstNotNullOfOrNull`, `takeWhile`, `asSequence()`.

## Agent enforcement

| Artifact | Role |
|----------|------|
| `.cursor/rules/chinese-listener-kotlin-collections.mdc` | No imperative loops (always on `**/*.kt`) |
| `.cursor/rules/chinese-listener-code-style.mdc` | Full write/refactor Kotlin style |
| `.cursor/rules/chinese-listener-refactor.mdc` | Refactor program gate |
| `.cursor/skills/chinese-listener-kotlin-style/SKILL.md` | Skill for implementing or refactoring code |

## Verification

```bash
./gradlew :app:testDebugUnitTest :app:assembleDebug
```

After style-only refactors, grep mentally: no `for (` / `while (` in production Kotlin except inside **string literals** or **comments** (there should be none in code).
