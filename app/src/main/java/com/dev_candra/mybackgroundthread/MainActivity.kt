package com.dev_candra.mybackgroundthread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.ref.WeakReference






class MainActivity : AppCompatActivity(){

    companion object{
        private const val INPUT_STRING = "Halo ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        GlobalScope.launch(Dispatchers.IO){
            // background thread
            val input = INPUT_STRING

            var output : String? = null
            Log.d(LOG_ASYNC,"Status : doInBackground")


            try {
                // input stringnya ditambahkan dengan string 'Selamat Belajar !!'
                output = "$input Selamat Belajar !!"

                delay(2000)

                // pindah ke Main Thread untuk update Ui
                withContext(Dispatchers.Main){
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }

            }catch (e: Exception){
                Log.d(LOG_ASYNC,e.message.toString())
            }

        }
    }

    private fun initToolbar(){
        supportActionBar?.title = "Candra Julius Sinaga"
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }
}

/*
    Kesimpulan
    Dispatchers.Default, adalah default dispatcher jika tidak ada Dispatcher yang didefinisikan, cocok untuk fungsi yang membutuhkan proses CPU yang tinggi, seperti parsing 100 data.
    Dispatchers.IO, untuk menjalankan fungsi yang berisi read-write data ke Network/Disk, seperti menulis atau mengambil data ke database dan ke server.
    Dispatchers.Main, untuk menjalankan fungsi di Main Thread, biasanya digunakan untuk mengupdate View UI.

   Beberapa Metode Utama di dalam AsyncTask
Kelas asynctask memiliki 4 metode utama, yaitu:

onPreExecute()
Metode ini akan dijalankan pertama kali sebelum proses asynchronous dilakukan. Metode ini masih berada pada ui thread. Pada umumnya, ia digunakan untuk menampilkan komponen ui seperti progress bar.
doInBackground()
Metode ini akan dijalankan setelah onPreExecute(). Di sinilah proses asynchronous terjadi. Pada kasus di atas, metode doInBackground() akan melakukan pengunduhan berkas dari network melalui metode downloadFile() dan hasil perkembangannya dikirim melalui metode publishProgress().

Penjelasan 3 argument di atas adalah seperti berikut:

Params :  Tipe parameter yang akan menjadi inputan untuk proses asynchronous.
Progress :  Tipe satuan unit untuk memberi kabar perkembangan ke ui thread.
Result  :  Tipe hasil dari proses asynchronous yang dijalankan.


 */

