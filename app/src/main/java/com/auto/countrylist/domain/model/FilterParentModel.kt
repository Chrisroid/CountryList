package com.auto.countrylist.domain.model

import com.auto.countrylist.common.Constants

data class FilterParentModel(
    val parentTitle:String?=null,
    var type:Int = Constants.PARENT,
    var subList : MutableList<FilterChildData> = ArrayList(),
    var isExpanded:Boolean = false
)