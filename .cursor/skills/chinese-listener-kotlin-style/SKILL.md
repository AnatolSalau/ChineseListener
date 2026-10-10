---
name: chinese-listener-kotlin-style
description: Write and refactor Kotlin like kotlin-mutliplatform-test — PATTERNS_REFERENCE, REFACTOR_GUIDE phases, REFACTOR_PLAYBOOK §4 collections, no for/while, UDF and layers. Use when implementing features, fixing bugs in Kotlin, or any refactor/style pass in ChineseListener.
---

# Kotlin style (write & refactor)

## Read first

1. `docs/CODE_AND_REFACTOR_STYLE.md` — parity with template repo (procedure + collections)
2. `docs/REFACTOR_PLAYBOOK.md` §4 — collection/style matrix
3. `.cursor/rules/chinese-listener-kotlin-collections.mdc` — **no loops**
4. `.cursor/rules/chinese-listener-code-style.mdc` — enforceable summary
5. `docs/PATTERNS_REFERENCE.md` — pattern names for structure changes
6. For **refactor batches:** `docs/REFACTOR_GUIDE.md`, `docs/REFACTOR_AUDIT.md`, `.cursor/rules/chinese-listener-refactor.mdc`

## Writing new code

- Match existing packages under `com.example.chineselistener` and layer (`ui`, `presentation`, `domain`, `data`, `theme`, `util`).
- Prefer **collection pipelines** over mutable loops; use `buildList` / `fold` / `lineSequence` per playbook §4.
- State: `sealed` + `data class`; async: `StateFlow` + structured concurrency.
- If you need index walks or angle wrap, use or extend `util/KotlinLoopReplacements.kt`.

## Refactoring (same approach as kotlin-mutliplatform-test)

1. **Phase 0** — plan, scope, verification (approval if multi-file).
2. **Phases 1–6** — PR-§1 principles, GRASP, architecture, GoF tags, algorithms, Kotlin/tests.
3. **Phase 6** — replace any `for`/`while` with stdlib ops or shared scan helpers.
4. **Phase 7** — Gradle verify; fill audit checklist.

**Minimal diff** per batch; tag patterns (e.g. Strategy, Repository) in audit/PR.

## Quick replacements

| Instead of | Use |
|------------|-----|
| `for (x in list)` building result | `map` / `filter` / `fold` / `buildList` |
| `for` with break | `firstOrNull`, `firstNotNullOfOrNull`, `takeWhile` |
| `while (i < n)` | `(0 until n).firstOrNull { … }`, `generateSequence` |
| `for (line in text.lines())` | `text.lineSequence()` |
| Channel `for` | `consumeEach` |

## Must not

- Add imperative loops “just once” — extend `KotlinLoopReplacements` if playbook ops are insufficient.
- Skip REFACTOR_GUIDE for cross-package structural refactors.
- Mix domain rules into composables.

## Verify

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```
