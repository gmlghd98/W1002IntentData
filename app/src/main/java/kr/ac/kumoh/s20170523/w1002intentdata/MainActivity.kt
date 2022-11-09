package kr.ac.kumoh.s20170523.w1002intentdata

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20170523.w1002intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    companion object {
        const val keyName = "image"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGundam.setOnClickListener(this)
        binding.btnZaku.setOnClickListener(this)

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {

            if(it.resultCode != RESULT_OK)
                return@registerForActivityResult

            val result = it.data?.getIntExtra(
                ImageActivity.resultName,
                ImageActivity.NONE)


            val str = when (result) {
                ImageActivity.LIKE -> "좋아요"
                ImageActivity.DISLIKE -> "싫어요"
                else -> ""
            }

            val image = it.data?.getStringExtra(
                ImageActivity.imageName)

            when(image) {
                "gundam" -> binding.btnGundam.text = "건담 ($str)"
                "zaku" -> binding.btnZaku.text = "자쿠 ($str)"
            }
        }
    }

    override fun onClick(v:View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val value = when (v?.id) {
            binding.btnGundam.id -> "gundam"
            binding.btnZaku.id -> "zaku"
            else -> return
        }
        intent.putExtra(keyName, value)
        launcher.launch(intent)
    }
}