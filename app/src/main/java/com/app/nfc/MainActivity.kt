package com.app.nfc

import android.app.PendingIntent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.nfc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    var myTag: Tag? = null
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        /* binding.textView.text =
            "Place the back of the phone over a NFC tag to read message from NFC tag"

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            finish()
        }
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            flags
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNfcIntent(intent)
    }

    private fun handleNfcIntent(intent: Intent) {
        val action = intent.action
        Log.e("tag","intent 1")
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            Log.e("tag","intent 2")

            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.ACTION_NDEF_DISCOVERED)

            if (rawMessages != null) {
                Log.e("tag","intent 3")

                for (message in rawMessages) {
                    val ndefMessage = message as NdefMessage
                    val records = ndefMessage.records
                    for (record in records) {
                        // Process the NFC data
                        val payload = String(record.payload)
                        binding.textView.append(payload + "\n")
                    }
                }
            }
        }
    }

   *//* override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.e("tag","tag1")
        processNfcTag(intent)
    }

    private fun processNfcTag(intent: Intent) {
        Log.e("tag","tag2")

        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Log.e("tag","tag3")

            // Handle the NFC tag, possibly using IsoDep for communication.
            val isoDep = IsoDep.get(tag)
            isoDep.connect()
            Log.e("data",isoDep.toString())
            // Read and process the bank card data using EMV standards.
            // This part may be specific to your bank card.

            isoDep.close()
        }
    }*//*
    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }
*/
        var i = 0;
        binding.textView.setOnClickListener(View.OnClickListener {
            Log.e("tag","tag")

            if (i == 0) {
                changeFragment(CorrectFragment())
                i++
            } else {
                changeFragment(ErrorFragment())
                i = 0
            }
        })
    }

    private fun changeFragment(fragment: Fragment) {
        Log.e("tag","tag")
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
}