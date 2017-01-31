package no.mesan.faghelg.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;

public class ImageService {

    public static final int MAX_SIZE = 1024;
    private final Context context;

    public ImageService(Context context) {
        this.context = context;
    }

    public Bitmap getScaledBitmapFromUri(Uri selectedImageUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(selectedImageUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            // Determine how much to scale down the image
            int scaleFactor = Math.min(MAX_SIZE / photoW, photoH / MAX_SIZE);
            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            Bitmap resizedImage = resizeBitmap(image);
            if (resizedImage != image) {
                image.recycle();
            }
            Bitmap rotatedImage = rotateBitmap(resizedImage);
            if (rotatedImage != resizedImage) {
                resizedImage.recycle();
            }
            parcelFileDescriptor.close();
            return rotatedImage;
        } catch (Exception ex) {
            //TODO Handle exception
        }
        return null;
    }
    private Bitmap rotateBitmap(Bitmap resizedImage) {
        final int width = resizedImage.getWidth();
        final int height = resizedImage.getHeight();
        Matrix matrix = new Matrix();
        int angle = width > height ? -90 : 0;
        matrix.postRotate(angle);
        return Bitmap.createBitmap(resizedImage, 0, 0, width, height, matrix, true);
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = MAX_SIZE;
        int newHeight = Math.round(((float) MAX_SIZE / width) * (float) height);
        if (height > width) {
            newWidth = Math.round(((float) MAX_SIZE / height) * (float) width);
            newHeight = MAX_SIZE;
        }
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    public String encodeBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
}
