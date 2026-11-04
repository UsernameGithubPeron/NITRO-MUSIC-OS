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

## Add to an AOSP Android 12 source tree
Copy this directory to:
`packages/apps/NITROLauncher`

Then add `NITROLauncher` to the product package list in the device product makefile:
`PRODUCT_PACKAGES += NITROLauncher`

Build it with the normal AOSP product build. The Android.bp file uses platform signing
and privileged installation for a ROM integration. Review permissions and signing
policy for the final product before release.

## Important limitations
- This is a first UI prototype, not a complete ROM.
- App intents open whatever compatible apps exist on the system. Dedicated NITRO
  Music, Camera, Gallery and Files apps still need to be built.
- Camera image quality depends on the NITRO N62's camera HAL, sensor and tuning data.
- It has not yet been compiled or tested on the NITRO N62.
- Do not flash this prototype or any ROM image until the exact device build,
  partition layout, boot chain and recovery path are verified.
