package com.app.nfc

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.nfc.databinding.ActivityMainBinding
import java.io.IOException


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
         binding.textView.text =
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

/*    override fun onNewIntent(intent: Intent) {
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
    }*/

    override fun onNewIntent(intent: Intent) {
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
            try {
                // Send SELECT command to the card to choose the application
                val selectAidCommand = byteArrayOf(
                    0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(), 0x07.toByte(),
                    0xA0.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x04.toByte(), 0x10.toByte(), 0x10.toByte()
                )
                val response = isoDep.transceive(selectAidCommand)

                // Check the response for success and continue with EMV commands
                if (response[response.size - 2] == 0x90.toByte() && response[response.size - 1] == 0x00.toByte()) {
                    // Continue sending EMV commands as needed
                    // For example, you can send GET DATA or READ RECORD commands.

                    // Example of reading cardholder name (tag 5A)
                    val getCardholderNameCommand = byteArrayOf(0x80.toByte(), 0xCA.toByte(),
                        0x9F.toByte(), 0x46.toByte(), 0x00.toByte())
                    val nameResponse = isoDep.transceive(getCardholderNameCommand)

                    // Process the response to extract and interpret data
                    val cardholderName = String(nameResponse, Charsets.UTF_8)
                    Log.e("Name",cardholderName)
                    binding.textView.text=cardholderName
                    // Handle and display the cardholder name
                    // ...

                } else {
                    // Card selection failed
                    // Handle the error
                }
            } catch (e: IOException) {
                // Handle communication errors
            } finally {
                isoDep.close()
            }
            isoDep.close()
        }
    }
    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

}