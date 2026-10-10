# ChineseListener — Project

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

## What this is

**Single-module Android** app (`:app`) using **Jetpack Compose** for UI.

**Living governance (bundle policy, skills/rules):** [WHAT_WE_DO.md](./WHAT_WE_DO.md)  
**Architecture practices:** [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md)  
**Energy efficiency practices:** [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md)

## Non-negotiables

| Item | Value |
|------|--------|
| **Application id** | `com.example.chineselistener` |
| **Module** | `:app` only (unless user approves splits) |
| **minSdk** | **28** — `app/build.gradle.kts` |
| **targetSdk / compileSdk** | As declared in `app/build.gradle.kts` — do not change without approval |
| **Kotlin / AGP** | Pinned in root and `app` `build.gradle.kts` |

## Agent rules

Cursor rules and skills live in [`.cursor/`](../.cursor/):

- `chinese-listener-docs-bundle.mdc` — `@docs/WHAT_WE_DO.md` loads all docs in this folder
- `chinese-listener-core.mdc` — workflow, honesty, approval, git
- `chinese-listener-code-style.mdc` — Kotlin write/refactor (parity with kotlin-mutliplatform-test)
- `chinese-listener-kotlin-collections.mdc` — **no** imperative loops (always on `**/*.kt`)
- `chinese-listener-architecture.mdc` — **strict** layers, UDF, Kotlin idioms, testing
- `chinese-listener-android-compose.mdc` — Compose/Android/OLED conventions
- `chinese-listener-energy-efficiency.mdc` — battery, background work, Flow/Compose discipline
- `chinese-listener-refactor.mdc` — refactor order, playbook, audit checklist, verification
- **`docs/ANDROID_PRACTICES.md`** — canonical Android layering (agents must follow)
- **`docs/CODE_AND_REFACTOR_STYLE.md`** — collections + refactor parity with template repo
- **`docs/PATTERNS_REFERENCE.md`** — pattern name catalog
- **`docs/REFACTOR_GUIDE.md`** — strict Phases 0–7 refactor manual
- **`docs/REFACTOR_PLAYBOOK.md`** — Kotlin style, Gradle verify; **`docs/REFACTOR_AUDIT.md`** — living findings

Skills: `chinese-listener-engineering`, `chinese-listener-kotlin-style`, `chinese-listener-energy-efficiency`, `chinese-listener-architecture`, `chinese-listener-android`, `chinese-listener-refactor`, `chinese-listener-core-workflow`

## Documentation readiness

| Area | Source of truth |
|------|-----------------|
| Governance & bundle | [WHAT_WE_DO.md](./WHAT_WE_DO.md) |
| Layers & packages | [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) |
| Power | [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md) |
| Device test | [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md) |
| Agent entry | `@docs/WHAT_WE_DO.md` + `.cursor/rules/` + `.cursor/skills/chinese-listener-engineering/` |

## Build & test

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

Windows:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:testDebugUnitTest
```

## References

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android app architecture](https://developer.android.com/topic/architecture)
- [Android API 28 (Pie)](https://developer.android.com/tools/releases/platforms#9)
