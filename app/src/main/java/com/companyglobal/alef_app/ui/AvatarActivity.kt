package com.companyglobal.alef_app.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.utils.SharedPreferencesManager
import com.companyglobal.alef_app.databinding.ActivityAvatarBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File


class AvatarActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAvatarBinding

    private var resultLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                if (data != null) {
                    val selectedPhoto = data.data // content://gallery/photo/..
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(
                        selectedPhoto!!,
                        filePathColumn, null, null, null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val imgIndex = cursor.getColumnIndex(filePathColumn[0])
                        val photoPath = cursor.getString(imgIndex)

                        // Subir la foto al Viewmodel.upload(photoPath);
                        Log.d("IMAGEN", photoPath)
                        if (!photoPath.isEmpty()) {
                            val imgFile = File(photoPath)
                            if (imgFile.exists()) {
                                val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                                mBinding.civAvatar.setImageBitmap(bitmap)
                            }
                        }

                        cursor.close()
                    }
                }
            }
        }

    private var resultLauncherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val photo = data.extras?.get("data") as Bitmap
                    mBinding.civAvatar.setImageBitmap(photo)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnSelectAvatar.setOnClickListener {
            selectDialogGalleryOrCamera()
        }

        mBinding.fab.setOnClickListener {
            val intent = Intent(this, InformationUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectDialogGalleryOrCamera() {

        val items = resources.getStringArray(R.array.array_options_item)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_gallery_camera_title)
            .setItems(items) { _, i ->
                when (i) {
                    0 -> requestCameraPermission()
                    1 -> requestGalleryPermission()
                    2 -> usePictureGoogle()
                    3 -> null
                }
            }
            .show()
    }

    private fun usePictureGoogle() {
        val pictureGoogle =
            SharedPreferencesManager.getStringValue(AppConstants.USER_PICTURE_PROFILE)
        if (pictureGoogle != "") {
            Glide.with(this).load(pictureGoogle).centerCrop().into(mBinding.civAvatar)
        } else {
            Toast.makeText(this, "Ocurrio un error, seleccione otra opción ;S", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun requestGalleryPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openGallery()
                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Ocurrio un error en los permisos de la galeria",
                    Toast.LENGTH_SHORT
                ).show();
            }.check()

    }

    private fun requestCameraPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openCamera()
                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Ocurrio un error en los permisos de la camara",
                    Toast.LENGTH_SHORT
                ).show();
            }.check()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncherGallery.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncherCamera.launch(intent)
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_permissions_title)
            .setMessage(R.string.dialog_permissions_message)
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .setPositiveButton(R.string.dialog_confirm) { dialog, _ ->
                dialog.cancel()
                openSettings()
            }
            .show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        resultLauncherCamera.launch(intent)
    }
}