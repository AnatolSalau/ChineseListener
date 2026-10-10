---
name: chinese-listener-energy-efficiency
description: Energy efficiency — throttle, thermal, battery, background work, Flow/coroutine discipline, Compose performance. Use for sync, sensors, media, or any work affecting power/CPU/network in ChineseListener.
---

# Energy efficiency skill

## Read first

1. `docs/WHAT_WE_DO.md` — governance
2. `docs/ENERGY_EFFICIENCY.md` — canonical practices
3. `.cursor/rules/chinese-listener-energy-efficiency.mdc` — enforceable rules
4. `.cursor/rules/chinese-listener-architecture.mdc` — where logic lives

## Before implementing

Answer in the plan (user approval required for non-trivial work):

- What runs **only while screen visible** vs **in background**?
- **Frequency** and **stop condition** for any periodic work
- **Cancellation** path (ViewModel cleared, lifecycle, WorkManager constraints)
- **Verification**: unit tests + Gradle tasks + idle/device test if background

## Defaults

| Area | Default |
|------|---------|
| Refresh | User action or explicit pull-to-refresh pattern |
| Sync | Batched, backoff, domain decides eligibility |
| UI updates | `StateFlow` + minimal recomposition |
| Android deferral | WorkManager adapter behind domain port |
| Theme | Dark/OLED-safe via app theme |

## Must not

- `GlobalScope`, composable-owned infinite collectors, repository calls from UI
- Battery whitelist or wake locks without approved scope

## Keep in sync

When efficiency policies change, update **this skill**, `chinese-listener-energy-efficiency.mdc`, `ENERGY_EFFICIENCY.md`, and `WHAT_WE_DO.md` in the same change set.

## Verify

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```
