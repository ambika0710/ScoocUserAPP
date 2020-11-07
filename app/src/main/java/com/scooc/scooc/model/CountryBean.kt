package com.scooc.scooc.model
import com.google.gson.annotations.SerializedName
import com.scooc.scooc.model.BaseBean

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package com.scooc.scoocdriver.model
 * Project LaTaxiDriver
 */

class CountryBean : BaseBean(), Comparable<CountryBean> {

    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("dial_code")
    var dialCode: String = ""
    @SerializedName("code")
    var countryCode: String = ""

    override fun compareTo(other: CountryBean): Int {
        val comparison = dialCode.compareTo(other.dialCode)
        return if (comparison == 0) {
            0
        } else if (comparison > 0) {
            1
        } else
            -1
    }
}
