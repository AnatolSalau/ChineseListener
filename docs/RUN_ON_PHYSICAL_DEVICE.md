# Run ChineseListener on a physical Android phone (IntelliJ / Gradle)

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

## 1. Phone setup (all brands)

1. **Developer options** → enable **USB debugging**.  
   https://developer.android.com/studio/debug/dev-options
2. Connect USB → accept **Allow USB debugging** on the phone.
3. PC check:
   ```powershell
   adb devices
   ```
   Must show your device as `device` (not `unauthorized`).

## 2. IntelliJ IDEA / Android Studio

1. Open project `ChineseListener`.
2. **Gradle sync** after any `build.gradle.kts` change.
3. Run configuration: **app** → device = your phone → **Run**.

Or terminal:

```powershell
cd E:\Projects\ChineseListener
.\gradlew.bat :app:installDebug
```

APK path after build: `app\build\outputs\apk\debug\app-debug.apk`

## 3. Xiaomi / MIUI — `INSTALL_FAILED_VERIFICATION_FAILURE`

If Gradle reports **INSTALL_FAILED_VERIFICATION_FAILURE**:

1. **Developer options** (MIUI):
   - **USB debugging** — on
   - **USB debugging (Security settings)** / **Install via USB** — on
2. **Settings → Additional settings → Developer options** → turn off **MIUI optimization** (reboot if asked), then retry.
3. **Security / Mi Security** → disable **Verify installations** for USB installs if available.
4. Uninstall any old test build, then retry:
   ```powershell
   adb uninstall com.example.chineselistener
   .\gradlew.bat :app:installDebug
   ```
5. Manual install:
   ```powershell
   adb install -r app\build\outputs\apk\debug\app-debug.apk
   ```

## 4. IDE shows only `Build cancelled`

Usually **not** a compile error:

- You clicked **Stop**, or a second Run started while Gradle was busy.
- **Gradle sync** was invalidated after editing `build.gradle.kts` — sync, wait, Run again.
- Run `installDebug` from the terminal once to confirm the build works.

## 5. Permissions

1. Grant runtime permissions when the app requests them.
2. If denied: fix in **Settings → Apps → ChineseListener → Permissions**, then retest.

## 6. Device smoke test (manual)

On a physical **API 28+** device after installing debug build:

1. Launch app — no crash on cold start.
2. Exercise primary navigation / main screen flow defined by the product (update this section when UX stabilizes).
3. Background the app 1 minute — no unexpected foreground service or sustained high CPU (profile if unsure).
4. Optional: leave app idle with screen off — confirm no unapproved periodic work.

## 7. Requirements

- Phone **Android 9+** (API 28+) — matches project `minSdk` in `app/build.gradle.kts`.
