
package com.adit.poskologistikapp.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import coil.api.load
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.contracts.LogistikMasukActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaLogistikMasukBinding
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.presenters.KelolaLogistikMasukActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class KelolaLogistikMasukActivity : AppCompatActivity(), LogistikMasukActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaLogistikMasukBinding
    private var produkKonsidi: String = "baru"
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null
    private var presenter : LogistikMasukActivityContract.CreateOrUpdatePresenter? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityKelolaLogistikMasukBinding.inflate(layoutInflater)
        presenter = KelolaLogistikMasukActivityPresenter(this)
        setContentView(binding.root)
        handleRadioButton()
        openDatePicker()
        binding.etTanggal.inputType = InputType.TYPE_NULL
        binding.imageView.setOnClickListener {
            chooseImage()
        }

        setupSpinner()
        doSave()
        fill()

        binding.etTanggal.setText(currentDateTime.format(DateTimeFormatter.ISO_DATE))
    }

    private fun setupSpinner(){
        val spinnerJenisAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.jenis_kebutuhan
        ))
        binding.jenis.adapter = spinnerJenisAdapter

        val spinnerSatuanAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.satuan
        ))
        binding.spinnerSatuan.adapter = spinnerSatuanAdapter

        val spinnerStatusAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.status
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getMasuk()?.jenis_kebutuhan)
            binding.jenis.setSelection(selectedJenis)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getMasuk()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)

            val selectedStatus = spinnerStatusAdapter.getPosition(getMasuk()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)
        }
    }

    private fun handleRadioButton(){
        binding.rgCondition.setOnCheckedChangeListener { _, checkedId ->
            val radioSelected = findViewById<RadioButton>(checkedId)
            produkKonsidi = radioSelected.text.toString().lowercase()
            if(radioSelected.text.toString() == "Baru"){
                binding.inNama.visibility = View.VISIBLE
                binding.rlProduk.visibility = View.GONE
            }else{
                binding.inNama.visibility = View.GONE
                binding.rlProduk.visibility = View.VISIBLE
            }
        }
    }

    private val imagePickerLauncher = registerImagePicker {
        choosedImage = it[0]
        showImage()
    }

    private fun chooseImage(){
        val config = ImagePickerConfig{
            mode = ImagePickerMode.SINGLE
            isIncludeVideo = false
            isShowCamera = false
        }

        imagePickerLauncher.launch(config)
    }

    private fun showImage(){
        choosedImage?.let{
                image -> binding.imageView.load(image.uri)
        }
    }

    private fun showAlertDialog(message: String){
        AlertDialog.Builder(this@KelolaLogistikMasukActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                d.cancel()
            }
        }.show()
    }

    private fun openDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.etTanggal.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.etTanggal.setText("$year-${month+1}-$dayOfMonth")
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getMasuk () : LogistikMasuk = intent.getParcelableExtra("MASUK")!!

    override fun attachToSpinner(produk: List<Logistik>) {
        val spinnerProdukAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, produk)
        binding.spinnerProduk.adapter = spinnerProdukAdapter
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KelolaLogistikMasukActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaLogistikMasukActivity, LogistikMasukActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun getProduk(){
        val token = Constants.getToken(this)
        presenter?.getLogistikProduk(token)
    }

    override fun onResume() {
        super.onResume()
        getProduk()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val baru = RequestBody.create(
                MultipartBody.FORM, produkKonsidi
            )
            val objectProduk = binding.spinnerProduk.selectedItem as Logistik
            val id_produk = RequestBody.create(
                MultipartBody.FORM, objectProduk.id
            )

            println("ID PRODUK " + produkKonsidi == "baru")

            val nama_produk = RequestBody.create(
                MultipartBody.FORM, binding.etNama.text.toString()
            )

            val jenis_kebutuhan = RequestBody.create(
                MultipartBody.FORM, binding.jenis.selectedItem.toString()
            )
            val keterangan = RequestBody.create(
                MultipartBody.FORM, binding.etKeterangan.text.toString()
            )
            val jumlah = RequestBody.create(
                MultipartBody.FORM, binding.etJumlah.text.toString()
            )

            val pengirim = RequestBody.create(
                MultipartBody.FORM, binding.etPengirim.text.toString()
            )

            val satuan = RequestBody.create(
                MultipartBody.FORM, objectProduk.satuan
            )
            val status = RequestBody.create(
                MultipartBody.FORM, "Proses"
            )
            val tanggal = RequestBody.create(
                MultipartBody.FORM, binding.etTanggal.text.toString()
            )
            if(choosedImage != null){
                var originalFile = File(choosedImage?.path!!)

                var imagePart : RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    originalFile
                )

                image = MultipartBody.Part.createFormData(
                    "foto",
                    originalFile.name,
                    imagePart
                )
            }

            if(isNew()){
                if(produkKonsidi == "baru"){
                    presenter?.createNew(token, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, image!!, baru, nama_produk )
                }else{
                    presenter?.create(token, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, image!!, id_produk)
                }
            }else{
                presenter?.update(token!!, getMasuk()?.id, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, image!!, id_produk)
            }
        }
    }

    private fun fill(){
        if(!isNew()){
            binding.etKeterangan.setText(getMasuk()?.keterangan)
            binding.etJumlah.setText(getMasuk()?.jumlah)
            binding.etPengirim.setText(getMasuk()?.pengirim)
            binding.etTanggal.setText(getMasuk()?.tanggal)
            binding.imageView.load(getMasuk()?.foto)
            produkKonsidi = "lama"
        }
    }
}