# Android practices — ChineseListener

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

Canonical reference for agents and humans. **Cursor rules enforce this strictly.**

Official anchors:

- [Guide to app architecture](https://developer.android.com/topic/architecture)
- [ViewModel overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)

---

## 1. Module layout

- **`:app`** — Activity, manifest, UI, presentation, domain, data, theme.
- Optional package map under `com.example.chineselistener` (introduce as features grow; do not scatter logic in the root package).

---

## 2. Layered architecture (mandatory direction)

Dependency arrows point **inward** (UI → presentation → domain ← data).

| Layer | Package (suggested) | Allowed | Forbidden |
|-------|---------------------|---------|-----------|
| **UI** | `…ui` | Observe state, emit events, theme | Network, DB, business rules, direct repository impl |
| **Presentation** | `…presentation` | ViewModel, `StateFlow`, map events → use cases | Raw platform I/O in composables |
| **Domain** | `…domain` | Models, use cases, **repository interfaces** | Frameworks, Compose, `android.*` |
| **Data** | `…data` | Repository impl, mappers, platform adapters | Compose, Activities |
| **Theme** | `…theme` | `AppTheme`, color/typography | Business logic |

**Composition root:** wire implementations in Activity, Application, or a dedicated `di` package (constructors first; DI libraries only with approval).

---

## 3. Paradigm: idiomatic Kotlin

| Use | For |
|-----|-----|
| **OOP-shaped** | `data class`, `sealed class`/`interface`, repository **interfaces**, small use-case classes |
| **Functional** | Immutable `val`, pure functions in domain, **`map`/`filter`/`fold`** (no imperative `for`/`while` — [CODE_AND_REFACTOR_STYLE.md](./CODE_AND_REFACTOR_STYLE.md), [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) §4, `util/KotlinLoopReplacements.kt`) |
| **Async** | **Coroutines** + **Flow** / **StateFlow** — not threads, callbacks, or `GlobalScope` |
| **UI** | Declarative Compose, **UDF**, explicit loading/error/empty/success in state |

---

## 4. Platform boundaries

1. **Domain** stays free of Android framework types.
2. **System APIs** (storage, network stack, sensors) live in **data** or **platform** behind interfaces.
3. **Share incrementally** — one feature or package at a time.

---

## 5. Presentation (default)

- **State:** `StateFlow` exposed read-only to UI.
- **ViewModel:** AndroidX `ViewModel` + `viewModelScope` (or documented alternative with injected scope).
- **UDF:** `UiState` + `UiEvent`; composables call `onEvent(...)`, never mutate domain from UI.
- **Text on OLED/dark mode:** use `MaterialTheme.colorScheme.onSurface` / `onPrimary`; DayNight window theme when applicable.

---

## 6. Data (when added)

- **HTTP:** OkHttp/Ktor + serialization — version pinned in Gradle; approval for new client libs.
- **DB:** Room or agreed store — not accessed from composables.
- **Repository pattern:** single interface in domain; impl in data.

---

## 7. Testing

| Where | What |
|-------|------|
| `app/src/test` | Domain, use cases, mappers, repositories with **fakes** |
| `app/src/androidTest` | Instrumented UI or Android adapters when needed |
| Device/emulator | Smoke UI, permissions, OEM quirks |

---

## 8. Tooling

- Versions in Gradle files (`build.gradle.kts`, `app/build.gradle.kts`).
- After changes: `./gradlew :app:assembleDebug` and `./gradlew :app:testDebugUnitTest`.
- **Detekt** / **ktlint** when CI is added — must pass.

---

## 9. Energy efficiency (engineering requirement)

- Treat **power impact** like a functional requirement: document foreground vs background work in plans.
- **Domain** owns pure policies; **data** owns I/O and platform APIs.
- **Presentation** cancels work with scope; **UI** does not poll or own long-running collectors.
- Details: [ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md), rule `chinese-listener-energy-efficiency.mdc`.

See also [PROJECT.md](./PROJECT.md), [WHAT_WE_DO.md](./WHAT_WE_DO.md), [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md).
