package eric.latihanfragmentrumit

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val mFragmentManager = supportFragmentManager
        val mfSatu = fSatu()
        val mfDua = fDua()
        val mfTiga = fTiga()

        mFragmentManager.findFragmentByTag(fSatu::class.java.simpleName)
        mFragmentManager.beginTransaction().add(R.id.frameLayout, mfSatu, fSatu::class.java.simpleName).commit()

        val _tvHalaman1 = findViewById<TextView>(R.id.tvHalaman1)
        val _tvHalaman2 = findViewById<TextView>(R.id.tvHalaman2)
        val _tvHalaman3 = findViewById<TextView>(R.id.tvHalaman3)

        _tvHalaman1.setOnClickListener {
            mFragmentManager.findFragmentByTag(fSatu::class.java.simpleName)
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfSatu, fSatu::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _tvHalaman2.setOnClickListener {
            mFragmentManager.findFragmentByTag(fDua::class.java.simpleName)
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfDua, fDua::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        _tvHalaman3.setOnClickListener {
            mFragmentManager.findFragmentByTag(fTiga::class.java.simpleName)
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mfTiga, fTiga::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

    }


}

