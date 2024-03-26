//package com.boikinhdich.quekinhdich.data
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.res.AssetManager
//import android.media.MediaPlayer
//import android.net.Uri
//import com.amuse.animalsounds.R
//import com.amuse.animalsounds.data.model.FeedBackModel
//import com.amuse.animalsounds.data.model.TopicModel
//import com.amuse.animalsounds.utils.ext.*
//import com.amuse.animalsounds.utils.sharePreferences.SPKeyEnum
//import com.amuse.animalsounds.utils.sharePreferences.SharePreferencesManager
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.ktx.storage
//import org.xmlpull.v1.XmlPullParser
//import org.xmlpull.v1.XmlPullParserFactory
//import java.io.*
//import java.util.*
//
//
//class FileManager {
//
//    var activity: Activity? = null
//    var timeStartApp = 0L
//
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        var sInstance: FileManager? = null
//
//        @Synchronized
//        fun initializeInstance() {
//            sInstance = FileManager()
//        }
//
//        @Synchronized
//        fun getInstance(): FileManager {
//            if (sInstance == null)
//                sInstance = FileManager()
//            return sInstance as FileManager
//        }
//    }
//
//    var dataTopic: ArrayList<TopicModel>? = null
//    var animalsName = ArrayList<String>()
//
//
//    constructor() {
//        timeStartApp = System.currentTimeMillis()
//    }
//
//    fun initData(activity: Activity) {
//        this.activity = activity
//        val stringArray = getResourcesByLocale(activity).getStringArray(R.array.animals)
//        for (i in stringArray)
//            animalsName.add(i.toString())
//
//        getDataTopics()
//    }
//
//    private fun storage(nameFolder: String): StorageReference {
//        val storage = Firebase.storage("gs://animal-sounds-ffb2b.appspot.com")
//        return storage.getReference(nameFolder)
//    }
//
//    fun uploadFeedBack(context: Context, feedBackModel: FeedBackModel) {
//        try {
//            val file = context.saveFileJsonFeedback()
////            Log.e("uploadFeedBack", "$file")
//            if (file != null) {
//                val fileRef = storage("feedback/${toJson(feedBackModel)}")
//                val fileUri = Uri.fromFile(file)
//                fileRef.putFile(fileUri)
//                    .addOnSuccessListener {
////                        Log.e("uploadFeedBack", "${it.storage}")
//                        deleteFile(file)
//                    }
//                    .addOnFailureListener {
//                        // The file upload failed
//                        deleteFile(file)
//                    }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun getDataTopics(onResultData: ((ArrayList<TopicModel>) -> Unit?)? =null) {
//        if (this.dataTopic == null)
//            this.dataTopic =
//                fromJsonArray(assetJSONFile("dayfile.json", activity!!), TopicModel::class.java)
//
//        onResultData?.invoke(dataTopic!!)
//
////        val dataTopic =
////            fromJsonArray(assetJSONFile("dayfile.json", activity!!), TopicModel::class.java)
//
//
////        //TODO: check time out load image
////        val time = object : CountDownTimer(10000L, 1000) {
////            override fun onFinish() {
////                if (!isCancelListener) {
////                    isCancelListener = true
////                    this@FileManager.dataTopic = dataTopic
////                    listener?.onResult()
////                }
////            }
////
////            override fun onTick(millisUntilFinished: Long) {
////
////            }
////        }.start()
////        var count = 0
////
////        if (SharePreferencesManager.getInstance()
////                .getValueBool(SPKeyEnum.FIST_APP) && NetworkUtil.isOnline()
////        ) {
////            for (item in dataTopic) {
////                val urlImage = item.imgurltopic[if (day == Calendar.SUNDAY) 6 else day - 2]
////
////                Glide.with(activity!!)
////                    .asBitmap().load(urlImage)
////                    .listener(object : RequestListener<Bitmap?> {
////                        override fun onLoadFailed(
////                            e: GlideException?,
////                            model: Any?,
////                            target: Target<Bitmap?>?,
////                            isFirstResource: Boolean
////                        ): Boolean {
////                            return false
////                        }
////
////                        override fun onResourceReady(
////                            resource: Bitmap?,
////                            model: Any?,
////                            target: Target<Bitmap?>?,
////                            dataSource: DataSource?,
////                            isFirstResource: Boolean
////                        ): Boolean {
////                            item.imgBitmapTopic = resource!!
////                            count++
////                            if (count >= dataTopic.size && !isCancelListener) {
////                                isCancelListener = true
////                                time.cancel()
////                                sInstance!!.dataTopic = dataTopic
////                                listener?.onResult()
////                            }
////                            return false
////                        }
////                    }).submit()
//////                if (item.aid == "animalsGroupAll")
//////                    dataTopic.remove(item)
////            }
////        } else {
////            this.dataTopic = dataTopic
////            listener?.onResult()
////        }
//    }
//
//    //TODO: lấy link ảnh pixabay
//    fun getLink(context: Context, idPos: Int): ArrayList<String> {
//        val links = ArrayList<String>()
//        val stream = context.resources.openRawResource(R.raw.gallery_links)
//        val xmlPullParserFactory = XmlPullParserFactory.newInstance()
//        val parser = xmlPullParserFactory.newPullParser()
//        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//        parser.setInput(stream, null)
//
//        var tag_name: String? = ""
//        var text = ""
//        var event = parser.eventType
//        var id = ""
//
//        while (event != XmlPullParser.END_DOCUMENT) {
//            tag_name = parser.name
//            when (event) {
//                XmlPullParser.START_TAG -> {
//                }
//
//                XmlPullParser.TEXT -> text = parser.text
//                XmlPullParser.END_TAG -> {
//                    if (tag_name.equals("id"))
//                        id = text
//                    if (id == idPos.toString() && id != text && text.isNotEmpty())
//                        links.add("https://cdn.pixabay.com/${text}_640.jpg")
//                }
//            }
//            event = parser.next()
//        }
//        return links
//    }
//
////    private fun loadBitmapImage(
////        context: Context,
////        url: String,
////        listener: CallBackLoadImage
////    ) {
////        Glide.with(context)
////            .asBitmap()
////            .load(url)
////            .listener(object : RequestListener<Bitmap?> {
////                override fun onLoadFailed(
////                    e: GlideException?,
////                    model: Any?,
////                    target: Target<Bitmap?>?,
////                    isFirstResource: Boolean
////                ): Boolean {
////                    return false
////                }
////
////                override fun onResourceReady(
////                    resource: Bitmap?,
////                    model: Any?,
////                    target: Target<Bitmap?>?,
////                    dataSource: DataSource?,
////                    isFirstResource: Boolean
////                ): Boolean {
////                    listener.onCall(resource!!)
////                    return false
////                }
////            }).submit()
////    }
//
//    private fun assetJSONFile(filename: String?, context: Context): String {
//        val manager: AssetManager = context.assets
//        val file: InputStream = manager.open(filename!!)
//        val formArray = ByteArray(file.available())
//        file.read(formArray)
//        file.close()
//        return String(formArray)
//    }
//
//    //TODO: get name folder
//    private fun getListFolder(storageReference: StorageReference): List<StorageReference> {
//        var items: List<StorageReference>? = null
//        storageReference.listAll().addOnSuccessListener { listResult ->
//            items = listResult.prefixes
//        }
//        return items!!
//    }
////
////    //TODO: get name folder
////    private fun getListFile(storageReference: StorageReference) {
////        storageReference.listAll().addOnSuccessListener { listResult ->
////            for (item in listResult.items) {
////                Log.e("getDayData", " ${item.name}")
////                item.storage.reference.child(item.path).downloadUrl
////                    .addOnSuccessListener { uri ->
////                        val presenter = RestPresenter()
////                        if (item.name == "animals.json") {
////                            presenter.onGetFileJson(uri.toString())
////                                .subscribeUntilDestroy(activity!!, LoadingType.NOT) {
////                                    onNext {
//////                                        allFile = it
////                                        SharePreferencesManager.getInstance()
////                                            .setValue(SPKeyEnum.ANIMALS_JSON, toJson(it))
////                                    }
////                                }
////                        } else {
//////                            presenter.onGetTopicJson(uri.toString())
//////                                .subscribeUntilDestroy(activity!!, LoadingType.NOT) {
//////                                    onNext { dataTopic ->
//////                                        SharePreferencesManager.getInstance()
//////                                            .setValue(SPKeyEnum.DAY_JSON, toJson(dataTopic))
//////
//////                                        for (item in dataTopic) {
//////                                            val urlImage =
//////                                                item.imgurltopic[if (day == Calendar.SUNDAY) 6 else day - 2]
//////                                            loadBitmapImage(
//////                                                activity!!,
//////                                                urlImage,
//////                                                object : CallBackLoadImage {
//////                                                    override fun onCall(bitmap: Bitmap) {
//////                                                        item.imgBitmapTopic = bitmap
//////                                                    }
//////                                                })
//////                                            if (item.aid == "backdrop1")
//////                                                dataTopic.remove(item)
//////                                        }
//////                                        sInstance!!.dataTopic = dataTopic
//////                                        listener?.onResult()
//////                                    }
//////                                }
////                        }
////
////                    }
////            }
////        }
//////        return items!!
////    }
////
////    /**
////     * get Array
////     */
////
////    @SuppressLint("Recycle")
////    fun animalsArray(idArray: Int, context: Context): ArrayList<String> {
////        val item = ArrayList<String>()
////        val animals = context.resources.obtainTypedArray(idArray)
////        val animalDatas = getResourcesByLocale(context).obtainTypedArray(R.array.animals)
////        for (i in 0 until animals.length()) {
////            if (animals.getInt(i, 0) != 0)
////                item.add(animalDatas.getString(i)!!)
////        }
////        return item
////    }
////
////    @SuppressLint("Recycle")
////    fun animalsSourdArray(idArray: Int): ArrayList<String> {
////        val item = ArrayList<String>()
////        val animals = activity!!.resources.obtainTypedArray(idArray)
////        val animalDatas = activity!!.resources.obtainTypedArray(R.array.animals_files)
////        for (i in 0 until animals.length()) {
////            if (animals.getInt(i, 0) != 0)
////                item.add(animalDatas.getString(i)!!)
////        }
////        return item
////    }
////
////
////    interface CallBackDataTopic {
////        fun onCall(items: ArrayList<TopicModel>)
////    }
//
////    interface CallBackLoadImage {
////        fun onCall(bitmap: Bitmap)
////    }
////
////    interface CallBackDataResponer {
////        fun onResult()
////    }
//
//
//    /**
//     * sourd
//     */
//    var mediaPlayer: MediaPlayer? = null
//    fun startSound(activity: Activity, fileName: String, listener: CallBackSound?): MediaPlayer {
//        val raw = activity.getIdByNameRes(fileName, "raw")
////        try {
////            Log.e("startSound", "$fileName")
////
////            player = MediaPlayer.create(activity, raw)
////            player?.apply {
////                try {
////                    isLooping =
////                        SharePreferencesManager.getInstance().getValueBool(SPKeyEnum.PLAY_LOOP)
////                    start()
////                    setOnCompletionListener { mp ->
////                        mp.stop()
//////                        mp.release()
////                        listener?.apply { onCompletion() }
////                    }
////                } catch (e: IOException) {
////                    e.printStackTrace()
////                }
////            }
////        } catch (e: IOException) {
////            e.printStackTrace()
////        }
//
//        try {
//
//            try {
//                mediaPlayer?.let {
//                    it.stop()
//                    it.release()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            mediaPlayer = null
//            mediaPlayer = MediaPlayer.create(activity, raw)
//            mediaPlayer!!.isLooping =
//                SharePreferencesManager.getInstance().getValueBool(SPKeyEnum.PLAY_LOOP)
//            mediaPlayer!!.setOnPreparedListener { mediaPlayer!!.start() }
//            mediaPlayer!!.setOnCompletionListener { mMediaPlayer ->
//                mMediaPlayer.stop()
//                mMediaPlayer.release()
//                listener?.apply { onCompletion() }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//        return mediaPlayer!!
//    }
//
//
//    fun stopSound(player: MediaPlayer?) {
//        try {
//            player?.apply {
//                stop()
//                release()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    private fun deleteFile(file: File) {
//        if (file.exists()) {
//            if (file.delete()) {
//                println("File deleted successfully")
//            } else {
//                println("Failed to delete file")
//            }
//        } else {
//            println("File does not exist")
//        }
//    }
//
//    interface CallBackSound {
//        fun onCompletion()
//    }
//}
//
