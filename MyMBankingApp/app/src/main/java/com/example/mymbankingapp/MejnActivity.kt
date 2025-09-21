package com.example.mymbankingapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mymbankingapp.databinding.ActivityMejnBinding
import com.journeyapps.barcodescanner.*
import retrofit2.*
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MejnActivity : AppCompatActivity() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://127.0.0.1:8060/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
            if(isGranted){
                showCamera()
            }else{

            }
        }
    private val scanLauncher =
        registerForActivityResult(ScanContract()){result: ScanIntentResult ->
            run{
                if(result.contents == null){
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                }else{
                    setResult(result.contents)
                }
            }
        }
    private lateinit var binding: ActivityMejnBinding
    private var request = PaymentRequest(amount = 0, paymentId = ".", customerAccountNumber = ".", merchantAccountNumber = ".", merchantId = ".")
    private var qrCodeScanned = false
    private fun setResult(res: String){
        parseStringToObject(res)
        qrCodeScanned = true
    }
    private fun showCamera(){
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan qr code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        scanLauncher.launch(options)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
    }

    private fun initViews(){
        binding.QRbutton.setOnClickListener {
            checkPermissionCamera(this)
        }
        binding.payButton.setOnClickListener {
            if(qrCodeScanned){
                fetchData()
            }else{
                Toast.makeText(this, "Please scan the QR code first", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkPermissionCamera(context: Context){
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        }
        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        }
        else{
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    private fun initBinding(){
        binding = ActivityMejnBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun parseStringToObject(input: String) {
        var amountPart: String = "";
        var merchantName: String = "";
        var paymentPurpose: String = "";
        val pairs = input.split("|")

        for (pair in pairs) {
            val (field, value) = pair.split(":")
            when (field) {
                "C" -> request.paymentId = value
                "R" -> request.merchantAccountNumber = value
                "I" -> amountPart = value
                "RO" -> request.merchantId = value
                "N" -> merchantName = value
                "S" -> paymentPurpose = value
            }
        }
        val (currency, amount) = amountPart.split(";")
        request.amount =  amount.toInt()
        request.customerAccountNumber = getString(R.string.user_account_number)
        binding.amountText.text = "Amount to pay: "+request.amount+" "+currency
        binding.merchantAccount.text = "Recipient account number: " +request.merchantAccountNumber
        binding.paymentPurpose.text = "Payment purpose: "+paymentPurpose
    }

    fun fetchData(){
        val call: Call<PaymentResponse> = apiService.postData(request)
        println("URL: ${call.request().url()}")
        println("BODY: ${call.request().body()}")
        call.enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val data: PaymentResponse? = response.body()
                    if (data != null) {
                        println("Data received: ${data.status}")
                        callResultActivity("Payment successful")
                    }
                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                    callResultActivity("Payment not finished successfully.\nAn error occurred.")
                }
            }
            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                println("Failed to fetch data: ${t.message}")
            }
        })
    }
    fun callResultActivity(resultText: String){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("result_key", resultText)
        startActivity(intent)
    }

}