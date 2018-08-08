package sec.co.jp.mbp_kotlin_sample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val jobId=1
    val TAG:String=javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG,"onResume()")

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val buttonJobStart:Button = findViewById(R.id.buttonJobStart)
        buttonJobStart.setOnClickListener {

            Log.d(TAG,"buttonJobStart()")

            val componentName = ComponentName(this,
                    CasarealJobService::class.java)

            val jobInfo = JobInfo.Builder(jobId, componentName)
                    .setBackoffCriteria(10000, JobInfo.BACKOFF_POLICY_LINEAR)
                    .setPersisted(false)
                    .setPeriodic(0)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                    .setRequiresCharging(false)
                    .build()
            scheduler.schedule(jobInfo)
        }

        val buttonJobCancel:Button = findViewById(R.id.buttonJobCancel)
        buttonJobCancel.setOnClickListener {

            Log.d(TAG,"buttonJobCancel()")

            scheduler.cancel(jobId);

            // 下記の方法だとアプリで登録したすべてのJobがキャンセルされる
            scheduler.cancelAll()
        }
    }
}
