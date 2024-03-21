package com.boikinhdich.quekinhdich.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amuse.animalsounds.utils.ext.hideSoftKeyboard
import com.boikinhdich.quekinhdich.R


class MainActivity : AppCompatActivity() {

//    private val linearCards: LinearLayout by lazy {
//        findViewById(R.id.container)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchFragment(SelectQueFragment(), "SelectQueFragment", true)

//        val imageSmallResourceIds = listOf(
//            R.drawable.ic_1,
//            R.drawable.ic_2,
//            R.drawable.ic_3,
//            R.drawable.ic_4,
//            R.drawable.ic_5,
//            R.drawable.ic_6,
//            R.drawable.ic_7,
//            R.drawable.ic_8,
//            R.drawable.ic_9,
//            R.drawable.ic_10,
//            R.drawable.ic_11,
//            R.drawable.ic_12,
//            R.drawable.ic_13,
//            R.drawable.ic_14,
//            R.drawable.ic_15,
//            R.drawable.ic_16,
//            R.drawable.ic_17,
//            R.drawable.ic_18,
//            R.drawable.ic_19,
//            R.drawable.ic_20,
//            R.drawable.ic_21,
//            R.drawable.ic_22,
//            R.drawable.ic_23,
//            R.drawable.ic_24,
//            R.drawable.ic_25,
//            R.drawable.ic_26,
//            R.drawable.ic_27,
//            R.drawable.ic_28,
//            R.drawable.ic_29,
//            R.drawable.ic_30,
//            R.drawable.ic_31,
//            R.drawable.ic_32,
//            R.drawable.ic_33,
//            R.drawable.ic_35,
//            R.drawable.ic_36,
//            R.drawable.ic_37,
//            R.drawable.ic_38,
//            R.drawable.ic_39,
//            R.drawable.ic_40,
//            R.drawable.ic_41,
//            R.drawable.ic_42,
//            R.drawable.ic_43,
//            R.drawable.ic_44,
//            R.drawable.ic_45,
//            R.drawable.ic_46,
//            R.drawable.ic_47,
//            R.drawable.ic_48,
//            R.drawable.ic_49,
//            R.drawable.ic_50,
//            R.drawable.ic_51,
//            R.drawable.ic_52,
//            R.drawable.ic_53,
//            R.drawable.ic_54,
//            R.drawable.ic_55,
//            R.drawable.ic_56,
//            R.drawable.ic_57,
//            R.drawable.ic_58,
//            R.drawable.ic_59,
//            R.drawable.ic_60,
//            R.drawable.ic_61,
//            R.drawable.ic_62,
//            R.drawable.ic_63,
//            R.drawable.ic_64
//        )
//
//        val imageDescriptionResourceIds = listOf(
//            Pair(1, R.drawable.ic_1_can_vi_thien),
//            Pair(2, R.drawable.ic_2_khon_vi_dia),
//            Pair(3, R.drawable.ic_3_thuy_loi_tran),
//            Pair(4, R.drawable.ic_4_son_thuy_mong),
//            Pair(5, R.drawable.ic_5_thuy_thien_nhu),
//            Pair(6, R.drawable.ic_6_thien_trach_tung),
//            Pair(7, R.drawable.ic_7_dia_thuy_su),
//            Pair(8, R.drawable.ic_8_thuy_dia_ty),
//            Pair(9, R.drawable.ic_9_phong_thien_tieu_suc),
//            Pair(10, R.drawable.ic_10_thien_trach_ly),
//            Pair(11, R.drawable.ic_11_dia_thien_thai),
//            Pair(12, R.drawable.ic_12_thien_dia_bi),
//            Pair(13, R.drawable.ic_13_thien_hoa_dong_nhan),
//            Pair(14, R.drawable.ic_14_hoa_thien_dai_huu),
//            Pair(15, R.drawable.ic_15_dia_son_khiem),
//            Pair(16, R.drawable.ic_16_loi_dia_du),
//            Pair(17, R.drawable.ic_17_thach_loi_tuy),
//            Pair(18, R.drawable.ic_18_son_phong_co),
//            Pair(19, R.drawable.ic_19_dia_trach_lam),
//            Pair(20, R.drawable.ic_20_phong_dia_quan),
//            Pair(21, R.drawable.ic_21_hoa_loi_he_hop),
//            Pair(22, R.drawable.ic_22_son_hoa_bi),
//            Pair(23, R.drawable.ic_23_son_dia_bac),
//            Pair(24, R.drawable.ic_24_dia_loi_phuc),
//            Pair(25, R.drawable.ic_25_thien_loi_vo_vong),
//            Pair(26, R.drawable.ic_26_son_thien_dai_thuc),
//            Pair(27, R.drawable.ic_27_son_loi_di),
//            Pair(28, R.drawable.ic_28_trach_phong_dai_qua),
//            Pair(29, R.drawable.ic_29_kham_vi_thuy),
//            Pair(30, R.drawable.ic_30_ly_vi_hoa),
//            Pair(31, R.drawable.ic_31_trach_son_hang),
//            Pair(32, R.drawable.ic_32_loi_phong_hang),
//            Pair(33, R.drawable.ic_33_thien_son_don),
//            Pair(34, R.drawable.ic_34_loi_thien_dia_trang),
//            Pair(35, R.drawable.ic_35_hoa_dia_tan),
//            Pair(36, R.drawable.ic_36_dia_hoa_minh_di),
//            Pair(37, R.drawable.ic_37_phong_hoa_gia_nhan),
//            Pair(38, R.drawable.ic_38_khoa_trach_khue),
//            Pair(39, R.drawable.ic_39_thuy_son_kien),
//            Pair(40, R.drawable.ic_40_loi_thuy_giai),
//            Pair(41, R.drawable.ic_41_son_trach_ton),
//            Pair(42, R.drawable.ic_42_phong_loi_ich),
//            Pair(43, R.drawable.ic_43_thach_thien_quai),
//            Pair(44, R.drawable.ic_44_thien_phong_cau),
//            Pair(45, R.drawable.ic_45_trach_dia_tuy),
//            Pair(46, R.drawable.ic_46_dia_phong_thang),
//            Pair(47, R.drawable.ic_47_thach_thuy_khon),
//            Pair(48, R.drawable.ic_48_thuy_phong_tinh),
//            Pair(49, R.drawable.ic_49_thach_hoa_cach),
//            Pair(50, R.drawable.ic_50_hoa_phong_dinh),
//            Pair(51, R.drawable.ic_51_chan_vi_loi),
//            Pair(52, R.drawable.ic_52_can_vi_son),
//            Pair(53, R.drawable.ic_53_phong_son_tiem),
//            Pair(54, R.drawable.ic_54_loi_trach_va_quy_muoi),
//            Pair(55, R.drawable.ic_55_loi_hoa_phong),
//            Pair(56, R.drawable.ic_56_hoa_son_lu),
//            Pair(57, R.drawable.ic_57_ton_vi_phong),
//            Pair(58, R.drawable.ic_58_doai_vi_trach),
//            Pair(59, R.drawable.ic_59_phong_thuy_hoan),
//            Pair(60, R.drawable.ic_60_thuy_trach_tiet),
//            Pair(61, R.drawable.ic_61_phong_trach_trung_phu),
//            Pair(62, R.drawable.ic_62_loi_son_tieu_qua),
//            Pair(63, R.drawable.ic_63_thuy_hoa_ky_te),
//            Pair(64, R.drawable.ic_64_hoa_thuy_vi_te)
//        )
//
//        // Chuyển đổi tất cả các ID tài nguyên thành chuỗi Base64
//        val base64Images = mutableListOf<Pair<Int, String>>()
//        for ((resourceId, _) in imageDescriptionResourceIds) {
//            val base64Image = convertResourceIdToBase64(resourceId)
//            base64Images.add(Pair(resourceId, base64Image))
//        }


