package ru.netology.nmedia

import androidx.annotation.DrawableRes

@DrawableRes
fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_liked_by_me_24dp else R.drawable.ic_like_24dp

fun printQuantity(quantity: Int): String {

    val hundreds = quantity % 1_000
    val thousands = quantity % 1_000_000
    val hundredsToText = (hundreds / 100).toString()
    val thousandsToText = (thousands / 100_000).toString()

    val value = when {
        quantity < 1_000 -> quantity
        quantity in 1_000..999_999 -> quantity / 1_000
        else -> quantity / 1_000_000
    }.toString()

    val result = when {
        quantity in 1_000..9_999 && hundreds < 100 -> value + "K"
        quantity in 1_000..9_999 -> value + '.' + hundredsToText + "K"
        quantity in 10_000..999_999 -> value + "K"
        quantity >= 1_000_000 && thousands < 100_000 -> value + "M"
        quantity >= 1_000_000 -> value + "." + thousandsToText + "M"
        else -> value
    }
    return result
}