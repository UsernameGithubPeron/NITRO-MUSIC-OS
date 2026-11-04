# NITRO N62 Vendor Library Inventory

Device: Maxwest NITRO N62
Device ID: MX-NN62WW01
Userspace: 32-bit ARM (armeabi-v7a)

## Graphics libraries

All four recovered libraries are 32-bit ARM ELF files.

## SHA-256 checksums

cf76b6b22cb77378452e4216e7ffab82b87cf00b1cfb4f0636921fe469724faf  vendor/stock/hw/android.hardware.graphics.mapper@4.0-impl-mediatek.so
4728a9738846a58849496c5134adf4a9327da173f3e860e2a1f5e5798756b3b9  vendor/stock/hw/android.hardware.renderscript@1.0-impl.so
83b0c40508dc073d38daa3b38a5d0992b65a84f2f36d2cb4d41a2aecb5a2fb77  vendor/stock/hw/gralloc.default.so
85fef04a13613e9be80fc3a43458100755e58c17c7f9e1526551872f0cdd9a18  vendor/stock/hw/gralloc.rogue.so

## Confirmed stock graphics hardware

GPU: Imagination Technologies PowerVR Rogue GE8100
OpenGL ES: 3.2
EGL: 1.4
Graphics backend: MediaTek (mtk)
Hardware composer: mtk_common, version 2.1
Graphics allocator HAL: android.hardware.graphics.allocator@4.0
Graphics mapper HAL: android.hardware.graphics.mapper@4.0
Composer HAL: android.hardware.graphics.composer@2.1

Preserve the matching stock PowerVR and MediaTek graphics components.
Do not substitute generic graphics drivers without compatibility testing.
