//package com.amuse.animalsounds.utils.ext
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.util.Log
//import android.widget.ImageView
//import androidx.core.content.ContextCompat
//import com.amuse.animalsounds.BuildConfig
//import com.amuse.animalsounds.utils.GlideApp
//import com.bumptech.glide.load.DataSource
//import com.bumptech.glide.load.engine.GlideException
//import com.bumptech.glide.request.RequestListener
//import com.bumptech.glide.request.target.Target
//import com.google.firebase.crashlytics.FirebaseCrashlytics
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.FileNotFoundException
//import java.io.IOException
//import java.io.InputStream
//import java.net.HttpURLConnection
//import java.net.URL
//import javax.net.ssl.HttpsURLConnection
//import javax.net.ssl.SSLContext
//import javax.net.ssl.TrustManager
//import javax.net.ssl.X509TrustManager
//
//
///**
// * Set Image Glide width, height auto image input
// */
//
//fun Context.setImageNotPlaceholder(image: ImageView, input: Int) {
//    GlideApp.with(this)
//        .load(input)
//        .override(image.width, image.height)
//        .centerCrop()
//        .into(image)
//}
//
///**
// * Set Image Glide width, height auto image input
// */
//
//fun Context.setImage(image: ImageView, input: Int, isFailed: ((Boolean) -> Unit)? = null) {
//    GlideApp.with(this)
//        .load(input)
//        .override(image.width, image.height)
//        .placeholder(com.amuse.animalsounds.R.drawable.image_empty)
//        .listener(object : RequestListener<Drawable?> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable?>,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(true)
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable?>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(false)
//                return false
//            }
//
////            override fun onResourceReady(
////                resource: Drawable,
////                model: Any,
////                target: Target<Drawable?>?,
////                dataSource: DataSource,
////                isFirstResource: Boolean,
////            ): Boolean {
////                isFailed?.invoke(false)
////                return false
////            }
//        })
//        .into(image)
//}
//
//fun Context.setImage(image: ImageView, input: String, isFailed: ((Boolean) -> Unit)? = null) {
////    Log.e("setImage", "input: $input")
//    val drawable = getDrawableFromName(input)
//
//    GlideApp.with(this)
//        .load(drawable)
//        .override(image.width, image.height)
//        .placeholder(com.amuse.animalsounds.R.drawable.image_empty)
//        .listener(object : RequestListener<Drawable?> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable?>,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(true)
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable?>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(false)
//                return false
//            }
////
////            override fun onResourceReady(
////                resource: Drawable,
////                model: Any,
////                target: Target<Drawable?>?,
////                dataSource: DataSource,
////                isFirstResource: Boolean,
////            ): Boolean {
////                isFailed?.invoke(false)
////                return false
////            }
//        })
//        .into(image)
//}
//
//fun loadImageFromUrl(imageUrl: String, imageView: ImageView) {
//    disableCertificateValidation()
//    GlobalScope.launch(Dispatchers.IO) {
//        try {
//            val url = URL(imageUrl)
//            val connection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val input: InputStream = connection.inputStream
//            val bitmap = BitmapFactory.decodeStream(input)
//
//            // Update UI on the main thread
//            GlobalScope.launch(Dispatchers.Main) {
//                imageView.setImageBitmap(bitmap)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}
//
//private fun disableCertificateValidation() {
//    val trustAllCertificates: Array<TrustManager> = arrayOf(
//        object : X509TrustManager {
//            override fun checkClientTrusted(
//                chain: Array<java.security.cert.X509Certificate?>?,
//                authType: String?,
//            ) {
//            }
//
//            override fun checkServerTrusted(
//                chain: Array<java.security.cert.X509Certificate?>?,
//                authType: String?,
//            ) {
//            }
//
//            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
//                return arrayOf()
//            }
//        }
//    )
//
//    try {
//        val sslContext = SSLContext.getInstance("SSL")
//        sslContext.init(null, trustAllCertificates, java.security.SecureRandom())
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
//        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun Context.setImageUrl(
//    image: ImageView,
//    input: String,
//    imagePlaceHolder: Int = com.amuse.animalsounds.R.drawable.image_empty,
//    isFailed: ((Boolean) -> Unit)? = null,
//) {
//    Log.e("Picasso", "input: $input")
//
//    disableCertificateValidation()
////    Picasso.get()
////        .load(input)
////        .placeholder(R.drawable.image_empty)
//////        .error(R.drawable.user_placeholder_error)
////        .into(image);
//
//
//    GlideApp.with(this)
//        .load(input)
//        .override(image.width, image.height)
//        .placeholder(imagePlaceHolder)
//        .listener(object : RequestListener<Drawable?> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable?>,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(true)
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable?>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean,
//            ): Boolean {
//                isFailed?.invoke(false)
//                return false
//            }
//        })
//        .into(image)
//}
//
//
///**
// * Set Image Glide width, height auto image input
// */
//
//fun Context.setImage(
//    image: ImageView,
//    input: String?,
//    placeholder: Int = com.amuse.animalsounds.R.drawable.image_empty,
//    isFailed: ((Boolean) -> Unit)? = null,
//) {
////    try {
////        GlideApp.with(this)
////            .load(input)
////            .override(image.width, image.height)
////            .placeholder(placeholder)
////            .listener(object : RequestListener<Drawable?> {
//////                override fun onResourceReady(
//////                    resource: Drawable,
//////                    model: Any,
//////                    target: Target<Drawable?>?,
//////                    dataSource: com.bumptech.glide.load.DataSource,
//////                    isFirstResource: Boolean,
//////                ): Boolean {
//////                    isFailed?.invoke(false)
//////                    return false
//////                }
////
////                override fun onLoadFailed(
////                    e: GlideException?,
////                    model: Any?,
////                    target: Target<Drawable?>,
////                    isFirstResource: Boolean,
////                ): Boolean {
////                    isFailed?.invoke(true)
////                    return false
////                }
////
//////                override fun onResourceReady(
//////                    resource: Drawable?,
//////                    model: Any?,
//////                    target: Target<Drawable?>?,
//////                    dataSource: DataSource?,
//////                    isFirstResource: Boolean,
//////                ): Boolean {
//////                    isFailed?.invoke(false)
//////                    return false
//////                }
////
////                override fun onResourceReady(
////                    resource: Drawable,
////                    model: Any,
////                    target: Target<Drawable?>?,
////                    dataSource: DataSource,
////                    isFirstResource: Boolean,
////                ): Boolean {
////                    isFailed?.invoke(false)
////                    return false
////                }
////            })
////            .into(image)
////    } catch (e: Exception) {
////        e.printStackTrace()
////    }
//}
//
///**
// * Set Image Glide width, height auto image input
// */
//
//fun Context.setImage(
//    image: ImageView,
//    input: String?,
//    placeholderString: String,
//    isFailed: ((Boolean) -> Unit)? = null,
//) {
////    val placeholder = getDrawableFromName(placeholderString)
////    try {
////        GlideApp.with(this)
////            .load(input)
////            .override(image.width, image.height)
//////            .placeholder(placeholder)
////            .listener(object : RequestListener<Drawable?> {
////                override fun onResourceReady(
////                    resource: Drawable,
////                    model: Any,
////                    target: Target<Drawable?>?,
////                    dataSource: com.bumptech.glide.load.DataSource,
////                    isFirstResource: Boolean,
////                ): Boolean {
////                    isFailed?.invoke(false)
////                    return false
////                }
////
////                override fun onLoadFailed(
////                    e: GlideException?,
////                    model: Any?,
////                    target: Target<Drawable?>,
////                    isFirstResource: Boolean,
////                ): Boolean {
////                    isFailed?.invoke(true)
////                    return false
////                }
////
//////                override fun onResourceReady(
//////                    resource: Drawable?,
//////                    model: Any?,
//////                    target: Target<Drawable?>?,
//////                    dataSource: DataSource?,
//////                    isFirstResource: Boolean,
//////                ): Boolean {
//////                    isFailed?.invoke(false)
//////                    return false
//////                }
////            })
////            .into(image)
////    } catch (e: Exception) {
////        e.printStackTrace()
////    }
//}
//
//fun Context.getDrawableFromName(name: String): Drawable {
//    val uri =
//        Uri.parse("android.resource://${packageName}/drawable/$name")
//
//    var drawable: Drawable?
//    try {
//        val inputStream = contentResolver.openInputStream(uri)
//        drawable = Drawable.createFromStream(inputStream, uri.toString())
//    } catch (e: FileNotFoundException) {
//        drawable = ContextCompat.getDrawable(this, com.amuse.animalsounds.R.drawable.image_empty)
//    }
//    return drawable!!
//}
//
//fun Context.getBitmapFromName(input: String): Bitmap {
//
//    val drawable = getDrawableFromName(input)
//
//    if (drawable is BitmapDrawable) {
//        return drawable.bitmap
//    }
//
//    // Nếu không phải là BitmapDrawable, chuyển đổi thành Bitmap mới
//    val width = drawable.intrinsicWidth
//    val height = drawable.intrinsicHeight
//    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//    // Tạo một canvas mới và vẽ Drawable lên đó
//    // Lưu ý: bạn cần khởi tạo một canvas mới với bitmap để vẽ drawable lên đó.
//    // Nếu không, drawable có thể không được vẽ đúng cách.
//    // Ví dụ:
//    // Canvas canvas = new Canvas(bitmap);
//    // drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//    // drawable.draw(canvas);
//    return bitmap
//}
//
//fun Context.checkDrawableExistByName(nameFile: String): Boolean {
//    val uri =
//        Uri.parse("android.resource://${packageName}/drawable/$nameFile")
//    return uri == null
//}
//
//fun getIcId(resN: String, c: Class<*>): Int {
//    try {
//        val idField = c.getDeclaredField(resN)
//        return idField.getInt(idField)
//    } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//        return 0
//    }
//}
//
///**
// * Set setImageRadius Glide width, height auto image input
// */
//
//fun Context.setImageRadius(image: ImageView, input: String?) {
////    GlideApp.with(this)
////        .asBitmap()
////        .load(input)
////        .centerCrop()
////        .into(object : BitmapImageViewTarget(image) {
////            override fun setResource(resource: Bitmap?) {
////                val circularBitmapDrawable =
////                    RoundedBitmapDrawableFactory.create(this@setImageRadius.resources, resource)
////                circularBitmapDrawable.isCircular = true
////                image.setImageDrawable(circularBitmapDrawable)
////            }
////        })
//}
//
//fun Context.setImageAssets(image: ImageView, imageName: String) {
//    var istr: InputStream? = null
//    try {
//        istr = assets.open(imageName)
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    val drawable = BitmapFactory.decodeStream(istr)
////        Log.e("setImage", " " + drawable.width + " " + drawable.height)
//    image.setImageBitmap(drawable)
//}
//
//fun getDrawable(context: Context, id: Int, imageName: String): Drawable {
//    var drawable: Drawable? = null
//    try {
//        drawable = ContextCompat.getDrawable(context, id)
//    } catch (e: Exception) {
//        FirebaseCrashlytics.getInstance().log("imageName error: $imageName")
//
//        val bm = BitmapFactory.decodeResource(context.resources, id)
//        val width = context.resources.getDimension(com.intuit.sdp.R.dimen._234sdp).toInt()
//        val height = context.resources.getDimension(com.intuit.sdp.R.dimen._426sdp).toInt()
//        val d = BitmapDrawable(
//            context.resources,
//            Bitmap.createScaledBitmap(bm, width, height, true)
//        )
//        drawable = d
//        e.printStackTrace()
//    }
//    return drawable!!
//}
//
//fun bitmapFromResource(
//    context: Context,
//    resId: Int,
////    reqWidth: Int,
////    reqHeight: Int
//): Bitmap {
//    // First decode with inJustDecodeBounds=true to check dimensions
//    return BitmapFactory.Options().run {
//        val width = context.resources.getDimension(com.intuit.sdp.R.dimen._234sdp).toInt()
//        val height = context.resources.getDimension(com.intuit.sdp.R.dimen._426sdp).toInt()
//
//        inJustDecodeBounds = true
//        BitmapFactory.decodeResource(context.resources, resId, this)
//
//        // Calculate inSampleSize
//        inSampleSize = calculateInSampleSize(this, width, height)
//
//        // Decode bitmap with inSampleSize set
//        inJustDecodeBounds = false
//        BitmapFactory.decodeResource(context.resources, resId, this)
//    }
//}
//
//
//fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
//    // Raw height and width of image
//    val (height: Int, width: Int) = options.run { outHeight to outWidth }
//    var inSampleSize = 1
//
//    if (height > reqHeight || width > reqWidth) {
//
//        val halfHeight: Int = height / 2
//        val halfWidth: Int = width / 2
//
//        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//        // height and width larger than the requested height and width.
//        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
//            inSampleSize *= 2
//        }
//    }
//
//    return inSampleSize
//}
//
//
//@SuppressLint("DiscouragedApi")
//fun Context.getIdByNameRes(name: String, defType: String = "drawable"): Int {
//    val imageResource = resources.getIdentifier(name, defType, BuildConfig.APPLICATION_ID)
//    return imageResource
//}