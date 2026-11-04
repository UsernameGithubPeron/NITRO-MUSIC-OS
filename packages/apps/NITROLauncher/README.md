# NITRO GLASS UI — first ROM component

This is the first native Android home-screen component for NITRO MUSIC OS.
It is an AOSP/Soong `android_app` system app, not a third-party launcher.

## Current implementation
- Android HOME activity
- Original dark, glass-inspired home screen
- Low-cost alpha surfaces and gradients; no continuous real-time blur
- Music, Camera, Photos, Files, Settings and Phone launch intents
- Hardware-accelerated Canvas drawing
- No external libraries or network permissions
- Optional camera hardware declaration so devices without cameras can still install the launcher

## Camera and picker behavior
- Check `FEATURE_CAMERA_ANY` before opening the camera.
- Try the still-image camera intent, then the capture intent if needed.
- If no camera hardware or compatible camera app is available, show a message and stop.
- The camera path never opens the Files picker as a fallback.
- Files is the only launcher action that opens the document picker.
- Photos uses an image-only content selection intent.

## Build setup

The Gradle project is under this directory and targets Android API 35 with a minimum
Android API 31. The AOSP Soong module uses the source and resource folders under
`src/main`.

### Standalone debug APK
From the repository root:

```sh
gradle -p packages/apps/NITROLauncher assembleDebug
```

The GitHub Actions workflow builds and uploads the debug APK on relevant pushes,
pull requests, or manual dispatches.

### Add to an AOSP Android 12 source tree
Copy this directory to:
`packages/apps/NITROLauncher`

Then add `NITROLauncher` to the product package list in the device product makefile:
`PRODUCT_PACKAGES += NITROLauncher`

Build it with the normal AOSP product build. The Android.bp file uses platform signing
and privileged installation for a ROM integration. Review permissions and signing
policy for the final product before release.

## Device target and limitations
The repository's device scaffold identifies the Maxwest NITRO N62 (MX-NN62WW01):
Android 12 / API 31, MediaTek MT6739, ARMv7 userspace, 2 GiB RAM and a 720x1520
display. Keep effects lightweight for this hardware.

This launcher remains a first UI prototype, not a complete ROM:
- App intents open compatible apps that already exist on the system.
- Dedicated NITRO Music, Camera, Gallery and Files apps still need to be built.
- Arti AI is not implemented yet. It needs a provider choice and a secure backend
  proxy; provider credentials must not be embedded in the APK or committed to Git.
- Camera image quality depends on the NITRO N62 camera HAL, sensor and tuning data.
- Changes in this branch have not yet been compiled or tested on the physical NITRO N62.

Do not flash this prototype or any ROM image until the exact device build, partition
layout, boot chain and recovery path are verified.
