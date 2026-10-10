# What we do

This document is the **human-owned narrative** for this repo: how we work with Cursor agents and how documentation stays organized. **Product features and mission** are defined by the project owner separately — this file does not describe app functionality.

## Docs bundle (use `@docs/WHAT_WE_DO.md`)

When you **attach or @-mention** `docs/WHAT_WE_DO.md` in chat, that means: **load and follow the entire `docs/` bundle**, not only this file.

Agents **must** read every project doc below **in this order** before planning or implementing (use the Read tool if a file is not already in context):

| Order | File | Role |
|------:|------|------|
| 1 | **WHAT_WE_DO.md** (this file) | Governance, bundle index, skills/rules policy |
| 2 | [PROJECT.md](./PROJECT.md) | Module, SDK levels, build/test, agent rule index |
| 3 | [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) | Layers, UDF, Kotlin/Compose patterns, testing |
| 4 | [CODE_AND_REFACTOR_STYLE.md](./CODE_AND_REFACTOR_STYLE.md) | **Kotlin write/refactor parity** with kotlin-mutliplatform-test (collections, phases) |
| 5 | [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md) | Battery, background work, Compose performance |
| 6 | [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md) | USB debugging, install, device smoke |
| 7 | [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) | Pattern catalog: SOLID, DRY, architecture, GoF, algorithms |
| 8 | [REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md) | **Strict refactor manual** — Phases 0–7 |
| 9 | [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) | Kotlin style matrix, Gradle verify; audit: [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md) |

**Not part of the bundle:** non-markdown files under `docs/` (e.g. `*.code-workspace`). When you add a new `docs/*.md`, **add it to this table** and add a one-line pointer at the top of that file back to this bundle.

Humans: prefer a single `@docs/WHAT_WE_DO.md` instead of attaching multiple doc files.

---

## How we work in this repo

1. **One app module** — treat `:app` as a single Android product; use package layers instead of duplicating logic across random packages.
2. **Energy impact in every plan** — state foreground vs background work, frequency, cancellation, and how we verify (see [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md)).
3. **Approval before substantial change** — agents propose plan, layers, risks, and verification; you approve before multi-file or dependency work (see `.cursor/rules/chinese-listener-core.mdc`).
4. **Verify honestly** — prefer `./gradlew :app:testDebugUnitTest` and `./gradlew :app:assembleDebug`; report what was not run.
5. **Citations** — claims about code use `startLine:endLine:path`; external facts use full URLs.

---

## Skills and rules (mandatory)

Agents and contributors **must** use this repo’s Cursor **rules** and **skills** as the default way of working.

### Always load (in order)

| Priority | Location | Role |
|----------|----------|------|
| 1 | `.cursor/rules/chinese-listener-core.mdc` | Approval, honesty, git (never push), citations |
| — | `.cursor/rules/chinese-listener-docs-bundle.mdc` | When you **`@docs/WHAT_WE_DO.md`**: read full **docs bundle** |
| 2 | `.cursor/rules/chinese-listener-energy-efficiency.mdc` | Battery, background work, Flow/Compose discipline |
| 3 | `.cursor/rules/chinese-listener-code-style.mdc` | **Kotlin write/refactor** — patterns, collections, REFACTOR_GUIDE alignment |
| — | `.cursor/rules/chinese-listener-kotlin-collections.mdc` | **No `for`/`while`** (always on `**/*.kt`) |
| 4 | `.cursor/rules/chinese-listener-architecture.mdc` | Layers, UDF, Kotlin idioms, tests |
| 5 | `.cursor/rules/chinese-listener-android-compose.mdc` | Android, Compose, API levels, OLED/edge-to-edge |
| 6 | `.cursor/rules/ai-collaboration.mdc` | Checklist for substantial tasks |
| 7 | `.cursor/skills/chinese-listener-engineering/SKILL.md` | Entry skill — links rules, docs, other skills |
| 8 | Task-specific skills | `chinese-listener-kotlin-style`, `chinese-listener-energy-efficiency`, `chinese-listener-architecture`, `chinese-listener-android`, `chinese-listener-refactor`, `chinese-listener-core-workflow` |

At the start of a task, the agent should **read the relevant skill(s) and rules**, not improvise from generic Android advice.

### Keep skills and rules aligned

When stack or conventions change:

1. **Update this file** — bundle table and governance sections.
2. **Update** [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md) and `chinese-listener-energy-efficiency` when efficiency policies change.
3. **Update the rules** (`.cursor/rules/*.mdc`) when behavior or policy changes.
4. **Update the skills** (`.cursor/skills/**/SKILL.md`) when workflows or paths change — especially `chinese-listener-engineering`.
5. **Update** [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) and [PROJECT.md](./PROJECT.md) when architecture or toolchain facts change.
6. In chat, **state what was updated** so the next session does not follow stale instructions.

Do **not** leave agents following rules that contradict the codebase. Either change the code to match the rules, or change the rules/skills to match the approved direction — **never both silently diverging**.

### When to edit what

| Change | Touch |
|--------|--------|
| Agent governance, bundle index | This file |
| Efficiency, thermal, background APIs | `ENERGY_EFFICIENCY.md` + `chinese-listener-energy-efficiency` |
| New feature area (layers) | `chinese-listener-architecture` skill + `ANDROID_PRACTICES.md` |
| Kotlin style / collections / new feature code | `chinese-listener-kotlin-style` + `CODE_AND_REFACTOR_STYLE.md` + `chinese-listener-code-style.mdc` |
| Full-app refactor / style pass | `chinese-listener-refactor` skill + `PATTERNS_REFERENCE.md` + `REFACTOR_GUIDE.md` + `REFACTOR_PLAYBOOK.md` + `REFACTOR_AUDIT.md` |
| Android shell, themes, device deploy | `chinese-listener-android` skill + `chinese-listener-android-compose.mdc` |
| New dependency or SDK bump | `PROJECT.md`, Gradle files, and rules (with explicit approval) |
| New agent workflow (e.g. CI gate) | `chinese-listener-core-workflow` skill + `ai-collaboration.mdc` |

---

## References

- [PROJECT.md](./PROJECT.md) — module, SDK, build commands
- [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) — canonical layers and patterns
- [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md) — battery, CPU, network, Compose performance
- [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md) — device install
- [Android — Optimize for battery](https://developer.android.com/topic/performance/power)
