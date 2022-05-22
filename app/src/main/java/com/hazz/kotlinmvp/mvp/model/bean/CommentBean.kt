package com.hazz.kotlinmvp.mvp.model.bean


data class CommentBean(
		var total: Int,
		var rows: List<Row>,
		var code: Int,
		var msg: String
) {

    data class Row(
            var searchValue: String,
            var createBy: String,
            var createTime: String,
            var updateBy: String,
            var updateTime: String,
            var remark: String,
            var params: Params,
            var id: Int,
            var mvUrl: String,
            var userId: Int,
            var content: String
    )

    data class Params(val code:String)

}