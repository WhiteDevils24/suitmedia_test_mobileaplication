package com.akbar.suitmediatestaplication.data

data class UserResponse(
    val data: List<User>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int

)
