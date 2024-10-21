package com.example.eclipticongovindtest.home.common

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class CommonUserMeth() {
    companion object {
        fun convertToBulletList(context: Context, textView: TextView, type: String) {
            var fullText: String = ""
            var firstPart: String = ""
            var secondPart: String = ""
            if (type.equals(AppConst.LOGIN_FRAGMENT_TYPE)) {
                fullText =
                    "By creating passcode you agree with our \n Terms & Conditions And Privacy Policy"
                firstPart = "Terms & Conditions"
                secondPart = "Privacy Policy"

            } else if (type.equals(AppConst.VERIFICATION_FRAGMENT_TYPE)) {
                fullText =
                    "don't receive OTP? Resend"
                firstPart = "Resend"
                secondPart = ""

            }
            val spannableString = SpannableString(fullText)

            val firstPartStart = fullText.indexOf(firstPart)

            spannableString.setSpan(
                ForegroundColorSpan(context.resources.getColor(R.color.term_and_condition_button_color)), // Set color to red
                firstPartStart,
                firstPartStart + firstPart.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val clickableSpan1 = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    if (type.equals(AppConst.LOGIN_FRAGMENT_TYPE)) {
                        Toast.makeText(context, "Term and Condition ", Toast.LENGTH_SHORT).show()
                    } else {
                        showPopup(
                            null, context, widget,
                            SharePreferenceDB.getAuthCode(context).toString(), type
                        )
                    }
                }
            }
            spannableString.setSpan(
                clickableSpan1,
                firstPartStart,
                firstPartStart + firstPart.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val secondPartStart = fullText.indexOf(secondPart)

            spannableString.setSpan(
                ForegroundColorSpan(context.resources.getColor(R.color.term_and_condition_button_color)), // Set color to blue
                secondPartStart,
                secondPartStart + secondPart.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val clickableSpan2 = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(context, "Term and Condition", Toast.LENGTH_SHORT).show()
                }
            }
            spannableString.setSpan(
                clickableSpan2,
                secondPartStart,
                secondPartStart + secondPart.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            textView.text = spannableString
            textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        }


        fun showPopup(
            navController: NavController?, context: Context, view: View, inputString: String,
            type: String
        ) {
            lateinit var popupWindow: PopupWindow
            val inflater = LayoutInflater.from(context)

            val popupView = inflater.inflate(R.layout.popup_layout, null)

            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val editTexts = listOf<TextView>(
                popupView.findViewById(R.id.pin1ET),
                popupView.findViewById(R.id.pin2ET),
                popupView.findViewById(R.id.pin3ET),
                popupView.findViewById(R.id.pin4ET)
            )
            val characters = inputString.toCharArray()

            for (i in characters.indices) {
                if (i < editTexts.size) {
                    editTexts[i].setText(characters[i].toString())
                }
            }
            popupView.findViewById<TextView>(R.id.DismissOTPBT).setOnClickListener {
                popupWindow.dismiss()
            }
            popupView.findViewById<TextView>(R.id.okOTPBT).setOnClickListener {

                if (type.equals(AppConst.LOGIN_FRAGMENT_TYPE)) {
                    popupWindow.dismiss()

                    navController?.navigate(
                        R.id.verificationFragment,
                        null
                    )
                } else {
                    popupWindow.dismiss()
                }
            }

            popupWindow.isFocusable = true
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        fun getRealPathFromURI(context: Context, uri: Uri): String? {
            var cursor: Cursor? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            return try {
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor?.moveToFirst()
                columnIndex?.let { cursor?.getString(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                cursor?.close()
            }
        }

        fun getFileNameFromUri(context: Context, uri: Uri): String? {
            var cursor: Cursor? = null
            val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
            return try {
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                val columnIndex =
                    cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                cursor?.moveToFirst()
                columnIndex?.let { cursor?.getString(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                cursor?.close()
            }
        }


        fun pickImage(context: Context, pickImageLauncher: ActivityResultLauncher<Intent>) {
            val pickIntent: Intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Intent(Intent.ACTION_PICK).apply {
                    type = "image/*" // Specify the type of content to select
                }
            } else {
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                    type = "image/*" // Specify the type of content to select
                }
            }
            try {
                pickImageLauncher.launch(pickIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    "No application found to pick an image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        //        Home Activity Common Methods
        fun setImage(image: String, imgView: ImageView) {
            Glide.with(imgView.context)
                .load(image)
                .placeholder(R.drawable.splash_background)
                .into(imgView)
        }

        fun setBackgroundColor(color: String): Int {
            return Color.parseColor(color)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTimestampToDateAndTime(timestamp: Long): Pair<String, String> {
            val instant = Instant.ofEpochMilli(timestamp)

            // Formatter for date
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault())

            // Formatter for time
//            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//                .withZone(ZoneId.systemDefault())
            val timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
                .withZone(ZoneId.systemDefault())

            val date = dateFormatter.format(instant)
            val time = timeFormatter.format(instant)

            return Pair(date, time)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setDateTimeInTextViews(
            timestamp: Long,
            dateTextView: TextView,
            timeTextView: TextView
        ) {
            val (date, time) = convertTimestampToDateAndTime(timestamp)
            if (getLatestDate() == date) {
                dateTextView.text = "Today"
            } else {
                dateTextView.text = date

            }
            timeTextView.text = time
        }

        private fun getLatestDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            return currentDate
        }

        fun updateStatusTextView(
            context: Context,
            upComingData: String?,
            statusTextView: TextView
        ) {
            val context = context

            // Reset the TextView for every update
            statusTextView.text = ""
            statusTextView.setBackgroundResource(0)
            statusTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            when (upComingData) {
                "started" -> {
                    statusTextView.text = "LIVE"
                    Log.e("govind", statusTextView.text.toString())
                    statusTextView.setBackgroundResource(R.drawable.live_background)
                    val drawableLeft = ContextCompat.getDrawable(context, R.drawable.dot)
                    statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                        drawableLeft,
                        null,
                        null,
                        null
                    )
                }

                "completed" -> {
                    statusTextView.text = "COMPLETED"
                    Log.e("govind", statusTextView.text.toString())

                    statusTextView.setBackgroundResource(R.drawable.complete_status_background)
                    val drawableLeft = ContextCompat.getDrawable(context, R.drawable.complete)
                    statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                        drawableLeft,
                        null,
                        null,
                        null
                    )
                }

                "upcoming" -> {
                    statusTextView.text = "NEXT"
                    Log.e("govind", statusTextView.text.toString())

                    statusTextView.setBackgroundResource(R.drawable.upcoming_status_background)
                    val drawableLeft = ContextCompat.getDrawable(context, R.drawable.uncomplete)
                    statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                        drawableLeft,
                        null,
                        null,
                        null
                    )
                }

                "not_started" -> {
                    statusTextView.text = ""
                    Log.e("govind", statusTextView.text.toString())

                    statusTextView.setBackgroundResource(0)
                    statusTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }

                else -> {
                    // Reset to a default state if status is unknown
                    statusTextView.text = ""
                    Log.e("govind", statusTextView.text.toString())

                    statusTextView.setBackgroundResource(0)
                    statusTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            }
            statusTextView.requestLayout()
            statusTextView.invalidate()
        }

        fun getTexColor(contestText: String, context: Context?, textView: TextView) {
            val spannable = SpannableString(contestText)

            val startIndex = contestText.indexOf('(')
            val endIndex = contestText.indexOf(')') + 1

            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context!!,
                        R.color.colorSecondary
                    )
                ), // Replace with your color
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            textView.text = spannable
        }

    }
}