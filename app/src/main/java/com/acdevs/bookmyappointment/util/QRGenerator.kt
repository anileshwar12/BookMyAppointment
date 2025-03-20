package com.acdevs.bookmyappointment.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

object QRCodeGenerator {
    fun generateQRCode(data: String): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}
