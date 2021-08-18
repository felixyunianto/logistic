package com.adit.poskologistikapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import coil.api.load
import com.adit.poskologistikapp.activities.BencanaActivity
import com.adit.poskologistikapp.activities.MainActivity
import com.adit.poskologistikapp.contracts.BencanaActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaBencanaBinding
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.presenters.KelolaBencanaActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class KelolaBencanaActivity : AppCompatActivity(), BencanaActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaBencanaBinding
    private var presenter : BencanaActivityContract.CreateOrUpdatePresenter? = null
    private lateinit var tanggal : String
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null
    private lateinit var sheenValidator : SheenValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openDatePicker()
        presenter = KelolaBencanaActivityPresenter(this)
        sheenValidator = SheenValidator(this)
        supportActionBar?.hide()
        binding.etTanggal.inputType = InputType.TYPE_NULL
        binding.imageView.setOnClickListener {
            chooseImage()
        }

        fill()
        doSave()
    }

    private val imagePickerLauncher = registerImagePicker {
        choosedImage = if(it.size == 0)  null else it[0]
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
        AlertDialog.Builder(this@KelolaBencanaActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                d.cancel()
            }
        }.show()

        hideLoading()
    }

    private fun openDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.etTanggal.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.etTanggal.setText("$year-${month+1}-$dayOfMonth")
                tanggal = "${year}-${month+1}-${dayOfMonth}"
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getBencana () : Bencana = intent.getParcelableExtra("BENCANA")!!

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getBencana()?.nama)
            binding.etDetail.setText(getBencana()?.detail)
            binding.etTanggal.setText(getBencana()?.tanggal)
            binding.imageView.load(getBencana()?.foto)
        }
    }

    private fun doSave(){
        sheenValidator.setOnValidatorListener {
            showLoading()
            val token = Constants.getToken(this@KelolaBencanaActivity)
            val nama = RequestBody.create(
                MultipartBody.FORM, binding.etNama.text.toString()
            )
            val detail = RequestBody.create(
                MultipartBody.FORM, binding.etDetail.text.toString()
            )
            val tanggal = RequestBody.create(
                MultipartBody.FORM,binding.etTanggal.text.toString()
            )

            val methodBody = RequestBody.create(
                MultipartBody.FORM, "PUT"
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
                if(choosedImage == null){
                    showAlertDialog("Silahkan pilih foto")
                    return@setOnValidatorListener
                }
                presenter?.create(token!!, nama, image!!, detail, tanggal)
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getBencana()!!.id.toString(), nama,detail, tanggal, methodBody)
                }else{
                    presenter?.update(token!!, getBencana()!!.id.toString(), nama, image!!,detail, tanggal, methodBody)
                }
            }
        }

        sheenValidator.registerAsRequired(binding.etNama)
        sheenValidator.registerAsRequired(binding.etDetail)
        sheenValidator.registerAsRequired(binding.etTanggal)

        binding.btnSubmit.setOnClickListener {
            sheenValidator.validate()
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaBencanaActivity, BencanaActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@KelolaBencanaActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingBencana.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingBencana.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }
}