# Energy efficiency — engineering practices

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

Treat efficiency as a **feature requirement**, not an afterthought.

Canonical enforcement: `.cursor/rules/chinese-listener-energy-efficiency.mdc` and skill `chinese-listener-energy-efficiency`.

Official anchors:

- [Optimize for battery](https://developer.android.com/topic/performance/power)
- [Background work overview](https://developer.android.com/develop/background-work/background-tasks)
- [Coroutines best practices](https://kotlinlang.org/docs/coroutines-guide.html)
- [Compose performance](https://developer.android.com/develop/ui/compose/performance)

---

## 1. Engineering intent

- **Foreground work** should match what the user is doing; stop or defer when not needed.
- **Idle-light** by default: no polling, no hidden background work unless approved.
- **Measure** when claiming wins: Energy Profiler, short sessions on a physical device ([RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md)).

---

## 2. Architecture (layers)

| Layer | Energy responsibility |
|-------|------------------------|
| **Domain** | Pure policies: thresholds, schedules, “should sync now?” — no Android APIs |
| **Presentation** | Lifecycle-aware collection; cancel work when UI not needed; debounce user-driven refresh |
| **Data** | Batch requests, cache, backoff; single source of truth to avoid duplicate fetches |
| **Platform adapters** | `WorkManager`, alarms, sensors — **thin** implementations behind domain ports |
| **UI** | Minimize recomposition; OLED-friendly theme; no animations that run forever |

New background capabilities (location, BLE, foreground service, `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`) require **explicit user approval** in chat and manifest review.

---

## 3. Coroutines & Flow

- **No `GlobalScope`**. Scopes tied to ViewModel, `lifecycleScope`, or injected supervisor cancelled with the feature.
- **Collect with lifecycle** (`repeatOnLifecycle`, `collectAsStateWithLifecycle` when available).
- **`distinctUntilChanged`**, **`debounce`**, **`conflate`** on hot streams where UI does not need every tick.
- **Right dispatcher:** `Dispatchers.Default` for CPU; `Dispatchers.IO` for blocking I/O; never block main.
- **Prefer cold flows** and explicit `refresh()` over infinite polling; if polling is required, document interval and stop condition in domain.

---

## 4. Compose UI

- Wrap screens in app theme; **opaque** `colorScheme` text colors on edge-to-edge/OLED.
- **Stable keys**, avoid unnecessary `remember` invalidation; hoist state to presentation.
- **No infinite `LaunchedEffect` loops** without backoff and cancellation.
- Lazy lists for long content; avoid overdraw and full-screen recompositions on timer ticks.

---

## 5. Android platform (API 28+)

- **Deferrable work:** `WorkManager` (or approved equivalent), not raw long-lived threads.
- **Avoid wake locks** unless approved; release in `finally` / lifecycle end.
- **Network:** coalesce calls; respect metered/unmetered when connectivity awareness is added.
- **Doze & App Standby:** design sync around batches; test idle behavior on physical device when adding sync.
- **Profiling:** Android Studio Energy Profiler — report if not run when claiming battery impact.

---

## 6. Testing & verification

| Check | When |
|-------|------|
| `./gradlew :app:testDebugUnitTest` | Logic, schedulers, use cases with fakes |
| `./gradlew :app:assembleDebug` | Every change |
| Idle soak (screen off, app backgrounded) | New periodic work, sync, or sensors |
| API 28+ device/emulator | Permission + OEM behavior |

---

## 7. When practices change

Update this file, `docs/WHAT_WE_DO.md`, `.cursor/rules/chinese-listener-energy-efficiency.mdc`, and skill `chinese-listener-energy-efficiency` together.
