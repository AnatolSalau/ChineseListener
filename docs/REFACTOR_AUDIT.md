# Refactor audit — ChineseListener

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. **Strict procedure:** [REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md). Verify: [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md). Pattern names: [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md).

**Last full pass:** _(not started — fill when first refactor batch completes)_.

## Executive summary

| Area | Verdict | Action |
|------|---------|--------|
| **Module** | `:app` single module | Document layer packages as they are introduced |
| **Layers** | TBD | Map `ui` / `presentation` / `domain` / `data` when refactoring |
| **Kotlin loops** | TBD | Enforce [chinese-listener-kotlin-collections.mdc](../.cursor/rules/chinese-listener-kotlin-collections.mdc) |

---

## Layer 1 — Structure

### Module

| Module | Layer OK? | Findings | Status |
|--------|-----------|----------|--------|
| `:app` | TBD | Default Compose template — assign layers when features grow | open |

### Cross-cutting

| Topic | Finding | Priority | Status |
|-------|---------|----------|--------|
| Package map | Not yet split by layer | medium | open |
| Unit tests | Default sample tests only | low | open |

---

## Principle checklist (per batch)

Copy this table into PR/chat or extend below when closing a REFACTOR_GUIDE Phase 7 batch.

| PR-§1 item | Verified (Y/N/N/A) | Notes |
|------------|-------------------|-------|
| S, O, L, I, D | | |
| DRY, KISS, YAGNI, SSOT | | |
| SoC, LoD, Comp, FF, LP | | |
| Stop-ship items | | |
| GRASP roles for new types | | |
| Pattern tags (Phase 4) | | |
| Gradle verify §6 playbook | | |

---

## File inventory

_Add rows as refactors proceed. Legend: **L** = layer OK, **I** = issue, **P** = pattern suggestion, **T** = tests._

| File / package | L | Notes | Status |
|----------------|---|-------|--------|
| _(example)_ `MainActivity.kt` | | Entry point — keep thin | open |
