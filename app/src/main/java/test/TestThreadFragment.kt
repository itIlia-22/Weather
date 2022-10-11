package test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.move.R
import com.example.move.databinding.FragmentTestTheardBinding
import kotlinx.android.synthetic.main.fragment_test_theard.*
import java.util.*
import java.util.concurrent.TimeUnit

class TestThreadFragment : Fragment() {

    private var _binding: FragmentTestTheardBinding? = null
    private val binding: FragmentTestTheardBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testBroadcastReceiver)
        }


        _binding = null
    }


    private val testBroadcastReceiver:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            //Достаём данные из интента
            intent.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA).also {
                Toast.makeText(context,it, Toast.LENGTH_LONG).show()
            }
        }

    }



    private fun initServiceWithBroadcastButton() {
        binding.serviceWithBroadcastButton.setOnClickListener {
            context?.let {
                it.startService(Intent(it, MainService::class.java).apply {
                    putExtra(
                        MAIN_SERVICE_INT_EXTRA,
                        binding.editText.text.toString().toInt()
                    )
                })
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(testBroadcastReceiver,
                    IntentFilter(TEST_BROADCAST_INTENT_FILTER))
        }


    }

    private var counterThread = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonService()
        initServiceWithBroadcastButton()
        binding.button.setOnClickListener {
            binding.textView.text = startCalculations(binding.editText.text.toString().toInt())
            binding.mainContainer.addView(AppCompatTextView(it.context).apply {
                text = getString(R.string.in_main_thread)
                textSize =
                    resources.getDimension(R.dimen.main_container_text_size)
            })
        }
        binding.calcThreadBtn.setOnClickListener {
            Thread {
                counterThread++
                activity?.runOnUiThread {
                    val calculatedText = startCalculations(binding.editText.text.toString().toInt())
                    binding.textView.text = calculatedText
                    binding.mainContainer.addView(AppCompatTextView(it.context).apply {
                        text = String.format(getString(R.string.from_thread), counterThread)
                        textSize =
                            resources.getDimension(R.dimen.main_container_text_size)
                    })

                }

            }.start()
        }

        val handlerThread = HandlerThread(getString(R.string.my_handler_thread))
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        binding.calcThreadHandler.setOnClickListener {
            handler.post {
                startCalculations(binding.editText.text.toString().toInt())
                mainContainer.post {
                    mainContainer.addView(AppCompatTextView(it.context).apply {
                        text = String.format(
                            getString(R.string.calculate_in_thread),
                            Thread.currentThread().name
                        )
                        textSize =
                            resources.getDimension(R.dimen.main_container_text_size)
                    })
                }

            }


        }
    }

    private fun startCalculations(second: Int): String {
        val date = Date()
        var diffInSec: Long
        do {
            val currentDate = Date()
            val diffInMin: Long = currentDate.time - date.time
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMin)
        } while (diffInSec < second)
        return diffInSec.toString()

    }

    private fun buttonService(){
        binding.serviceButton.setOnClickListener {
            context?.let {
                it.startService(Intent(it,MainService::class.java).apply {
                    putExtra(
                        MAIN_SERVICE_STRING_EXTRA,
                        getString(R.string.hello_from_thread_fragment))
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTestTheardBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TestThreadFragment()
    }
}
