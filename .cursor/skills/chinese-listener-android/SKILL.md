---
name: chinese-listener-android
description: Android shell — MainActivity, themes, edge-to-edge OLED, permissions, device deploy. Use when editing app module, manifest, res/themes, or physical device testing.
---

# Android shell

Read `.cursor/rules/chinese-listener-android-compose.mdc`, `docs/ENERGY_EFFICIENCY.md` (background APIs, Doze), and `docs/RUN_ON_PHYSICAL_DEVICE.md`.

**`:app`** holds Activity, manifest, and feature code — keep layers clear per `chinese-listener-architecture`.

## Verify

`./gradlew :app:assembleDebug`

Install: `:app:installDebug` (Windows: `.\gradlew.bat :app:installDebug`)
