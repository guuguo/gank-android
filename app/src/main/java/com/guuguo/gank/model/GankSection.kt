package com.guuguo.gank.model

import com.chad.library.adapter.base.entity.SectionEntity
import com.guuguo.gank.model.entity.GankModel

/**
 * Created by guodeqing on 7/17/16.
 */

class GankSection : SectionEntity<GankModel> {

    constructor(header: String) : super(true, header) 

    constructor(t: GankModel) : super(t)

}
