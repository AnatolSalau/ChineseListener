---
name: chinese-listener-engineering
description: ChineseListener — single-module Android app, strict layered UDF, approval before non-trivial edits, honesty, citations, git never push. Use for any task in this repo.
---

# ChineseListener engineering

## Canonical rules (read in order)

| File | When |
|------|------|
| `.cursor/rules/chinese-listener-core.mdc` | Every task |
| `.cursor/rules/chinese-listener-docs-bundle.mdc` | User referenced `@docs/WHAT_WE_DO.md` — read full `docs/` bundle |
| `.cursor/rules/chinese-listener-energy-efficiency.mdc` | Battery, background work, Flow/Compose discipline |
| `.cursor/rules/chinese-listener-code-style.mdc` | Kotlin write/refactor — patterns, collections, REFACTOR parity |
| `.cursor/rules/chinese-listener-kotlin-collections.mdc` | No imperative loops (always on `**/*.kt`) |
| `.cursor/rules/chinese-listener-architecture.mdc` | Features, logic, structure, tests |
| `.cursor/rules/chinese-listener-android-compose.mdc` | Kotlin, Gradle, Android, Compose, themes |
| `.cursor/rules/ai-collaboration.mdc` | Substantial or multi-step work |

## Docs

**Single entry:** `@docs/WHAT_WE_DO.md` loads the **full docs bundle** (read every file in the table inside that doc, in order). Rule: `chinese-listener-docs-bundle.mdc`.

| Doc | Content |
|-----|---------|
| `docs/WHAT_WE_DO.md` | **Bundle entry** — governance, skills/rules; @-mention = all docs below |
| `docs/PROJECT.md` | Module, SDK levels, build/test, agent rule index |
| `docs/ANDROID_PRACTICES.md` | Layers, patterns, testing, links |
| `docs/CODE_AND_REFACTOR_STYLE.md` | Same refactor/collection approach as kotlin-mutliplatform-test |
| `docs/ENERGY_EFFICIENCY.md` | Battery, background work, Compose performance |
| `docs/RUN_ON_PHYSICAL_DEVICE.md` | Device install |
| `docs/PATTERNS_REFERENCE.md` | Pattern catalog |
| `docs/REFACTOR_*.md` | Refactor program |

## Skills

| Skill | Use when |
|-------|----------|
| `chinese-listener-energy-efficiency` | Throttle, thermal, sync, power impact |
| `chinese-listener-architecture` | ViewModels, repos, layers, UDF |
| `chinese-listener-android` | Manifest, Activity, OEM/OLED |
| `chinese-listener-core-workflow` | Governance & approval |
| `chinese-listener-kotlin-style` | Implement or refactor Kotlin (collections, patterns) |
| `chinese-listener-refactor` | Structural/style refactors (Phases 0–7) |

## Workflow

```
Investigate → docs bundle (@WHAT_WE_DO) → ANDROID_PRACTICES + ENERGY → Propose (layers, lifecycle, power) → User approves → Implement → testDebugUnitTest + assembleDebug → Summarize with citations
```

## Strict defaults

- **Paradigm:** idiomatic Kotlin — OOP boundaries + functional domain + coroutines/Flow
- **UI:** Compose + Material 3, app theme, opaque `colorScheme` text colors
- **Presentation:** `StateFlow` + ViewModel
- **Data:** repository pattern; new network/DB libs only with approval

## Verification

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```
