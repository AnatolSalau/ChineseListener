---
name: chinese-listener-refactor
description: Strict refactor — PATTERNS_REFERENCE + REFACTOR_GUIDE Phases 0–7, audit checklist, Kotlin style, Gradle verification. Use when refactoring structure or style across ChineseListener.
---

# Refactor workflow

## Read first

1. `docs/PATTERNS_REFERENCE.md` — definitions (PR-§1–9)
2. `docs/REFACTOR_GUIDE.md` — **mandatory Phases 0–7**
3. `docs/REFACTOR_AUDIT.md` — next row + fill per-batch checklist
4. `docs/REFACTOR_PLAYBOOK.md` — collection matrix, Gradle verify
5. `docs/ANDROID_PRACTICES.md` — layers and ports
6. `.cursor/rules/chinese-listener-refactor.mdc` — enforceable rules
7. `docs/CODE_AND_REFACTOR_STYLE.md` — parity with kotlin-mutliplatform-test procedure
8. `.cursor/rules/chinese-listener-kotlin-collections.mdc` — **mandatory** collection style
9. `.cursor/skills/chinese-listener-kotlin-style/SKILL.md` — write/refactor Kotlin

## Workflow

1. **REFACTOR_GUIDE Phase 0** — plan in chat (unless batch pre-approved).
2. **Phases 1–6** — principles, GRASP, architecture, pattern tags, algorithms, Kotlin/tests.
3. **Minimal diff** — one work package; behavior preserved unless tests updated.
4. **Phase 7** — Gradle verify; device smoke if permissions/background touched.
5. **Close audit** — principle checklist + row status in `REFACTOR_AUDIT.md`.

## Style defaults

- **All Kotlin:** mandatory stdlib collection ops — **no** imperative `for`/`while` (`chinese-listener-kotlin-collections.mdc`; helpers in `util/KotlinLoopReplacements.kt`).

## Must not

- Skip REFACTOR_GUIDE phases or jump to cosmetic renames.
- Violate PR-§1 stop-ship list (see REFACTOR_GUIDE Phase 1).
- Remove lifecycle or energy guards without explicit approval.
- Fabricate test results.
