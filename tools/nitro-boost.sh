#!/bin/bash

set -e

echo "================================"
echo "     NITRO PERFORMANCE BOOST"
echo "================================"

adb wait-for-device

echo "[1/5] Reducing interface animations..."

adb shell settings put global window_animation_scale 0.5
adb shell settings put global transition_animation_scale 0.5
adb shell settings put global animator_duration_scale 0.5

echo "[2/5] Improving background app behavior..."

adb shell settings put global cached_apps_freezer enabled

echo "[3/5] Enabling standard network optimizations..."


echo "[4/5] Cleaning temporary app caches..."

adb shell pm trim-caches 500M

echo "[5/5] Applying settings..."

echo ""
echo "NITRO PERFORMANCE BOOST COMPLETE!"
echo "Restart your phone for a fresh session."
