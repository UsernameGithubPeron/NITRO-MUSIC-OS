$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base.mk)

$(call inherit-product, device/maxwest/mx_nn62/device.mk)

PRODUCT_NAME := aosp_mx_nn62
PRODUCT_DEVICE := mx_nn62
PRODUCT_BRAND := Maxwest
PRODUCT_MODEL := NITRO N62
PRODUCT_MANUFACTURER := Maxwest

PRODUCT_SHIPPING_API_LEVEL := 31
PRODUCT_TARGET_VNDK_VERSION := 31
PRODUCT_USE_DYNAMIC_PARTITIONS := true
