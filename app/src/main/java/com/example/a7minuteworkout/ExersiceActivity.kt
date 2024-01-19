package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.a7minuteworkout.databinding.ActivityExersiceBinding
import com.example.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.Locale

class ExersiceActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExersiceBinding ?= null
    private var restTimer : CountDownTimer ?= null
    private var restProgress = 0
    private var exersiceList : ArrayList<ExersiceModel> ?= null
    private var exersiceCount = -1
    private var exersiceTimer : CountDownTimer ?= null
    private var exersiceProgress = 0
    private var tts : TextToSpeech ?= null
    private var player : MediaPlayer ?= null
    private var exersiceAdapter: ExersiceStatusAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExersiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        exersiceList = Constants.defaultExersiceList()

        binding?.rvExersiceStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exersiceAdapter = ExersiceStatusAdapter(exersiceList!!)
        binding?.rvExersiceStatus?.adapter = exersiceAdapter


        setSupportActionBar(binding?.toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tts = TextToSpeech(this, this)

        binding?.toolBar?.setNavigationOnClickListener{
            customBackPress()
        }


        setUpRestView()
    }

    override fun onBackPressed() {
        customBackPress()
    }

    private fun customBackPress() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener{
            this@ExersiceActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpRestView() {

        try {
            var uri = Uri.parse("android.resource://com.example.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, uri)
            player?.isLooping = false
            player?.start()
        } catch (e : Exception) {
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.flExersiceBar?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingExersice?.visibility = View.VISIBLE
        binding?.tvUpcomingExersiceName?.visibility = View.VISIBLE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExersiceName?.text = exersiceList!![exersiceCount + 1].getName()
        restProgressBar()
    }

    private fun setUpExersiceView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.flExersiceBar?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingExersice?.visibility = View.INVISIBLE
        binding?.tvUpcomingExersiceName?.visibility = View.INVISIBLE

        if(exersiceTimer != null) {
            exersiceTimer?.cancel()
            exersiceProgress = 0
        }

        speak(exersiceList!![exersiceCount].getName())
        binding?.ivImage?.setImageResource(exersiceList!![exersiceCount].getImage())
        binding?.tvExerciseName?.text = exersiceList!![exersiceCount].getName()


        exersiceProgressBar()
    }

    private fun restProgressBar() {
        binding?.progressBar?.progress = 10
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++;
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                exersiceCount++
                exersiceList!![exersiceCount].setIsSelected(true)
                exersiceAdapter!!.notifyDataSetChanged()
                setUpExersiceView()
            }

        }.start()
    }

    private fun exersiceProgressBar() {
        binding?.progressBarExercise?.progress = 30
        exersiceTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exersiceProgress++;
                binding?.progressBarExercise?.progress = 30 - exersiceProgress
                binding?.tvTimerExercise?.text = (30-exersiceProgress).toString()
            }

            override fun onFinish() {

                exersiceList!![exersiceCount].setIsSelected(false)
                exersiceList!![exersiceCount].setIsCompleted(true)
                exersiceAdapter!!.notifyDataSetChanged()


                if(exersiceCount < exersiceList?.size!! - 1) {
                    setUpRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExersiceActivity, finishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exersiceTimer != null) {
            exersiceTimer?.cancel()
            exersiceProgress = 0
        }

        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null) {
            player!!.stop()
        }
        super.onDestroy()
        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            tts?.setLanguage(Locale.US)
        }
    }

    fun speak(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }
}