        // Sử dụng danh sách chuỗi Base64 đã chuyển đổi
        // Ví dụ: tạo đối tượng Item cho mỗi ảnh và thêm chúng vào danh sách hoặc nguồn dữ liệu của bạn
//        for ((resourceId, base64Image) in base64Images) {
//            val newItem = Item(resourceId = resourceId, image = base64Image)
//            // adapter.addItem(newItem)
//            for (item in items) {
//                if (item.id == newItem.resourceId)
//                    item.imageDescription = newItem.image
//            }
//        }


    }

    fun switchFragment(
        fragment: Fragment?,
        tag: String?,
        isTaskback: Boolean,
    ) {
        hideSoftKeyboard()
        val fragmentTransaction =
            supportFragmentManager
                .beginTransaction().setCustomAnimations(
                    R.animator.slide_in_left, R.animator.slide_out_left,
                    R.animator.slide_out_right, R.animator.slide_in_right
                )
                .replace(R.id.container, fragment!!, tag)
        if (isTaskback) fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

//    private fun convertResourceIdToBase64(resourceId: Int): String {
//        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
//        return convertBitmapToBase64(bitmap)
//    }
//
//    private fun convertBitmapToBase64(bitmap: Bitmap): String {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//    }
//
//    data class Item(val resourceId: Int, val image: String)
}