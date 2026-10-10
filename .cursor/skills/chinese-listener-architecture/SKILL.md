---
name: chinese-listener-architecture
description: Strict layers, UDF/MVVM, domain/data boundaries, coroutines, testing. Use when adding features, ViewModels, repositories, or refactoring app code in ChineseListener.
---

# Architecture (strict)

## Read first

1. `docs/ANDROID_PRACTICES.md` — canonical practices
2. `docs/ENERGY_EFFICIENCY.md` — Flow, background boundaries
3. `.cursor/rules/chinese-listener-architecture.mdc` — enforceable rules
4. `.cursor/rules/chinese-listener-energy-efficiency.mdc` — power impact
5. `.cursor/rules/chinese-listener-android-compose.mdc` — UI/Android/OLED

## Quick layer map

```
ui (Compose) → presentation (ViewModel, StateFlow) → domain (use cases, repo interfaces)
                                                      ↑
                                                   data (network, DB, impl)
platform: Android system adapters behind ports
```

## Must

- `sealed` / `data class` for state and errors
- UDF: state down, events up
- Unit tests for domain/presentation
- App theme + scheme text colors on all screens
- Lifecycle-scoped async; side effects in data/platform, not composables

## Must not

- `GlobalScope`, business logic in composables, domain importing Android/Compose
- New deps without approval + Gradle update

## Verify

`./gradlew :app:testDebugUnitTest` and `:app:assembleDebug`
