class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.runningplan)

        val week1Button = findViewById<Button>(R.id.week1)
        week1Button.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            this.startActivity(intent)
            Log.d("TAG", "Week 1 선택!")

            }
    }
