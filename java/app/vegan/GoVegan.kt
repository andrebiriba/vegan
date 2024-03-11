package app.vegan

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_go_vegan.*
import me.relex.circleindicator.CircleIndicator3

class GoVegan : AppCompatActivity() {

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_vegan)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val midiabutton = findViewById<ImageButton>(R.id.recomenBtn)
        val beepoint = findViewById<ImageView>(R.id.imageView24)
        val textbutton = findViewById<View>(R.id.textView51) as TextView

        val anim = RotateAnimation(350f, 0f, 15f, 15f)
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = Animation.INFINITE
        anim.duration = 600
        beepoint.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            beepoint.animation = null
        },3000)


        Handler(Looper.getMainLooper()).postDelayed({
            beepoint.rotationY = 180f
            beepoint.animation = AnimationUtils.loadAnimation(this@GoVegan,R.anim.rotatezoomout)

            Handler(Looper.getMainLooper()).postDelayed({
                beepoint.clearAnimation()
                beepoint.visibility = View.GONE
            },4000)

        },7500)

        Handler(Looper.getMainLooper()).postDelayed({
            gif.visibility = View.GONE
            gifBg.visibility = View.GONE
            midiabutton.visibility = View.VISIBLE
            textbutton.visibility = View.VISIBLE
        },10000)

        postToList()

        view_pager2.adapter = ViewPagerAdapter(titlesList,descList,imagesList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator :CircleIndicator3 = findViewById(R.id.indicator)
        indicator.setViewPager(view_pager2)

       val imageButtonBack = findViewById<ImageButton>(R.id.imageButtonBack)

        imageButtonBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Outros::class.java)
            val actcompat = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                R.anim.slide_in_right, R.anim.slide_out_right)
            ActivityCompat.startActivity(this, intent, actcompat.toBundle())
            finish() })

        midiabutton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Goveganmore3::class.java)
            val actcompat = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                R.anim.slide_in_left, R.anim.slide_out_left)
            ActivityCompat.startActivity(this, intent, actcompat.toBundle())
        finish()})

        textbutton.setOnClickListener {
            val intent = Intent(this, Goveganmore3::class.java)
            val actcompat = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                R.anim.slide_in_left, R.anim.slide_out_left)
            ActivityCompat.startActivity(this, intent, actcompat.toBundle())
            finish() } }

    private fun addToList(title: String, description: String, image: Int){
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i:Int in 0..0) {
            addToList(getString(R.string.govegan1title),getString(R.string.govegan1), R.drawable.sunflower)
            addToList(getString(R.string.govegan2title),getString(R.string.govegan2), R.drawable.why)
            addToList(getString(R.string.govegan3title),getString(R.string.govegan3), R.drawable.search)
            addToList(getString(R.string.govegan4title),getString(R.string.govegan4), R.drawable.species)
            addToList(getString(R.string.govegan5title),getString(R.string.govegan5), R.drawable.die)
            addToList(getString(R.string.govegan6title),getString(R.string.govegan6), R.drawable.connected)
            addToList(getString(R.string.govegan7title),getString(R.string.govegan7), R.drawable.sheep)
            addToList(getString(R.string.govegan8title),getString(R.string.govegan8), R.drawable.apple)
            addToList(getString(R.string.govegan9title),getString(R.string.govegan9), R.drawable.man)
            addToList(getString(R.string.govegan10title),getString(R.string.govegan10), R.drawable.whendo)

        }

    }

}
