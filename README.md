# Waxd Launcher

A dark, phosphor-green terminal/ops-console home screen for Android — the first app in the **waxd** lifestyle suite.

Waxd Launcher is a themed fork of [Fossify Launcher](https://github.com/FossifyOrg/Launcher) (GPL-3.0). All launcher functionality is upstream's; this fork changes the identity and the look:

- Package id `com.waxd.launcher`
- Near-black base (`#0A0C0A`) with a muted phosphor green (`#2E9E5B`) primary and a brighter phosphor accent (`#4CFF8B`) for interactive states
- Material You (dynamic color) off by default so the palette shows on Android 12+; the user can still switch it on in Settings → Customize colors
- App name: **Waxd**

Everything else — home screen grid, app drawer, widgets, hidden icons, gestures — is straight from upstream.

## The waxd theme system

Every waxd reskin overrides the same four `fossify-commons` resource names in its own `app/src/main/res/values/colors.xml`:

```xml
<color name="default_background_color">@color/waxd_background</color>
<color name="default_text_color">@color/waxd_text</color>
<color name="default_primary_color">@color/waxd_primary</color>
<color name="default_accent_color">@color/waxd_accent</color>
```

Android gives an app module's resources priority over a library dependency's resources of the same name, so this works without forking `commons` — it just shadows the library's defaults. Copy this block (with the same `waxd_*` hex values) into any new waxd reskin to keep the whole family in sync; change the four hex values once, here, to push a palette revision everywhere later.

Because Fossify apps default to Android 12+ Material You dynamic color, every waxd reskin also needs a one-time override to keep the custom palette from being replaced by the system's wallpaper-based colors — see `helpers/Config.kt` (`applyWaxdThemeDefaultsIfNeeded`) and the `WaxdApp` Application class below.

## Building

Requires JDK 17+ and the Android SDK. Locally:

```
./gradlew assembleFossRelease
```

The APK lands in `app/build/outputs/apk/foss/release/`. For a signed release APK, put a keystore at the repo root and copy `keystore.properties_sample` to `keystore.properties` with the real values (see [cypher-messages](https://github.com/splashieee7/cypher-messages) for the CI/signing-secrets pattern this suite follows).

Keep the keystore safe. Android only allows updates signed with the same key.

## Keeping up with upstream

This fork stays close to upstream so rebases are cheap. The changes live in:

- `gradle.properties` — `APP_ID`, version
- `app/src/main/res/values/colors.xml` — the palette (shadows Commons defaults)
- `app/src/main/res/values/strings.xml` — `app_launcher_name` (home screen label)
- `app/src/main/res/values/donottranslate.xml` — `app_name` (About screen)
- `app/src/main/kotlin/com/waxd/launcher/WaxdApp.kt` — Application class, disables Material You default
- `app/src/main/kotlin/com/waxd/launcher/helpers/Config.kt` — `applyWaxdThemeDefaultsIfNeeded()`
- `app/src/main/AndroidManifest.xml` — `android:name` points at `WaxdApp`
- Kotlin package moved from `org.fossify.home` to `com.waxd.launcher` (the project uses `android.nonTransitiveRClass`, so the source package must match `namespace`/`APP_ID` or the generated `R` class won't resolve)

To pull upstream changes:

```
git remote add upstream https://github.com/FossifyOrg/Launcher.git
git fetch upstream
git merge upstream/main
```

then re-apply the package rename if new files were added under `org.fossify.home`, and re-check the other files above if upstream touched them.

## License

GPL-3.0, same as upstream. See [LICENSE](LICENSE).
