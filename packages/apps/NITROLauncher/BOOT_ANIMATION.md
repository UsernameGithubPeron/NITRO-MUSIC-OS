# NITRO MUSIC OS startup screen

Add the supplied animation at `src/main/assets/nitro_boot.gif`.

The launcher startup screen draws the GIF centered on a pure black background and
preserves its aspect ratio. It transitions to the home screen after about 2.6 seconds.
If the GIF is missing, it shows a built-in NITRO MUSIC OS wordmark instead.

## Android 12 Go limitation

This is shown when Android opens NITRO as the selected Home/launcher app. It cannot
replace the manufacturer's animation before Android starts. That requires a compatible
device-specific system image or privileged/root access. Device Admin permission does
not grant that capability; this project does not request Device Admin or root access.

Keep the GIF reasonably small (ideally a few MB) for a device with 2 GB RAM.
