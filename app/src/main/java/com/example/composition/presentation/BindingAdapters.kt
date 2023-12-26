package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_percentage),
        count
    )
}
@BindingAdapter("scorePercentage")
fun bindScorePercentage(tv: TextView, gameResult: GameResult) {
    tv.text = String.format(
        tv.context.getString(R.string.required_percentage),
        getPercentageOfRightAnswers(gameResult)
    )
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(iv: ImageView, winner: Boolean) {
    iv.setImageResource(getSmileResId(winner))
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

private fun getPercentageOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("setProgress")
fun bindSetProgress(pb: ProgressBar, count: Int) {
    pb.setProgress(count, true)
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(tv: TextView, enough: Boolean) {
    tv.setTextColor(getColorByState(tv.context, enough))
}
@BindingAdapter("enoughPercent")
fun bindEnoughPercent(pb: ProgressBar, enough: Boolean) {
    val color = getColorByState(pb.context, enough)
    pb.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(tv: TextView, number: Int) {
    tv.text = number.toString()
}
@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(tv: TextView, clickListener: OnOptionClickListener) {
    tv.setOnClickListener {
        clickListener.onOptionClick(tv.text.toString().toInt())
    }
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}
